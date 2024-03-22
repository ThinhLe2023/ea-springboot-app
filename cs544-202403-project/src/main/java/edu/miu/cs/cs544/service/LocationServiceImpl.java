package edu.miu.cs.cs544.service;

import edu.miu.common.service.BaseReadWriteServiceImpl;
import edu.miu.cs.cs544.domain.Location;
import edu.miu.cs.cs544.domain.Member;
import edu.miu.cs.cs544.service.LocationService;
import edu.miu.cs.cs544.service.MemberService;
import edu.miu.cs.cs544.service.contract.LocationPayload;
import edu.miu.cs.cs544.service.contract.MemberPayload;
import org.springframework.stereotype.Service;

@Service
public class LocationServiceImpl extends BaseReadWriteServiceImpl<LocationPayload, Location, Long> implements LocationService {
}
