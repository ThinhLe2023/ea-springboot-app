package edu.miu.cs.cs544.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import edu.miu.common.domain.AuditData;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Table(name = "Role")
@Entity
public class Role implements Serializable{
    @Id
    @GeneratedValue
    private long id;

    @Column(unique = true)
    private String name;

    @ManyToMany(mappedBy = "roles")
    private List<Member> members = new ArrayList<Member>();

    @ManyToMany
    @JoinTable(name = "Role_AccountType",
            joinColumns = { @JoinColumn(name = "role_id") },
            inverseJoinColumns = { @JoinColumn(name = "accountType_id") }
    )
    private List<AccountType>accountTypes = new ArrayList<AccountType>();


    public Role(){}
    public Role(String name){
        this.name = name;
    }

    @Embedded
    private AuditData auditData;
}