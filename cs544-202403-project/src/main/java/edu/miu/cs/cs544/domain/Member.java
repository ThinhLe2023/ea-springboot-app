package edu.miu.cs.cs544.domain;

import edu.miu.common.domain.AuditData;
import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "Member")
public class Member implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstName;

    private String lastName;

    @Column(unique = true)
    private String emailAddress;

    @Column(unique = true)
    private String barcode;

    public Member() {
    }

    public Member(String firstName, String lastName, String emailAddress, String barcode) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.emailAddress = emailAddress;
        this.barcode = barcode;
    }


    @ManyToMany(cascade = CascadeType.MERGE)
    @JoinTable(name = "Member_Role",
            joinColumns = {@JoinColumn(name = "member_id")},
            inverseJoinColumns = {@JoinColumn(name = "role_id")}
    )

    private List<Role> roles = new ArrayList<Role>();

    @ManyToMany
    @JoinTable(name = "Member_Event",
            joinColumns = {@JoinColumn(name = "member_id")},
            inverseJoinColumns = {@JoinColumn(name = "event_id")}
    )
    private List<Event> events = new ArrayList<Event>();

    @OneToMany
    @JoinColumn(name = "member_id")
    private List<Attendance> attendances = new ArrayList<Attendance>();


    @OneToMany(mappedBy = "member")
    private List<Account> accounts = new ArrayList<Account>();


    @Embedded
    private AuditData auditData;

}
