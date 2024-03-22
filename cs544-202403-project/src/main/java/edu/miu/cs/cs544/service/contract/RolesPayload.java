package edu.miu.cs.cs544.service.contract;

import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
public class RolesPayload implements Serializable {
    List<RolePayload>  roles = new ArrayList<RolePayload>();
}
