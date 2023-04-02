package ca.mcgill.purposeful.dao;

import ca.mcgill.purposeful.model.CollaborationRequest;
import ca.mcgill.purposeful.model.CollaborationResponse;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

/** Repository for CollaborationResponse */
public interface CollaborationResponseRepository
    extends CrudRepository<CollaborationResponse, Integer> {

  /**
   * Find a CollaborationResponse by id
   * @param id - the id of the CollaborationResponse
   * @return the CollaborationResponse with the given id
   */
  CollaborationResponse findCollaborationResponseById(String id);
}
