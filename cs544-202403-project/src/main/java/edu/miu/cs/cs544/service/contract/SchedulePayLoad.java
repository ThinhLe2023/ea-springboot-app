package edu.miu.cs.cs544.service.contract;

import edu.miu.cs.cs544.domain.Session;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
public class SchedulePayLoad implements Serializable {
    private Integer scheduleId;
    private Date startDate;
    private Date endDate;

    private List<Session> sessionList= new ArrayList<>();
}
