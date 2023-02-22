package ca.mcgill.purposeful;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Main Application
 */
@SpringBootApplication
public class PurposefulApplication {

  public static void main(String[] args) {
    SpringApplication.run(PurposefulApplication.class, args);
  }
//
//  /**
//   * Command Line Runner to initialize database if needed
//   *
//   * @param appUserRepository to create the user
//   * @param passwordEncoder   to encode the user's password
//   * @return
//   */
//
//  @Bean
//  public CommandLineRunner commandLineRunner(AppUserRepository appUserRepository,
//      PasswordEncoder passwordEncoder) {
//    return args -> {
//
//      // create a owner user
//      AppUser appUser = new AppUser();
//      appUser.setFirstname("Big");
//      appUser.setLastname("Boss");
//      appUser.setEmail("owner@email.com");
//      // encode the password
//      String encoded = passwordEncoder.encode("owner");
//      appUser.setPassword(encoded);
//      appUser.getAuthorities().add(Authority.Owner);
//      appUserRepository.save(appUser);
//
//      // create a moderator user
//      AppUser appUser1 = new AppUser();
//      appUser1.setFirstname("Marwan");
//      appUser1.setLastname("Kanaan");
//      appUser1.setEmail("moderator@email.com");
//      // encode the password
//      String encoded1 = passwordEncoder.encode("moderator");
//      appUser1.setPassword(encoded1);
//      appUser1.getAuthorities().add(Authority.Moderator);
//      appUserRepository.save(appUser1);
//
//      // create a user user
//      AppUser appUser2 = new AppUser();
//      appUser2.setFirstname("Rob");
//      appUser2.setLastname("Sab");
//      appUser2.setEmail("user@email.com");
//      // encode the password
//      String encoded2 = passwordEncoder.encode("user");
//      appUser2.setPassword(encoded2);
//      appUser2.getAuthorities().add(Authority.User);
//      appUserRepository.save(appUser2);
//
//    };
//  }
}
