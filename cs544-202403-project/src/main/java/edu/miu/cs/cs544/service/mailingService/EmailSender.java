package edu.miu.cs.cs544.service.mailingService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
public class EmailSender {

    private final JmsTemplate jmsTemplate;

    @Autowired
    public EmailSender(JmsTemplate jmsTemplate) {
        this.jmsTemplate = jmsTemplate;
    }

    public void sendMessage(String message) {
        jmsTemplate.convertAndSend("queue.email", message);
        System.out.println("Sent message: " + message);
    }
}
