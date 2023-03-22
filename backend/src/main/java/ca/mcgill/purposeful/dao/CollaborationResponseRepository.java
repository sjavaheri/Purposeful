package ca.mcgill.purposeful.dao;

import ca.mcgill.purposeful.model.CollaborationResponse;
import org.springframework.data.repository.CrudRepository;

/** Repository for CollaborationConfirmation */
public interface CollaborationResponseRepository
    extends CrudRepository<CollaborationResponse, Integer> {

  CollaborationResponse findCollaborationResponseById(String id);
}
