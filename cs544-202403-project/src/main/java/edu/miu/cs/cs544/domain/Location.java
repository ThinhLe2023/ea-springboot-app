package edu.miu.cs.cs544.domain;

import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;

@Data
@Entity
@Table(name = "Location")

public class Location implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "LocationID")
    private long Id;
    private String name;
    private String Description;

    @Enumerated(EnumType.STRING)
    private LocationType Type;

    public Location (){}

    public Location(String name , String description , LocationType type) {
        this.name = name;
        this.Description = description;
        this.Type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public LocationType getType() {
        return Type;
    }

    public void setType(LocationType type) {
        Type = type;
    }
}
