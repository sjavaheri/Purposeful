package ca.mcgill.purposeful.dao;

import ca.mcgill.purposeful.model.CollaborationRequest;
import org.springframework.data.repository.CrudRepository;

/** Repository for CollaborationRequest */
public interface CollaborationRequestRepository
    extends CrudRepository<CollaborationRequest, Integer> {

  CollaborationRequest findCollaborationRequestById(String id);
}
