package edu.miu.cs.cs544.service.mailingService;

import edu.miu.cs.cs544.repository.EventRepository;
import edu.miu.cs.cs544.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class EMailQueueScheduledJob {

    @Autowired
    private  MemberService memberService;
    @Autowired
    private EventRepository eventRepository;
    @Autowired
    private  EmailSender messageSender;

    @Scheduled(fixedRate = 60000)
    public void memberAccountsBalanceChecker() {

        List<String> emails= memberService.findMembersByAccountBalanceLessThanFive().stream()
                .map(c->c.getEmailAddress()).toList();

        for(String email : emails){
            System.out.println(email);
            messageSender.sendMessage(email);
        }

//   messageSender.sendMessage("atefa827@outlook.com");
    }



}

