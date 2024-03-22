package edu.miu.cs.cs544.domain;

import edu.miu.common.domain.AuditData;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@Data
@Entity
public class Account implements Serializable {
    @Id
    @GeneratedValue
    private Long id;

    private String name;
    private String description;
    private Long balance;
    @ManyToOne(cascade = CascadeType.PERSIST)
    private AccountType accountType;
    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;
    @Embedded
    private AuditData auditData;

    public Account(String name, String description, AccountType accountType) {
        this.name = name;
        this.description = description;
        this.accountType = accountType;
    }

}
