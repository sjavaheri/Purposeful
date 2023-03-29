package ca.mcgill.purposeful;

import ca.mcgill.purposeful.configuration.Authority;import ca.mcgill.purposeful.dao.AppUserRepository;import ca.mcgill.purposeful.model.AppUser;import org.springframework.boot.CommandLineRunner;import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;import org.springframework.context.annotation.Bean;import org.springframework.security.crypto.password.PasswordEncoder;

/** Main Application */
@SpringBootApplication
public class PurposefulApplication {

  public static void main(String[] args) {
    SpringApplication.run(PurposefulApplication.class, args);
  }

  /**
   * Command Line Runner to initialize database if needed
   *
   * @param appUserRepository to create the user
   * @param passwordEncoder to encode the user's password
   * @return
   */
    @Bean
    public CommandLineRunner commandLineRunner(
        AppUserRepository appUserRepository, PasswordEncoder passwordEncoder) {
      return args -> {

        // create an owner at bootstrap
        if (appUserRepository.findAppUserByEmail("owner@gmail.com") == null) {
          AppUser appUser = new AppUser();
          appUser.setFirstname("Big");
          appUser.setLastname("Boss");
          appUser.setEmail("owner@gmail.com");
          // encode the password
          String encoded = passwordEncoder.encode("Bigboss1");
          appUser.setPassword(encoded);
          appUser.getAuthorities().add(Authority.Owner);
          appUserRepository.save(appUser);
        }
  //      if (appUserRepository.findAppUserByEmail("moderator@email.com") == null) {
  //        // create a moderator at bootstrap
  //        AppUser appUser1 = new AppUser();
  //        appUser1.setFirstname("Marwan");
  //        appUser1.setLastname("Kanaan");
  //        appUser1.setEmail("moderator@email.com");
  //        // encode the password
  //        String encoded1 = passwordEncoder.encode("moderator");
  //        appUser1.setPassword(encoded1);
  //        appUser1.getAuthorities().add(Authority.Moderator);
  //        appUserRepository.save(appUser1);
  //      }
  //      // create a user at bootstrap
  //      if (appUserRepository.findAppUserByEmail("user@email.com") == null) {
  //        AppUser appUser2 = new AppUser();
  //        appUser2.setFirstname("Rob");
  //        appUser2.setLastname("Sab");
  //        appUser2.setEmail("user@email.com");
  //        // encode the password
  //        String encoded2 = passwordEncoder.encode("user");
  //        appUser2.setPassword(encoded2);
  //        appUser2.getAuthorities().add(Authority.User);
  //        appUserRepository.save(appUser2);
  //      }
      };
    }
}
