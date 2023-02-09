package ca.mcgill.purposeful.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import ca.mcgill.purposeful.dao.AppUserRepository;
import ca.mcgill.purposeful.model.AppUser;
import ca.mcgill.purposeful.model.SecurityUser;

@Service
public class AppUserService implements UserDetailsService {

  @Autowired
  AppUserRepository appUserRepository;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

    // look for user in database. Tell spring how to get the user
    AppUser appUser = appUserRepository.findAppUserByUsername(username);
    if (appUser == null) {
      throw new UsernameNotFoundException("User not found");
    }
    // security user is an implementation of UserDetails which already knows how to check the password and whether it matches the given username
    return new SecurityUser(appUser);
  }


}
