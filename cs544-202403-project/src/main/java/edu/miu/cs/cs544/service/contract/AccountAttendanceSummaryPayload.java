package edu.miu.cs.cs544.service.contract;

import lombok.*;
import java.util.List;

@Data
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AccountAttendanceSummaryPayload {
    private long accountId;
    private Long memberId;
    private String accountName;
    private String accountType;
    private Long accountBalance;
    private long totalSessionRegistered;
    private int totalAttended;
    private long totalAbsent;
    private List<AttendancePayload> attendances;
}
