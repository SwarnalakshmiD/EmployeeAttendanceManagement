package com.example.UserActionService.dao.api;

import com.example.UserActionService.model.entity.Holidays;
import com.example.UserActionService.model.entity.Operations;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.Optional;

public interface HolidaysRepository extends JpaRepository<Holidays,Long>
{
    Optional<Holidays> findByHolidayDate(String holidayDate);
}
