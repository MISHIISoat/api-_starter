package com.assets_france.api;

import com.assets_france.api.account.domain.dao.AccountDao;
import com.assets_france.api.account.domain.dao.AccountRoleDao;
import com.assets_france.api.account.domain.dao.RoleDao;
import com.assets_france.api.account.infrastructure.dataprovider.entity.JpaRole;
import com.assets_france.api.account.infrastructure.dataprovider.entity.JpaUser;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.ArrayList;

@SpringBootApplication
public class ApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(ApiApplication.class, args);
    }

    @Bean
    CommandLineRunner run(AccountDao accountDao, RoleDao roleDao, AccountRoleDao accountRoleDao) {
        return args -> {
            roleDao.save(new JpaRole(null, "ROLE_USER"));
            roleDao.save(new JpaRole(null, "ROLE_PARTNER"));
            roleDao.save(new JpaRole(null, "ROLE_ADMIN"));
            roleDao.save(new JpaRole(null, "ROLE_SUPER_ADMIN"));

            accountDao.save(new JpaUser(null, "John", " Travolta","john@travolta.com", "1234", new ArrayList<>()));
            accountDao.save(new JpaUser(null, "Jim", " Carrey","jim@carrey.com", "1234", new ArrayList<>()));
            accountDao.save(new JpaUser(null, "Will", "Smith", "will@smith.fr", "7896", new ArrayList<>()));
            accountDao.save(new JpaUser(null, "Masa", "Ishii", "masa@ishii.fr", "123123", new ArrayList<>()));

            accountRoleDao.addRoleToUser("john@travolta.com", "ROLE_PARTNER");
            accountRoleDao.addRoleToUser("john@travolta.com", "ROLE_USER");
            accountRoleDao.addRoleToUser("jim@carrey.com", "ROLE_PARTNER");
            accountRoleDao.addRoleToUser("will@smith.fr", "ROLE_ADMIN");
            accountRoleDao.addRoleToUser("masa@ishii.fr", "ROLE_SUPER_ADMIN");
        };
    }
}
