package edu.miu.cs.cs544.service.contract;

import edu.miu.cs.cs544.domain.Member;
import jakarta.persistence.ManyToMany;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


@Data
public class RolePayload implements Serializable {
    private Long id;
    private String name;


}
