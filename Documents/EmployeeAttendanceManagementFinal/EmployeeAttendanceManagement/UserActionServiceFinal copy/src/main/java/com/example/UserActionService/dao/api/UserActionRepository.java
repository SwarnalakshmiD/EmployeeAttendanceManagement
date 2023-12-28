package com.example.UserActionService.dao.api;

import com.example.UserActionService.model.entity.SwipeHistory;
import com.example.UserActionService.model.vo.SwipeHistoryVo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.List;

public interface UserActionRepository extends JpaRepository<SwipeHistory, Long> {


   List<SwipeHistory> findAllByEmployeeId(int empId);
    List<SwipeHistory> findAllByEmployeeIdAndSwipeDate(int employeeId, String swipeDate);
    default List<SwipeHistory> findAllByEmployeeIdAndCurrentDate(int employeeId) {
        String currentDate = LocalDate.now().toString();
        return findAllByEmployeeIdAndSwipeDate(employeeId, currentDate);
    }

    @Query("SELECT employeeId, swipeDate, COUNT(*), MIN(swipeTime), MAX(swipeTime) " +
            "FROM SwipeHistory " +
            "WHERE employeeId = :employeeId AND swipeDate >= :startDate AND swipeDate <= :endDate " +
            "GROUP BY swipeDate, employeeId")
    List<Object[]> getSwipeSummaryByEmployeeId(@Param("employeeId") int employeeId,
                                               @Param("startDate") String startDate,
                                               @Param("endDate") String endDate
    );
    @Query("SELECT COUNT(DISTINCT employeeId) FROM SwipeHistory  WHERE employeeId IN :empIds AND swipeDate = :currentDate")
    int countPresentEmployeeIds(@Param("empIds") List<Integer> empIds, @Param("currentDate") String currentDate);

  }


