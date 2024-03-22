package edu.miu.cs.cs544.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import org.springframework.scheduling.annotation.Scheduled;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Entity
@Table(name = "session")
@Data
public class Session implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "sessionId")
    private Integer sessionId;

    @Column(name = "date")
    private LocalDate date;
    @Column(name = "start_time")
    private LocalTime startTime;
    @Column(name = "end_time")
    private LocalTime endTime;
    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "e_id")
    private Event event;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "sid")
    private List<Attendance> attendances;
}
