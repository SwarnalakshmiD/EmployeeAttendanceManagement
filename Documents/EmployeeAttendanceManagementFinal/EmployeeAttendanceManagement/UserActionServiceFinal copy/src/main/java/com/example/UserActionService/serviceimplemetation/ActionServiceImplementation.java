package com.example.UserActionService.serviceimplemetation;

import com.example.UserActionService.dao.api.HolidaysRepository;
import com.example.UserActionService.dao.api.OperationRepository;
import com.example.UserActionService.dao.api.UserActionRepository;
import com.example.UserActionService.model.entity.Holidays;
import com.example.UserActionService.model.entity.Operations;
import com.example.UserActionService.model.entity.SwipeHistory;
import com.example.UserActionService.model.vo.*;
import com.example.UserActionService.services.ActionServices;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.sql.Date;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Service("actionServices")
public class ActionServiceImplementation implements ActionServices {
    @Autowired
    RestTemplate restTemplate;

    @Autowired
    UserActionRepository userActionRepository;

    @Autowired
    OperationRepository operationRepository;


    @Autowired
    HolidaysRepository holidaysRepository;

    private String userServiceUrl = "http://localhost:8080/userService";
    private String notificationUrl = "http://localhost:8099/notification";
    SimpleDateFormat dateFormatTime = new SimpleDateFormat("HH:mm:ss");
    SimpleDateFormat dateFormatDate = new SimpleDateFormat("yyyy-MM-dd");
    ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    public ActionServiceImplementation(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendMessage(NotificationVo notificationVo) throws JsonProcessingException {
        kafkaTemplate.send("com.attendance.actions.create", objectMapper.writeValueAsString(notificationVo));
    }

    public List<SwipeHistoryVo> getUserSwipehistory(int id) {

        List<SwipeHistory> swipeHistories = userActionRepository.findAllByEmployeeIdAndCurrentDate(id);
//        objectMapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd"));
//        objectMapper.setTimeZone(TimeZone.getTimeZone("UTC"));


        List<SwipeHistoryVo> list = objectMapper.convertValue(swipeHistories, List.class);
        return list;

    }


    public String applyUserActions(int id, OperationsVo operation) {

        List<Operations> existingLeaveRequests = operationRepository.findAllByEmpId(id);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate operationStartDate = LocalDate.parse(operation.getActionStarted(), formatter);
        LocalDate operationEndDate = LocalDate.parse(operation.getActionEnded(), formatter);

        for (Operations leaveRequest : existingLeaveRequests) {
            LocalDate leaveStartDate = LocalDate.parse(leaveRequest.getActionStarted(), formatter);
            LocalDate leaveEndDate = LocalDate.parse(leaveRequest.getActionEnded(), formatter);

            if (!(operationEndDate.isBefore(leaveStartDate) || operationStartDate.isAfter(leaveEndDate))) {
                return "Already applied for leave or wfh in these dates";
            }
        }
        operation.setEmpId(id);
        operation.setCreatedDate(Date.valueOf(LocalDate.now()).toString());
        operation.setStatus("pending");
        operation.setStatusUpdatedDate(Date.valueOf(LocalDate.now()).toString());
        operationRepository.save(objectMapper.convertValue(operation, Operations.class));

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        HttpEntity<String> entity = new HttpEntity<>(headers);
        UriComponents builder = UriComponentsBuilder.fromHttpUrl(userServiceUrl + "/manager/").queryParam("empId", id).build();
        UserVo employee = restTemplate.exchange(builder.toUriString(), HttpMethod.GET, entity, UserVo.class).getBody();
        // for(UserVo emp:employee){
        System.out.println(employee);
        NotificationVo notification = new NotificationVo();
        notification.setEmpId(employee.getManagerId());
        notification.setNotificationMessage(employee.getEmployeeName() + " applied " + operation.getActionType() + " on " + operation.getActionStarted());
        notification.setReadStatus("unread");
        try {
            sendMessage(notification);
        } catch (JsonProcessingException exception) {
            System.out.println(exception);
        }
        return "Success";

    }

    public String applyActionStatus(OperationsVo operationDetails) {
        Operations operation = operationRepository.findByEmpIdAndActionTypeAndCreatedDateAndActionStarted(
                operationDetails.getEmpId(), operationDetails.getActionType(), operationDetails.getCreatedDate(), operationDetails.getActionStarted());

        operation.setStatus(operationDetails.getStatus());

        operation.setStatusUpdatedDate(Date.valueOf(LocalDate.now()).toString());
        operationRepository.save(operation);
        NotificationVo notification = new NotificationVo();
        notification.setEmpId(operation.getEmpId());
        notification.setNotificationMessage(operation.getStatus());
        notification.setReadStatus("unread");
        notification.setActionType(operation.getActionType());
        notification.setActionStartDate(operation.getActionStarted());
        notification.setUpdatedDate(operation.getStatusUpdatedDate());
        try {
            sendMessage(notification);
        } catch (JsonProcessingException exception) {
            System.out.println(exception);
        }
        return "status updated";
    }

    public String userSwipeDetails(SwipeHistoryVo swipeHistory) {

            Optional<Holidays> holiday = holidaysRepository.findByHolidayDate(swipeHistory.getSwipeDate());
            if(holiday.isPresent()){
                return "This date is already an Holiday,Cant make entry";
            }

        if (swipeHistory.getSwipeTime() == null || swipeHistory.getSwipeDate() == null) {

            if (swipeHistory.getSwipeDate() == null) {
                //swipeHistory.setSwipeDate(new java.util.Date());
                swipeHistory.setSwipeDate(LocalDate.now().toString());
            }
            if (swipeHistory.getSwipeTime() == null) {
                swipeHistory.setSwipeTime(Time.valueOf(LocalTime.now()));
            }
        }

        userActionRepository.save(objectMapper.convertValue(swipeHistory, SwipeHistory.class));
        return "Success";

    }

    public List<UserVo> getEmployeeByManager(int id) {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        HttpEntity<String> entity = new HttpEntity<>(headers);
        UriComponents builder = UriComponentsBuilder.fromHttpUrl(userServiceUrl + "/user/manager").queryParam("managerId", id).build();
        return restTemplate.exchange(builder.toUriString(), HttpMethod.GET, entity, new ParameterizedTypeReference<List<UserVo>>() {
        }).getBody();
    }

    public List<OperationsVo> getUserActionhistory(int id) {
        List<Operations> operation = operationRepository.findAllByEmpId(id);
        List<OperationsVo> list = objectMapper.convertValue(operation, List.class);
        return list;

    }



        public List<OperationsVo> viewEmployeePendingStatus(int id) {
        List<UserVo> employees = getEmployeeByManager(id);
        List<Integer> empIds = new ArrayList<>();
        for (UserVo user : employees) {
            empIds.add(user.getId());
        }

        List<OperationsVo> empoloyeeStatus = objectMapper.convertValue(operationRepository.findPendingActionsByEmployeeIds(empIds), new TypeReference<List<OperationsVo>>() {
        });
        List<OperationsVo> employeeList = new ArrayList<>();
        for (OperationsVo operation : empoloyeeStatus) {
            for (UserVo employee : employees) {
                if (operation.getEmpId() == employee.getId()) {
                    operation.setEmployeeName(employee.getEmployeeName());
                    break;
                }
            }
            employeeList.add(operation);
        }
        return employeeList;
    }

    public List<ReportVo> report(ReportVo reportVo) {
        if (reportVo.getMonth() != null) {
            String[] array = reportVo.getMonth().split(" ");
            Month month = Month.valueOf(array[0].toUpperCase());
            LocalDate startDate = LocalDate.of(Integer.valueOf(array[1]), month, 1);
            LocalDate endDate = startDate.withDayOfMonth(startDate.lengthOfMonth());
            reportVo.setStartDate(startDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
            reportVo.setEndDate(endDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));

        }
        List<Object[]> entries = userActionRepository.getSwipeSummaryByEmployeeId(reportVo.emplId, reportVo.getStartDate(), reportVo.getEndDate());
        List<Object[]> entriesForActions = operationRepository.findActionSummaryByEmpId(reportVo.emplId, reportVo.getStartDate(), reportVo.getEndDate());
        List<ReportVo> list = new ArrayList<>();

        for (Object[] row : entries) {
            ReportVo report = new ReportVo();

            int eid = (int) row[0];

            String swipeDate = (String) row[1];

            Time min = (Time) row[3];
            Time max = (Time) row[4];

            report.setEmplId(eid);
            report.setCheckIn(min);
            report.setCheckOut(max);
            report.setReportDate(swipeDate);
            int diff = max.getHours() - min.getHours();
            report.setWorkingHours(diff);
            report.setDay(LocalDate.parse(swipeDate, DateTimeFormatter.ISO_DATE).getDayOfWeek().toString());

            list.add(report);

        }

        for (Object[] row : entriesForActions) {


            int eid = (int) row[0];
            String actionType = (String) row[1];
            String startDateString = (String) row[2];
            String endDateString = (String) row[3];
            LocalDate startDate = LocalDate.parse(startDateString);
            LocalDate endDate = LocalDate.parse(endDateString);
            while (!startDate.isAfter(endDate)) {

                ReportVo report = new ReportVo();
                report.setEmplId(eid);
                report.setActionType(actionType);
                report.setReportDate(startDate.toString());
                report.setDay(LocalDate.parse(startDate.toString(), DateTimeFormatter.ISO_DATE).getDayOfWeek().toString());
                list.add(report);
                startDate = startDate.plusDays(1);

            }

        }
        list.sort(Comparator.comparing(ReportVo::getReportDate));


        return list;
    }


    public List<HolidaysVo> getAllHolidays() {
        List<Holidays> holidays = holidaysRepository.findAll();
        return objectMapper.convertValue(holidays, List.class);
    }

    public CountOfEmployees countOfEmployees(int id) {

        List<UserVo> employees = getEmployeeByManager(id);
        List<Integer> empIds = new ArrayList<>();
        for (UserVo user : employees) {
            empIds.add(user.getId());
        }
        CountOfEmployees countOfEmployees = new CountOfEmployees();


        countOfEmployees.setPresentCount(userActionRepository.countPresentEmployeeIds(empIds, LocalDate.now().toString()));

        countOfEmployees.setLeaveCount(operationRepository.countOfEmployeesByActions(empIds, "leave", LocalDate.now().toString()));

        countOfEmployees.setWfhCount(operationRepository.countOfEmployeesByActions(empIds, "wfh", LocalDate.now().toString()));

        countOfEmployees.setTotalCount(employees.size());
        return countOfEmployees;
    }


    public String nudgeEmployeeDetails(int managerId) {
        List<UserVo> employees = getEmployeeByManager(managerId);
        for (UserVo employee : employees) {
            LocalDate endDate = LocalDate.now().minusDays(1);
            LocalDate startDate = LocalDate.now().minusDays(1);
            ReportVo reportVo = new ReportVo();
            reportVo.setEmplId(employee.getId());
            reportVo.setStartDate(startDate.toString());
            reportVo.setEndDate(endDate.toString());
            List<ReportVo> report = report(reportVo);

            List<ReportVo> filteredReports = report.stream()
                    .filter(r -> (r.getWorkingHours() < 4 && (r.getActionType() == null || r.getActionType().isEmpty())))
                    .collect(Collectors.toList());
            for (ReportVo filter : filteredReports) {
                NotificationVo notification = new NotificationVo();
                notification.setEmpId(employee.getManagerId());
                notification.setNotificationMessage(employee.getEmployeeName() + " worked for only " + filter.getWorkingHours() + " on " + filter.getReportDate());
                notification.setReadStatus("unread");
                try {
                    sendMessage(notification);
                } catch (JsonProcessingException exception) {
                    System.out.println(exception);
                }


            }
        }

        return "Success";
    }
}


