package com.factory.tycoon.schedule.domain.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
public class ScheduleRequest {
    private Long workorderId;
    private String status;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate date;
    private String shift;
    private String worker;
}
