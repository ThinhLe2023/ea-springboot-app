package edu.miu.cs.cs544.service.mapper;

import edu.miu.common.service.mapper.BaseMapper;
import edu.miu.cs.cs544.domain.Event;
import edu.miu.cs.cs544.domain.Member;
import edu.miu.cs.cs544.service.contract.EventPayload;
import edu.miu.cs.cs544.service.contract.MemberPayload;
import ma.glasnost.orika.MapperFactory;
import org.springframework.stereotype.Component;

@Component
public class EventToEventPayLoadMapper extends BaseMapper<Event, EventPayload> {

    public EventToEventPayLoadMapper(MapperFactory mapperFactory) {
        super(mapperFactory, Event.class, EventPayload.class);
    }
}
