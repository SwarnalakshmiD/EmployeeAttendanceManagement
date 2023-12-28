package com.quinbay.UserService.serviceImplementation;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.quinbay.UserService.api.LoginRepository;
import com.quinbay.UserService.api.UserRepository;
import com.quinbay.UserService.model.entity.Login;
import com.quinbay.UserService.model.entity.User;
import com.quinbay.UserService.model.vo.LoginVo;
import com.quinbay.UserService.model.vo.NotificationVo;
import com.quinbay.UserService.model.vo.UserVo;
import com.quinbay.UserService.service.Services;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class ServicesImplementation implements Services {

    @Autowired
    UserRepository userRepository;

    @Autowired
    LoginRepository loginRepository;
    ObjectMapper objectMapper = new ObjectMapper();
    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    public ServicesImplementation(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendMessage(NotificationVo notificationVo) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        kafkaTemplate.send("com.attendance.actions.create",  objectMapper.writeValueAsString(notificationVo));
    }

    @Override
    public List<UserVo> getAllUserDetails(){
        List<User> userList = userRepository.findAll();
        return objectMapper.convertValue(userList, List.class);

    }

    public UserVo loginCheck(LoginVo loginVo) {

        Login login = loginRepository.findByUserName(loginVo.userName);
        User user = new User();
        if(Objects.isNull(login)) return new UserVo();
        else if (login.password.equals(loginVo.password)) {
            Optional<User> foundUser = userRepository.findById((long) login.employeeId);
            if (foundUser.isPresent()) {
                user = foundUser.get();

            }
        }

        return objectMapper.convertValue(user, UserVo.class);
    }
//    public UserVo getAllUserDetailsByName(String userempName){
//        User user = userRepository.findByuserempName(userempName);
//        System.out.println(user);
//        ObjectMapper objectMapper = new ObjectMapper();
//        return objectMapper.convertValue(user, UserVo.class);
//
//    }
    public List<UserVo> getAllUserDetailsByManagerId(int managerId){
        List<User> userList = userRepository.findBymanagerId(managerId);

        return objectMapper.convertValue(userList, List.class);

    }

    public String nudgeEmployee(NotificationVo notification)
    {
        notification.setReadStatus("unread");
        try{
            sendMessage(notification);
        }catch (JsonProcessingException exception){
            System.out.println(exception);
        }
        return "updated";
    }

    public UserVo getManager(int empId) {
        Optional<User> userOptional = userRepository.findById((long) empId);
        if(userOptional.isPresent()) {
            User user = userOptional.get();
            return objectMapper.convertValue(user, UserVo.class);
        }
        return null;
    }



}
