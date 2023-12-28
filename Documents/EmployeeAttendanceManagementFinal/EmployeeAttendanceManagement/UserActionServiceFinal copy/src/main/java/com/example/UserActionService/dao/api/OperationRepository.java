package com.example.UserActionService.dao.api;

import com.example.UserActionService.model.entity.Operations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

public interface OperationRepository extends JpaRepository<Operations, Long> {
    Operations findByEmpIdAndActionType(int empId, String actionType);
  List<Operations> findAllByEmpId(int empId);


    @Query("SELECT a FROM Operations a WHERE a.empId IN :empIds AND a.status = 'pending'")
     List<Operations> findPendingActionsByEmployeeIds(@Param("empIds") List<Integer> empIds);

    Operations findByEmpIdAndActionTypeAndCreatedDateAndActionStarted(
            int empId, String actionType, String createdDate, String actionStarted);


    @Query("SELECT o.empId,o.actionType, o.actionStarted, o.actionEnded " +
            "FROM Operations o " +
            "WHERE o.empId = :empId AND (o.actionStarted >= :startDate AND o.actionEnded <= :endDate) " +
            "GROUP BY o.empId, o.actionType, o.actionStarted, o.actionEnded")
    List<Object[]> findActionSummaryByEmpId(@Param("empId") int empId,
                                            @Param("startDate") String startDate,
                                            @Param("endDate") String endDate
    );
    @Query("SELECT COUNT(DISTINCT empId) FROM Operations WHERE empId IN :empIds AND actionType = :actionType AND actionStarted = :currentDate")
    int countOfEmployeesByActions(@Param("empIds") List<Integer> empIds,
                                  @Param("actionType") String actionType,
                                  @Param("currentDate") String currentDate);


}
