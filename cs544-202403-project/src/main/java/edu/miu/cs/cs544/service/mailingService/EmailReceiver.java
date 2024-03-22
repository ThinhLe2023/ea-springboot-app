package edu.miu.cs.cs544.service.mailingService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
public class EmailReceiver {

    @Autowired
    MailService mailService;

    @JmsListener(destination = "queue.email")
    public void receiveMessage(String message) {
        System.out.println("Account balance warning mail sent to : " + message + " successfully");

        // sometimes it gets me connection timeout
     //  this.mailService.sendMail(message, "dsa", "dsadasdsa");
        System.out.println("Sent an email: " + message);
    }
}