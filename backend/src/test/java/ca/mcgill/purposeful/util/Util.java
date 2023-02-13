package ca.mcgill.purposeful.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import ca.mcgill.purposeful.dao.AppUserRepository;
import ca.mcgill.purposeful.dao.DomainRepository;
import ca.mcgill.purposeful.dao.IdeaRepository;
import ca.mcgill.purposeful.dao.ModeratorRepository;
import ca.mcgill.purposeful.dao.OwnerRepository;
import ca.mcgill.purposeful.dao.ReactionRepository;
import ca.mcgill.purposeful.dao.RegularUserRepository;
import ca.mcgill.purposeful.dao.TechnologyRepository;
import ca.mcgill.purposeful.dao.TopicRepository;
import ca.mcgill.purposeful.dao.URLRepository;
import ca.mcgill.purposeful.dao.VerificationRequestRepository;

/**
 * Class for resuable methods needed across other classes
 */
@Configuration
public class Util {

    @Autowired
    private AppUserRepository appUserRepository;

    @Autowired
    private DomainRepository domainRepository;

    @Autowired
    private IdeaRepository ideaRepository;

    @Autowired
    private ModeratorRepository moderatorRepository;

    @Autowired
    private OwnerRepository ownerRepository;

    @Autowired
    private ReactionRepository reactionRepository;

    @Autowired
    private RegularUserRepository regularUserRepository;

    @Autowired
    private TechnologyRepository technologyRepository;

    @Autowired
    private TopicRepository topicRepository;

    @Autowired
    private URLRepository urlRepository;

    @Autowired
    private VerificationRequestRepository verificationRequestRepository;

    /**
     * Method to clear the database completely
     * 
     * @Author Shidan Javaheri
     */
    public void clearDatabase() {
        reactionRepository.deleteAll();
        ideaRepository.deleteAll();
        appUserRepository.deleteAll();
        domainRepository.deleteAll();
        moderatorRepository.deleteAll();
        ownerRepository.deleteAll();
        regularUserRepository.deleteAll();
        technologyRepository.deleteAll();
        topicRepository.deleteAll();
        urlRepository.deleteAll();
        verificationRequestRepository.deleteAll();
    }

}
