package com.assets_france.api;

import com.assets_france.api.domain.Role;
import com.assets_france.api.domain.User;
import com.assets_france.api.sender.EmailSenderService;
import com.assets_france.api.service.RoleService;
import com.assets_france.api.service.UserRoleService;
import com.assets_france.api.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Bean;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.mail.MessagingException;
import java.util.ArrayList;

@SpringBootApplication
public class ApiApplication {

    @Autowired
    private EmailSenderService service;

    public static void main(String[] args) {
        SpringApplication.run(ApiApplication.class, args);
    }

    @EventListener(ApplicationReadyEvent.class)
    public void triggerMail() throws MessagingException {
//        service.sendSimpleEmail(
//                "johnny12doe34@gmail.com",
//                "This is the Email Body...",
//                "This is the Email Subject"
//        );

        service.sentEmailWithAttachment(
                "ishiimasataka@yahoo.fr",
                "This is Email Body with Attachment...",
                "This email has attachment",
                "D:\\Création perso\\Action.pdf"
        );

    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    CommandLineRunner run(UserService userService, RoleService roleService, UserRoleService userRoleService) {
        return args -> {
            roleService.save(new Role(null, "ROLE_USER"));
            roleService.save(new Role(null, "ROLE_PARTNER"));
            roleService.save(new Role(null, "ROLE_ADMIN"));
            roleService.save(new Role(null, "ROLE_SUPER_ADMIN"));

            userService.save(new User(null, "John", " Travolta","john@travolta.com", "1234", new ArrayList<>()));
            userService.save(new User(null, "Jim", " Carrey","jim@carrey.com", "1234", new ArrayList<>()));
            userService.save(new User(null, "Will", "Smith", "will@smith.fr", "7896", new ArrayList<>()));
            userService.save(new User(null, "Masa", "Ishii", "masa@ishii.fr", "123123", new ArrayList<>()));

            userRoleService.addRoleToUser("john@travolta.com", "ROLE_PARTNER");
            userRoleService.addRoleToUser("john@travolta.com", "ROLE_USER");
            userRoleService.addRoleToUser("jim@carrey.com", "ROLE_PARTNER");
            userRoleService.addRoleToUser("will@smith.fr", "ROLE_ADMIN");
            userRoleService.addRoleToUser("masa@ishii.fr", "ROLE_SUPER_ADMIN");
        };
    }
}
