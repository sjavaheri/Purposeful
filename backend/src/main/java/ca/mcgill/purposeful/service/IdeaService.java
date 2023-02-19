package ca.mcgill.purposeful.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import ca.mcgill.purposeful.exception.GlobalException;
import ca.mcgill.purposeful.model.Idea;
import ca.mcgill.purposeful.model.Domain;
import ca.mcgill.purposeful.model.Technology;
import ca.mcgill.purposeful.model.Topic;
import ca.mcgill.purposeful.model.URL;

import java.util.Date;
import java.util.Set;
import java.util.List;

import ca.mcgill.purposeful.dao.IdeaRepository;
import ca.mcgill.purposeful.dao.DomainRepository;
import ca.mcgill.purposeful.dao.TechnologyRepository;
import ca.mcgill.purposeful.dao.TopicRepository;
import ca.mcgill.purposeful.dao.URLRepository;
import ca.mcgill.purposeful.model.Technology;

/**
 * The IdeaService class, the business logic for managing Ideas
 */
@Service
public class IdeaService {
  
  @Autowired
  IdeaRepository ideaRepository;

  @Autowired
  DomainRepository domainRepository;
  
  @Autowired
  TechnologyRepository technologyRepository;

  @Autowired
  TopicRepository topicRepository;

  @Autowired
  URLRepository urlRepository;

  /**
   * Get an idea by its ID
   * 
   * @param id the ID of the idea
   * @throws GlobalException if the idea does not exist
   */
  public Idea getIdeaById(String id) {
    Idea idea = ideaRepository.findIdeaById(id);

    if (idea == null) {
      throw new GlobalException(HttpStatus.NOT_FOUND, "There is no Idea with this ID");
    }

    return idea;
  }

  @Transactional
  public Idea modifyIdea(String id, String title, Date date, String purpose, String descriptions, boolean isPaid, boolean inProgress, boolean isPrivate, Set<Domain> domains, Set<Technology> techs, Set<Topic> topics, List<URL> imgUrls, URL iconUrl){
    // Retrieve idea (we assume that no user can access an idea they don't own because of frontend)
    Idea idea = ideaRepository.findIdeaById(id);

    // Check to make sure essential fields are not empty
    checkEmptyAttributeViolation(title);
    checkEmptyAttributeViolation(purpose);
    checkEmptyAttributeViolation(descriptions);
    checkDomains(domains);
    checkTechs(techs);
    checkTopics(topics);
    checkURLS(imgUrls, iconUrl);

    // Check to see if it is necessary to change boolean fields
    if (idea.isPaid() != isPaid){
      idea.setPaid(isPaid);
    }
    if (idea.isInProgress() != inProgress){
      idea.setInProgress(inProgress);
    }
    if (idea.isPrivate() != isPrivate){
      idea.setPrivate(isPrivate);
    }

    // Change all remaining attributes
    if (title != null){
      idea.setTitle(title);
    }
    if (descriptions != null){
      idea.setTitle(descriptions);
    }
    if (purpose != null){
      idea.setPurpose(purpose);
    }
    if (date != null){
      idea.setDate(date);
    }
    if (domains != null){
      idea.setDomains(domains);
    }
    if (techs != null) {
      idea.setTechs(techs);
    }
    if (topics != null){
      idea.setTopics(topics);
    }
    if (imgUrls != null){
      idea.setSupportingImageUrls(imgUrls);
    }
    if (iconUrl != null){
      idea.setIconUrl(iconUrl);
    }

    ideaRepository.save(idea);

    return idea;
  }

  public void checkEmptyAttributeViolation(String newValue){
    if(newValue != null){
      if (newValue.isEmpty()){
        throw new GlobalException(HttpStatus.BAD_REQUEST,
            "Necessary fields have been left empty");
      }
    }
  }

  public void checkDomains(Set<Domain> domains){
    if (domains != null){
      for (Domain domain : domains){
        try {
          domainRepository.findDomainById(domain.getId());
        } catch (Exception e) {
          throw new GlobalException(HttpStatus.BAD_REQUEST,
          "You are attempting to link your idea to an object that does not exist");
        }
      }
    }
  }

  public void checkTechs(Set<Technology> techs){
    if (techs != null){
      for (Technology tech : techs){
        try {
          technologyRepository.findTechnologyById(tech.getId());
        } catch (Exception e) {
          throw new GlobalException(HttpStatus.BAD_REQUEST,
          "You are attempting to link your idea to an object that does not exist");
        }
      }
    }
  }

  public void checkTopics(Set<Topic> topics){
    if (topics != null){
      for (Topic topic : topics){
        try {
          topicRepository.findTopicById(topic.getId());
        } catch (Exception e) {
          throw new GlobalException(HttpStatus.BAD_REQUEST,
          "You are attempting to link your idea to an object that does not exist");
        }
      }
    }
  }

  public void checkURLS(List<URL> imgUrls, URL iconUrl){
    if (iconUrl != null){
      imgUrls.add(iconUrl);
    }
    if(imgUrls != null){
      List<URL> allUrls = imgUrls;
      for (URL url : allUrls){
        try {
          urlRepository.findURLById(url.getId());
        } catch (Exception e) {
          throw new GlobalException(HttpStatus.BAD_REQUEST,
          "You are attempting to link your idea to an object that does not exist");
        }
      }
    }
  }

}
