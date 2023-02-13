package ca.mcgill.purposeful.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;


import java.time.Instant;
import java.util.Date;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import ca.mcgill.purposeful.model.Idea;
import ca.mcgill.purposeful.model.RegularUser;
import ca.mcgill.purposeful.model.Reaction;
import ca.mcgill.purposeful.model.AppUser;
import ca.mcgill.purposeful.model.URL;


@SpringBootTest
public class ReactionRepositoryTests {

	@Autowired
	private IdeaRepository ideaRepository;

	@Autowired
	private ReactionRepository reactionRepository;

	@Autowired
	private AppUserRepository appUserRepository;

	@Autowired
	private RegularUserRepository regularUserRepository;

	@Autowired
	private URLRepository urlRepository;

	@AfterEach
    public void clearDatabase() {
        // clean up db after each test execution
		// clean up needs to happen in right order
        reactionRepository.deleteAll();
        ideaRepository.deleteAll();
		urlRepository.deleteAll();
        regularUserRepository.deleteAll();
        appUserRepository.deleteAll();
    }

	@Test 
	  public void testPersistAndLoadReaction() { 
		
		// create basic URL 
		URL url = new URL();
		url.setURL("www.url.com");

        // Create basic idea
		Idea idea = new Idea();
		idea.setDate(Date.from(Instant.now()));
		idea.setTitle("Brilliant Idea");
        idea.setPurpose("huge learning experience");
		idea.setDescription("It's a good idea");
		idea.setIconUrl(url);;

        
        // Create reaction
        Reaction reaction = new Reaction();
        reaction.setReactionType(Reaction.ReactionType.HighFive);
        reaction.setDate(Date.from(Instant.now()));
        reaction.setIdea(idea);

        // Create app user
		AppUser user = new AppUser();
		user.setEmail("friend@gmail.com");
		user.setUsername("friendly");
		user.setPassword("person");

        // Create corresponding regular user
		RegularUser regUser = new RegularUser();
		regUser.setAppUser(user);
		regUser.setVerifiedCompany(false);

		// set user for idea
		idea.setUser(regUser);

		// set user for reaction
		reaction.setRegularUser(regUser);

        // Save objects in database in the right order
		urlRepository.save(url);
		appUserRepository.save(user);
		regularUserRepository.save(regUser);
        ideaRepository.save(idea); 
		reactionRepository.save(reaction);

        // Retrieve reaction from database
        String reactId = reaction.getId();

        // Test found reaction in database
        Reaction foundReaction = reactionRepository.findReactionById(reactId);

	    assertNotNull(foundReaction);
	    assertEquals(foundReaction.getId(), reactId);
	    assertEquals(foundReaction.getDate().compareTo(reaction.getDate()), 0);
	    assertEquals(foundReaction.getIdea().getId(), idea.getId());
	}
}