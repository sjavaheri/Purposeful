package ca.mcgill.purposeful.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ca.mcgill.purposeful.exception.GlobalException;
import ca.mcgill.purposeful.model.Idea;
import ca.mcgill.purposeful.model.Domain;
import ca.mcgill.purposeful.model.Technology;
import ca.mcgill.purposeful.model.Topic;
import ca.mcgill.purposeful.model.URL;

import java.util.Date;
import java.util.Set;
import java.util.HashSet;
import java.util.List;
import java.util.ArrayList;

import ca.mcgill.purposeful.dao.IdeaRepository;
import ca.mcgill.purposeful.dao.DomainRepository;
import ca.mcgill.purposeful.dao.TechnologyRepository;
import ca.mcgill.purposeful.dao.TopicRepository;
import ca.mcgill.purposeful.dao.URLRepository;

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
  /**
   * Modify an idea based on id
   * 
   * @author Ramin Akhavan
   * @throws GlobalException if necessary field are left empty or if an object does not exist
   */
  public Idea modifyIdea(String id, String title, Date date, String purpose, String descriptions, boolean isPaid, boolean inProgress, boolean isPrivate, List<String> domainIds, List<String> techIds, List<String> topicIds, List<String> imgUrlIds, String iconUrlId){
    // Retrieve idea (we assume that no user can access an idea they don't own because of frontend)
    Idea idea = getIdeaById(id);

    // Check to make sure essential fields are not empty
    checkEmptyAttributeViolation(title);
    checkEmptyAttributeViolation(purpose);
    checkEmptyAttributeViolation(descriptions);

    // Check to see if all objects exists
    Set<Domain> domains = checkDomains(domainIds);
    Set<Technology> techs = checkTechs(techIds);
    Set<Topic> topics = checkTopics(topicIds);
    List<URL> imgUrls = checkImgURLS(imgUrlIds);
    URL iconUrl = checkURL(iconUrlId);

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

    // See if date changed
    if (date.compareTo(idea.getDate()) != 0){
      idea.setDate(date);
    }
    idea.setDomains(domains);
    idea.setTechs(techs);
    idea.setTopics(topics);
    idea.setSupportingImageUrls(imgUrls);
    idea.setIconUrl(iconUrl);

    // Save updated idea in the repository
    ideaRepository.save(idea);

    return idea;
  }

  /**
   * Check to make sure a necessary field is not empty
   * 
   * @author Ramin Akhavan
   * @throws GlobalException if necessary field is left empty
   */
  public void checkEmptyAttributeViolation(String newValue){
    if(newValue != null){
      if (newValue.isEmpty()){
        throw new GlobalException(HttpStatus.BAD_REQUEST,
            "Necessary fields have been left empty");
      }
    }
  }

  /**
   * Check to make sure all domains of an idea exist
   * 
   * @author Ramin Akhavan
   * @throws GlobalException if an object does not exist
   */
  public Set<Domain> checkDomains(List<String> domainIds){
    Domain domain = null;
    Set<Domain> domains = new HashSet<Domain>();
    if (domainIds != null){
      for (String id : domainIds){
        try {
          domain = domainRepository.findDomainById(id);
          domains.add(domain);
        } catch (Exception e) {
          throw new GlobalException(HttpStatus.BAD_REQUEST,
          "You are attempting to link your idea to an object that does not exist");
        }
      }
    }
    return domains;
  }

  /**
   * Check to make sure all technologies of an idea exist
   * 
   * @author Ramin Akhavan
   * @throws GlobalException if an object does not exist
   */
  public Set<Technology> checkTechs(List<String> techIds){
    Technology tech = null;
    Set<Technology> techs = new HashSet<Technology>();
    if (techIds != null){
      for (String id : techIds){
        try {
          tech = technologyRepository.findTechnologyById(id);
          techs.add(tech);
        } catch (Exception e) {
          throw new GlobalException(HttpStatus.BAD_REQUEST,
          "You are attempting to link your idea to an object that does not exist");
        }
      }
    }
    return techs;
  }

  /**
   * Check to make sure all topics of an idea exist
   * 
   * @author Ramin Akhavan
   * @throws GlobalException if an object does not exist
   */
  public Set<Topic> checkTopics(List<String> topicIds){
    Topic topic = null;
    Set<Topic> topics = new HashSet<Topic>();
    if (topics != null){
      for (String id : topicIds){
        try {
          topic = topicRepository.findTopicById(id);
          topics.add(topic);
        } catch (Exception e) {
          throw new GlobalException(HttpStatus.BAD_REQUEST,
          "You are attempting to link your idea to an object that does not exist");
        }
      }
    }
    return topics;
  }

  /**
   * Check to make sure all image urls exist
   * 
   * @author Ramin Akhavan
   * @throws GlobalException if an object does not exist
   */
  public List<URL> checkImgURLS(List<String> imgUrlIds){
    List<URL> urls = new ArrayList<URL>();
    if(imgUrlIds != null){
      for (String id : imgUrlIds){
        urls.add(checkURL(id));
      }
    }
    return urls;
  }

  /**
   * Check to make sure a url exists
   * 
   * @author Ramin Akhavan
   * @throws GlobalException if an object does not exist
   */
  public URL checkURL(String urlId){
    URL url = null;
    try {
      url = urlRepository.findURLById(urlId);
    } catch (Exception e) {
      throw new GlobalException(HttpStatus.BAD_REQUEST,
      "You are attempting to link your idea to an object that does not exist");
    }
    return url;
  }
}
