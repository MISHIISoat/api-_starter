package com.assets_france.api;

import com.assets_france.api.account.domain.dao.AccountDao;
import com.assets_france.api.account.domain.dao.AccountRoleDao;
import com.assets_france.api.account.domain.dao.RoleDao;
import com.assets_france.api.account.domain.entity.Account;
import com.assets_france.api.account.domain.entity.Role;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.HashSet;

@SpringBootApplication
public class ApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(ApiApplication.class, args);
    }

    @Bean
    CommandLineRunner run(AccountDao accountDao, RoleDao roleDao, AccountRoleDao accountRoleDao) {
        return args -> {
            roleDao.save(new Role().setName("ROLE_USER"));
            roleDao.save(new Role().setName("ROLE_PARTNER"));
            roleDao.save(new Role().setName("ROLE_ADMIN"));
            roleDao.save(new Role().setName("ROLE_SUPER_ADMIN"));

            accountDao.save(new Account().setFirstName("John").setLastName("Travolta").setUsername("john@travolta.com").setPassword("1234").setRoles(new HashSet<>()));
            accountDao.save(new Account().setFirstName("Jim").setLastName("Carrey").setUsername("jim@carrey.com").setPassword("1234").setRoles(new HashSet<>()));
            accountDao.save(new Account().setFirstName("Will").setLastName("Smith").setUsername("will@smith.fr").setPassword("7896").setRoles(new HashSet<>()));
            accountDao.save(new Account().setFirstName("Masa").setLastName("Ishii").setUsername("masa@ishii.fr").setPassword("123123").setRoles(new HashSet<>()));

            accountRoleDao.addRoleToUser("john@travolta.com", "ROLE_PARTNER");
            accountRoleDao.addRoleToUser("john@travolta.com", "ROLE_USER");
            accountRoleDao.addRoleToUser("jim@carrey.com", "ROLE_PARTNER");
            accountRoleDao.addRoleToUser("will@smith.fr", "ROLE_ADMIN");
            accountRoleDao.addRoleToUser("masa@ishii.fr", "ROLE_SUPER_ADMIN");
        };
    }
}
