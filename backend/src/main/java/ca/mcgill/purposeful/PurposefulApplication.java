package ca.mcgill.purposeful;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;
import ca.mcgill.purposeful.configuration.Authority;
import ca.mcgill.purposeful.dao.AppUserRepository;
import ca.mcgill.purposeful.dao.DomainRepository;
import ca.mcgill.purposeful.dao.TechnologyRepository;
import ca.mcgill.purposeful.dao.TopicRepository;
import ca.mcgill.purposeful.model.AppUser;
import ca.mcgill.purposeful.model.Domain;
import ca.mcgill.purposeful.model.Technology;
import ca.mcgill.purposeful.model.Topic;

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
        AppUserRepository appUserRepository, PasswordEncoder passwordEncoder, DomainRepository domainRepository, TopicRepository topicRepository, TechnologyRepository technologyRepository) {
      return args -> {
  
        // create an owner at bootstrap
        if (appUserRepository.findAppUserByEmail("owner@gmail.com") == null) {
          AppUser appUser = new AppUser();
          appUser.setFirstname("Big");
          appUser.setLastname("Boss");
          appUser.setEmail("owner@email.com");
          // encode the password
          String encoded = passwordEncoder.encode("owner");
          appUser.setPassword(encoded);
          appUser.getAuthorities().add(Authority.Owner);
          appUserRepository.save(appUser);
        }
        if (appUserRepository.findAppUserByEmail("moderator@email.com") == null) {
          // create a moderator at bootstrap
          AppUser appUser1 = new AppUser();
          appUser1.setFirstname("Marwan");
          appUser1.setLastname("Kanaan");
          appUser1.setEmail("moderator@email.com");
          // encode the password
          String encoded1 = passwordEncoder.encode("moderator");
          appUser1.setPassword(encoded1);
          appUser1.getAuthorities().add(Authority.Moderator);
          appUserRepository.save(appUser1);
        }
        // create a user at bootstrap
        if (appUserRepository.findAppUserByEmail("user@email.com") == null) {
          AppUser appUser2 = new AppUser();
          appUser2.setFirstname("Rob");
          appUser2.setLastname("Sab");
          appUser2.setEmail("user@email.com");
          // encode the password
          String encoded2 = passwordEncoder.encode("user");
          appUser2.setPassword(encoded2);
          appUser2.getAuthorities().add(Authority.User);
          appUserRepository.save(appUser2);
          
          //TODO: Remove
          Domain domain1 = new Domain();
          domain1.setName("Software");
          Domain domain2 = new Domain();
          domain2.setName("Hardware");
          Domain domain3 = new Domain();
          domain3.setName("Other");
          domainRepository.save(domain1);
          domainRepository.save(domain2);
          domainRepository.save(domain3);
          
          Topic topic1 = new Topic();
          topic1.setName("AI");
          Topic topic2 = new Topic();
          topic2.setName("Gaming");
          topicRepository.save(topic1);
          topicRepository.save(topic2);
          
          Technology tech1 = new Technology();
          tech1.setName("Tensorflow");
          Technology tech2 = new Technology();
          tech2.setName("GPT");
          technologyRepository.save(tech1);
          technologyRepository.save(tech2);
        }
      };
    }
}
