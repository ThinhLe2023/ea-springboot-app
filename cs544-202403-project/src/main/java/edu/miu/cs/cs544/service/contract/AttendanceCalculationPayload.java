package edu.miu.cs.cs544.service.contract;

import edu.miu.cs.cs544.domain.Attendance;
import lombok.Data;

import java.io.Serializable;
import java.util.List;


@Data
public class AttendanceCalculationPayload implements Serializable {
    private static final long serialVersionUID = 1L;

    private long absent;
    private long attend;
    private int totalAttend;

    private List<AttendancePayload> attendences;
}
