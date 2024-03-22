package edu.miu.cs.cs544.service.mapper;



import edu.miu.common.exception.ResourceNotFoundException;
import edu.miu.common.service.mapper.BaseMapper;
import edu.miu.cs.cs544.domain.Attendance;
import edu.miu.cs.cs544.domain.Member;
import edu.miu.cs.cs544.domain.Scanner;
import edu.miu.cs.cs544.domain.Session;
import edu.miu.cs.cs544.repository.MemberRepository;
import edu.miu.cs.cs544.repository.ScannerRepository;
import edu.miu.cs.cs544.repository.SessionRepository;
import edu.miu.cs.cs544.service.contract.AttendancePayload;
import ma.glasnost.orika.MapperFactory;
import org.springframework.stereotype.Component;


@Component
public class AttendancePayloadToAttendanceMapper extends BaseMapper<AttendancePayload, Attendance> {



    private final MemberRepository memberRepository;
    private final SessionRepository sessionRepository;
    private final ScannerRepository scannerRepository;

    public AttendancePayloadToAttendanceMapper(MapperFactory mapperFactory, MemberRepository memberRepository, SessionRepository sessionRepository, ScannerRepository scannerRepository) {
        super(mapperFactory, AttendancePayload.class, Attendance.class);
        this.memberRepository = memberRepository;
        this.sessionRepository = sessionRepository;
        this.scannerRepository = scannerRepository;
    }

    @Override
    protected Attendance customMapping(AttendancePayload source, Attendance target) {
        Member member = memberRepository.findById(source.getMember().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Member not found"));
        target.setMember(member);

        Session session = sessionRepository.findById(source.getSession().getSessionId())
                .orElseThrow(() -> new ResourceNotFoundException("Session not found"));
        target.setSession(session);

        Scanner scanner = scannerRepository.findById(source.getScanner().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Scanner not found"));
        target.setScanner(scanner);

        return target;
    }
}

