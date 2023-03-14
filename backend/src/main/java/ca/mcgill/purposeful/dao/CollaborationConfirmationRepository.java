package ca.mcgill.purposeful.dao;

import ca.mcgill.purposeful.model.CollaborationConfirmation;
import org.springframework.data.repository.CrudRepository;

/** Repository for CollaborationConfirmation */
public interface CollaborationConfirmationRepository
    extends CrudRepository<CollaborationConfirmation, Integer> {

  CollaborationConfirmation findCollaborationConfirmationById(String id);
}
