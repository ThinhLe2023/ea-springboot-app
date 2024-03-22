package edu.miu.cs.cs544.service.contract;

import edu.miu.common.domain.AuditData;
import lombok.Data;

import java.io.Serializable;

@Data
public class AccountTypePayload implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
    private String type;
    private String description;
    private AuditData auditData;
}
