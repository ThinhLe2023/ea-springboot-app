package edu.miu.cs.cs544.service.contract;

import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
public class MemberPayload implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
    private String firstName;

    private String lastName;

    private String emailAddress;

    private String barcode;

    private List<RolePayload> roles = new ArrayList<RolePayload>();
}
