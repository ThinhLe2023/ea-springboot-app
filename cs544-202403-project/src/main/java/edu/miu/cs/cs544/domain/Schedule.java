package edu.miu.cs.cs544.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity(name="Schedule")
@Getter
@Setter
@NoArgsConstructor
public class Schedule implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer scheduleId;
    private LocalDate startDate;
    private LocalDate endDate;
    @OneToMany(cascade = CascadeType.PERSIST)
    @JoinColumn(name="schedule_id")
    private List<Session> sessionList= new ArrayList<>();
    public Schedule( LocalDate startDate, LocalDate endDate) {
        this.startDate = startDate;
        this.endDate = endDate;
    }
}
