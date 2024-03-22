package edu.miu.cs.cs544.domain;

import edu.miu.common.domain.AuditData;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
@Getter
@Setter
@NoArgsConstructor
@Entity
public class AccountType implements Serializable {
    @Id
    @GeneratedValue
    private Long id;
    private String type;
    private String description;
    @Embedded
    private AuditData auditData;

    @ManyToMany(mappedBy = "accountTypes")
    private List<Role> roles  =new ArrayList<Role>();
    public AccountType(String type, String description) {
        this.type = type;
        this.description = description;
    }

}
