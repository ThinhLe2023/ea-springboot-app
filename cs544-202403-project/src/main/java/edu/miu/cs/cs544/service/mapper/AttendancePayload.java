package edu.miu.cs.cs544.service.mapper;

import lombok.Data;

import java.io.Serializable;


@Data
public class AttendancePayload implements Serializable {
    private static final long serialVersionUID = 1L;

    private String attendanceId;
    //    private Student student;
    //    private Session session;
    private boolean isPresent;
}
