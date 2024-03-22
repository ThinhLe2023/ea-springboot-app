package edu.miu.cs.cs544.service.contract;

import edu.miu.cs.cs544.domain.Member;
import edu.miu.cs.cs544.domain.Session;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
public class EventPayload implements Serializable {
    private Integer eventId;
    private String name;
    private String description;
    List<Session> sessions=new ArrayList<>();
    private List<Member> memberList  =new ArrayList<>();
}
