package edu.miu.cs.cs544.service.contract;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;


@Data
public class AttendancePayload implements Serializable {
    private static final long serialVersionUID = 1L;

    private String attendanceId;
    private MemberPayload member ;
    private SessionPayload session;
    private ScannerPayload scanner;
    private boolean isPresent;

    @JsonFormat(shape = JsonFormat.Shape.STRING , pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime scannedAt;
}
