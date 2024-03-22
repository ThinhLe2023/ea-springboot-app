package edu.miu.cs.cs544;

import edu.miu.cs.cs544.domain.*;
import edu.miu.cs.cs544.service.*;
import edu.miu.cs.cs544.service.contract.*;
import edu.miu.cs.cs544.service.mapper.SessionPayloadToSessionMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@SpringBootApplication(scanBasePackages = {"edu.miu.common", "edu.miu.cs.cs544"})
@EnableJms
@EnableScheduling
public class Application implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println("Scanning Badge system is started.");
    }
}