package edu.miu.cs.cs544.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity(name="Event")
@Setter
@Getter
@NoArgsConstructor
public class Event implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer eventId;
    private String name;
    private String description;
    @OneToMany (mappedBy = "event", cascade = CascadeType.PERSIST)
    List<Session> sessions=new ArrayList<>();

    @ManyToMany
    @Column(name="member_id")
    private List<Member> memberList  =new ArrayList<>();
    public Event(String name, String description) {
        this.name = name;
        this.description = description;
    }
}
