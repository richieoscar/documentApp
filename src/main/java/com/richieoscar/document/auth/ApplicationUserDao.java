package com.richieoscar.document.auth;

import java.util.Optional;

public interface ApplicationUserDao {
     Optional<ApplicationUser> selectApplicationUserByUsername(String username);
}
