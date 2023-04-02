package ca.mcgill.purposeful;

import ca.mcgill.purposeful.configuration.Authority;import ca.mcgill.purposeful.dao.AppUserRepository;
import ca.mcgill.purposeful.dao.DomainRepository;
import ca.mcgill.purposeful.dao.TechnologyRepository;
import ca.mcgill.purposeful.dao.TopicRepository;
import ca.mcgill.purposeful.model.AppUser;
import ca.mcgill.purposeful.model.Domain;
import ca.mcgill.purposeful.model.Technology;
import ca.mcgill.purposeful.model.Topic;
import org.springframework.boot.CommandLineRunner;import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;import org.springframework.context.annotation.Bean;import org.springframework.security.crypto.password.PasswordEncoder;

/** Main Application */
@SpringBootApplication
public class PurposefulApplication {
    /**
     * Main method
     *
     * @param args the arguments
     */
  public static void main(String[] args) {
    SpringApplication.run(PurposefulApplication.class, args);
  }

  /**
   * Command Line Runner to initialize database if needed
   *
   * @param appUserRepository to create the user
   * @param passwordEncoder to encode the user's password
   * @param technologyRepository to create the technologies
   * @param domainRepository to create the domains
   * @param topicRepository to create the topics
   *
   * @return the command line runner
   */
    @Bean
    public CommandLineRunner commandLineRunner(
            AppUserRepository appUserRepository, PasswordEncoder passwordEncoder, TechnologyRepository technologyRepository, DomainRepository domainRepository, TopicRepository topicRepository) {
    return args -> {

      /**
       * OWNER CREATION AT BOOTSTRAP
       */
      // create an owner at bootstrap
      if (appUserRepository.findAppUserByEmail("robert.sabourin@gmail.com") == null) {
        AppUser appUser = new AppUser();
        appUser.setFirstname("Robert");
        appUser.setLastname("Sabourin");
        appUser.setEmail("robert.sabourin@gmail.com");
        // encode the password
        String encoded = passwordEncoder.encode("Bestprof1");
        appUser.setPassword(encoded);
        appUser.getAuthorities().add(Authority.Owner);
        appUserRepository.save(appUser);
      }

      /**
       * TECHNOLOGIES CREATION AT BOOTSTRAP
       */
      // create the following techs at bootstrap:
      // Spring boot
      if (technologyRepository.findTechnologyByName("Spring boot") == null) {
        // Initialize the name
        String str = "Spring boot";

        // Create a technology
        Technology technology = new Technology();
        technology.setName(str);

        // Save the technology and extract the id
        technologyRepository.save(technology);
      }
      // Java
      if (technologyRepository.findTechnologyByName("Java") == null) {
          // Initialize the name
          String str = "Java";

          // Create a technology
          Technology technology = new Technology();
          technology.setName(str);

          // Save the technology and extract the id
          technologyRepository.save(technology);
      }
      // C++
      if (technologyRepository.findTechnologyByName("C++") == null) {
              // Initialize the name
              String str = "C++";

              // Create a technology
              Technology technology = new Technology();
              technology.setName(str);

              // Save the technology and extract the id
              technologyRepository.save(technology);
      }
      // C#
      if (technologyRepository.findTechnologyByName("C#") == null) {
            // Initialize the name
            String str = "C#";

            // Create a technology
            Technology technology = new Technology();
            technology.setName(str);

            // Save the technology and extract the id
            technologyRepository.save(technology);
      }
      // Python
      if (technologyRepository.findTechnologyByName("Python") == null) {
              // Initialize the name
              String str = "Python";

              // Create a technology
              Technology technology = new Technology();
              technology.setName(str);

              // Save the technology and extract the id
              technologyRepository.save(technology);
      }
      // JavaScript
      if (technologyRepository.findTechnologyByName("JavaScript") == null) {
              // Initialize the name
              String str = "JavaScript";

              // Create a technology
              Technology technology = new Technology();
              technology.setName(str);

              // Save the technology and extract the id
              technologyRepository.save(technology);
      }
      // HTML
      if (technologyRepository.findTechnologyByName("HTML") == null) {
              // Initialize the name
              String str = "HTML";

              // Create a technology
              Technology technology = new Technology();
              technology.setName(str);

              // Save the technology and extract the id
              technologyRepository.save(technology);
      }
      // CSS
      if (technologyRepository.findTechnologyByName("CSS") == null) {
              // Initialize the name
              String str = "CSS";

              // Create a technology
              Technology technology = new Technology();
              technology.setName(str);

              // Save the technology and extract the id
              technologyRepository.save(technology);
      }
      // SQL
      if (technologyRepository.findTechnologyByName("SQL") == null) {
              // Initialize the name
              String str = "SQL";

              // Create a technology
              Technology technology = new Technology();
              technology.setName(str);

              // Save the technology and extract the id
              technologyRepository.save(technology);
      }
      // NoSQL
      if (technologyRepository.findTechnologyByName("NoSQL") == null) {
              // Initialize the name
              String str = "NoSQL";

              // Create a technology
              Technology technology = new Technology();
              technology.setName(str);

              // Save the technology and extract the id
              technologyRepository.save(technology);
      }
      // React
      if (technologyRepository.findTechnologyByName("React") == null) {
              // Initialize the name
              String str = "React";

              // Create a technology
              Technology technology = new Technology();
              technology.setName(str);

              // Save the technology and extract the id
              technologyRepository.save(technology);
      }
      // Angular
      if (technologyRepository.findTechnologyByName("Angular") == null) {
              // Initialize the name
              String str = "Angular";

              // Create a technology
              Technology technology = new Technology();
              technology.setName(str);

              // Save the technology and extract the id
              technologyRepository.save(technology);
      }
      // Vue
      if (technologyRepository.findTechnologyByName("Vue") == null) {
        // Initialize the name
        String str = "Vue";

        // Create a technology
        Technology technology = new Technology();
        technology.setName(str);

        // Save the technology and extract the id
        technologyRepository.save(technology);
      }
      // Node
      if (technologyRepository.findTechnologyByName("Node") == null) {
        // Initialize the name
        String str = "Node";

        // Create a technology
        Technology technology = new Technology();
        technology.setName(str);

        // Save the technology and extract the id
        technologyRepository.save(technology);
      }
      // Express
      if (technologyRepository.findTechnologyByName("Express") == null) {
        // Initialize the name
        String str = "Express";

        // Create a technology
        Technology technology = new Technology();
        technology.setName(str);

        // Save the technology and extract the id
        technologyRepository.save(technology);
      }
      // MongoDB
      if (technologyRepository.findTechnologyByName("MongoDB") == null) {
        // Initialize the name
        String str = "MongoDB";

        // Create a technology
        Technology technology = new Technology();
        technology.setName(str);

        // Save the technology and extract the id
        technologyRepository.save(technology);
      }
      // MySQL
      if (technologyRepository.findTechnologyByName("MySQL") == null) {
        // Initialize the name
        String str = "MySQL";

        // Create a technology
        Technology technology = new Technology();
        technology.setName(str);

        // Save the technology and extract the id
        technologyRepository.save(technology);
      }
      // PostgreSQL
      if (technologyRepository.findTechnologyByName("PostgreSQL") == null) {
        // Initialize the name
        String str = "PostgreSQL";

        // Create a technology
        Technology technology = new Technology();
        technology.setName(str);

        // Save the technology and extract the id
        technologyRepository.save(technology);
      }
      // Docker
      if (technologyRepository.findTechnologyByName("Docker") == null) {
        // Initialize the name
        String str = "Docker";

        // Create a technology
        Technology technology = new Technology();
        technology.setName(str);

        // Save the technology and extract the id
        technologyRepository.save(technology);
      }
      // AWS
      if (technologyRepository.findTechnologyByName("AWS") == null) {
          // Initialize the name
          String str = "AWS";

          // Create a technology
          Technology technology = new Technology();
          technology.setName(str);

          // Save the technology and extract the id
          technologyRepository.save(technology);
      }
      // Linux
      if (technologyRepository.findTechnologyByName("Linux") == null) {
          // Initialize the name
          String str = "Linux";

          // Create a technology
          Technology technology = new Technology();
          technology.setName(str);

          // Save the technology and extract the id
          technologyRepository.save(technology);
      }
      // Git
      if (technologyRepository.findTechnologyByName("Git") == null) {
          // Initialize the name
          String str = "Git";

          // Create a technology
          Technology technology = new Technology();
          technology.setName(str);

          // Save the technology and extract the id
          technologyRepository.save(technology);
      }
      // GitHub
      if (technologyRepository.findTechnologyByName("GitHub") == null) {
          // Initialize the name
          String str = "GitHub";

          // Create a technology
          Technology technology = new Technology();
          technology.setName(str);

          // Save the technology and extract the id
          technologyRepository.save(technology);
      }

      /**
       * DOMAIN CREATION
       */
      // create domains at bootstrap
      if (domainRepository.findDomainByName("Software Engineering") == null) {
        // create a domain at bootstrap
        Domain domain = new Domain();
        domain.setName("Software Engineering");
        domainRepository.save(domain);
      }


      /**
       * TOPICS CREATION
       */
      // create the following topics at bootstrap:
      // Software Architecture
        if (topicRepository.findTopicByName("Software Architecture") == null) {
            // create a topic at bootstrap
            Topic topic = new Topic();
            topic.setName("Software Architecture");
            topicRepository.save(topic);
        }
      // Software Testing
        if (topicRepository.findTopicByName("Software Testing") == null) {
            // create a topic at bootstrap
            Topic topic = new Topic();
            topic.setName("Software Testing");
            topicRepository.save(topic);
        }
        // Software Development
        if (topicRepository.findTopicByName("Software Development") == null) {
            // create a topic at bootstrap
            Topic topic = new Topic();
            topic.setName("Software Development");
            topicRepository.save(topic);
        }
        // Software Design
        if (topicRepository.findTopicByName("Software Design") == null) {
            // create a topic at bootstrap
            Topic topic = new Topic();
            topic.setName("Software Design");
            topicRepository.save(topic);
        }
        // Software Maintenance
        if (topicRepository.findTopicByName("Software Maintenance") == null) {
            // create a topic at bootstrap
            Topic topic = new Topic();
            topic.setName("Software Maintenance");
            topicRepository.save(topic);
        }
        // Software Requirements
        if (topicRepository.findTopicByName("Software Requirements") == null) {
            // create a topic at bootstrap
            Topic topic = new Topic();
            topic.setName("Software Requirements");
            topicRepository.save(topic);
        }
        // Software Configuration Management
        if (topicRepository.findTopicByName("Software Configuration Management") == null) {
            // create a topic at bootstrap
            Topic topic = new Topic();
            topic.setName("Software Configuration Management");
            topicRepository.save(topic);
        }
        // Software Project Management
        if (topicRepository.findTopicByName("Software Project Management") == null) {
            // create a topic at bootstrap
            Topic topic = new Topic();
            topic.setName("Software Project Management");
            topicRepository.save(topic);
        }
        // Software Quality Assurance
        if (topicRepository.findTopicByName("Software Quality Assurance") == null) {
            // create a topic at bootstrap
            Topic topic = new Topic();
            topic.setName("Software Quality Assurance");
            topicRepository.save(topic);
        }
    };
    }
}
