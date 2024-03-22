package edu.miu.cs.cs544.service.mapper;

import edu.miu.common.service.mapper.BaseMapper;
import edu.miu.cs.cs544.domain.Location;
import edu.miu.cs.cs544.domain.Member;
import edu.miu.cs.cs544.service.contract.LocationPayload;
import edu.miu.cs.cs544.service.contract.MemberPayload;
import ma.glasnost.orika.MapperFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class LocationToLocationPayloadMapper extends BaseMapper<Location, LocationPayload> {
    @Autowired
    public LocationToLocationPayloadMapper(MapperFactory mapperFactory) {
        super(mapperFactory, Location.class, LocationPayload.class);
    }
}
