package edu.miu.cs.cs544.service;

import edu.miu.common.service.BaseReadWriteService;
import edu.miu.cs.cs544.domain.Location;
import edu.miu.cs.cs544.domain.Member;
import edu.miu.cs.cs544.service.contract.LocationPayload;
import edu.miu.cs.cs544.service.contract.MemberPayload;

public interface LocationService extends BaseReadWriteService<LocationPayload, Location, Long> {
}
