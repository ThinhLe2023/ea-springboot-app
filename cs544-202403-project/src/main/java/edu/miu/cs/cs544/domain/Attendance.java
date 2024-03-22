package edu.miu.cs.cs544.domain;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Attendance")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Attendance implements Serializable {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Integer attendanceId;

    @ManyToOne
    @JoinColumn(name = "scanner_id",referencedColumnName ="id")
    private Scanner scanner;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne
    @JoinColumn(name = "sid", referencedColumnName = "sessionId")
    private Session session;

    @OneToMany
    private List<Member> members=new ArrayList<>();
    private boolean isPresent;
    private LocalDateTime scannedAt;


    public Integer getId() {
        return attendanceId;
    }
    @Column(name="attendance_datetime")
    private LocalDateTime attendanceDateTIme;
}


