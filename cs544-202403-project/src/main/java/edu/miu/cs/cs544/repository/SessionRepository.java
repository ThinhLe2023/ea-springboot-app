package edu.miu.cs.cs544.repository;


import edu.miu.common.repository.BaseRepository;
import edu.miu.cs.cs544.domain.Session;
import edu.miu.cs.cs544.service.contract.SessionPayload;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface SessionRepository extends BaseRepository<Session, Integer> {
//    List<SessionPayload> findSessionsByEventId(Integer eventId);
}
