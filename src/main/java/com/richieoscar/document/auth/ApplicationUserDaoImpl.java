package com.richieoscar.document.auth;

import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import static com.richieoscar.document.security.ApplicationRole.*;

@Repository("fake")
public class ApplicationUserDaoImpl implements ApplicationUserDao{

   private final PasswordEncoder passwordEncoder;

    @Autowired
    public ApplicationUserDaoImpl(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Optional<ApplicationUser> selectApplicationUserByUsername(String username) {
        return getApplicationUsers()
                .stream()
                .filter(applicationUser -> username.equals(applicationUser.getUsername()))
                .findFirst();
    }

    List<ApplicationUser> getApplicationUsers(){
        List<ApplicationUser> users = Lists.newArrayList(
                new ApplicationUser(
                        "richieoscar",
                        passwordEncoder.encode("password"),
                        ADMIN.getAuthorities(),
                        false,
                        true,
                        false,
                        true),
                new ApplicationUser(
                        "alex",
                        passwordEncoder.encode("password"),
                        USER.getAuthorities(),
                        false,
                        false,
                        false,
                        false)

        );
        return users;
    }
}
