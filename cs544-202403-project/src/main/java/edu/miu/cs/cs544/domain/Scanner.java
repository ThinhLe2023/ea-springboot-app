package edu.miu.cs.cs544.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.*;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import java.io.Serializable;

@Data
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Scanner implements Serializable {
    @Id
    @GeneratedValue
    private Integer id;

    private String name;

    @ManyToOne
    @Cascade({CascadeType.MERGE, CascadeType.PERSIST})
    private Location location;

    @ManyToOne
    private AccountType accountType;
}
