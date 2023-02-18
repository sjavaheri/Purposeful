package ca.mcgill.purposeful.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import ca.mcgill.purposeful.dao.IdeaRepository;
import ca.mcgill.purposeful.exception.GlobalException;
import ca.mcgill.purposeful.model.Idea;

/**
 * The IdeaService class, the business logic for managing Ideas
 */
@Service
public class IdeaService {
  @Autowired
  IdeaRepository ideaRepository;

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
}
