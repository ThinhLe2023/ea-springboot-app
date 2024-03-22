package edu.miu.cs.cs544.service.contract;

import edu.miu.common.service.contract.CommonAuditData;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

@Data
public class AccountPayload implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private Long id;
    private String name;
    private String description;
    private Long balance;
    private MemberPayload member;
    private AccountTypePayload accountType;
    private CommonAuditData auditData;
}
