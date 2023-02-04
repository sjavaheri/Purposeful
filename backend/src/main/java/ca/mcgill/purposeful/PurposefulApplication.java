package ca.mcgill.purposeful;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import ca.mcgill.purposeful.configuration.Authority;
import ca.mcgill.purposeful.dao.AppUserRepository;
import ca.mcgill.purposeful.model.AppUser;

/**
 * Main Application
 */
@SpringBootApplication
public class PurposefulApplication {

	public static void main(String[] args) {
		SpringApplication.run(PurposefulApplication.class, args);
	}

	/**
	 * Command Line Runner to initialize database if needed
	 * @param appUserRepository to create the user
	 * @param passwordEncoder to encode the user's password
	 * @return
	 */

	@Bean
	public CommandLineRunner commandLineRunner(AppUserRepository appUserRepository, PasswordEncoder passwordEncoder) { 
		return args -> {

			// create an admin user
			AppUser appUser= new AppUser();
			appUser.setUsername("admin");
			// encode the password
			String encoded = passwordEncoder.encode("admin");
			appUser.setPassword(encoded);
			appUser.getAuthorities().add(Authority.Admin);
			appUserRepository.save(appUser);

			// create a user user
			AppUser appUser2= new AppUser();
			appUser2.setUsername("user");
			// encode the password
			String encoded2 = passwordEncoder.encode("user");
			appUser2.setPassword(encoded2);
			appUser2.getAuthorities().add(Authority.User);
			appUserRepository.save(appUser2);

			
		};
	}
}
