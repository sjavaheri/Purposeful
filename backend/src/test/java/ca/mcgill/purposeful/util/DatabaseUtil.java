package ca.mcgill.purposeful.util;

import ca.mcgill.purposeful.dao.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

/** Class for reusable methods needed across other classes */
@Configuration
public class DatabaseUtil {

  @Autowired private AppUserRepository appUserRepository;

  @Autowired private CollaborationConfirmationRepository collaborationConfirmationRepository;

  @Autowired private CollaborationRequestRepository collaborationRequestRepository;

  @Autowired private DomainRepository domainRepository;

  @Autowired private IdeaRepository ideaRepository;

  @Autowired private ModeratorRepository moderatorRepository;

  @Autowired private OwnerRepository ownerRepository;

  @Autowired private ReactionRepository reactionRepository;

  @Autowired private RegularUserRepository regularUserRepository;

  @Autowired private TechnologyRepository technologyRepository;

  @Autowired private TopicRepository topicRepository;

  @Autowired private URLRepository urlRepository;

  @Autowired private VerificationRequestRepository verificationRequestRepository;

  /** Method to clear the database completely @Author Shidan Javaheri */
  public void clearDatabase() {
    collaborationRequestRepository.deleteAll();
    collaborationConfirmationRepository.deleteAll();
    reactionRepository.deleteAll();
    ideaRepository.deleteAll();
    moderatorRepository.deleteAll();
    ownerRepository.deleteAll();
    regularUserRepository.deleteAll();
    appUserRepository.deleteAll();
    domainRepository.deleteAll();
    technologyRepository.deleteAll();
    topicRepository.deleteAll();
    urlRepository.deleteAll();
    verificationRequestRepository.deleteAll();
  }
}
