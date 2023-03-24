package ca.mcgill.purposeful.dao;

import ca.mcgill.purposeful.model.CollaborationRequest;
import ca.mcgill.purposeful.model.Idea;
import ca.mcgill.purposeful.model.RegularUser;
import java.util.List;
import org.springframework.data.repository.CrudRepository;

/** Repository for CollaborationRequest */
public interface CollaborationRequestRepository
    extends CrudRepository<CollaborationRequest, Integer> {

  CollaborationRequest findCollaborationRequestById(String id);
  List<CollaborationRequest> findCollaborationRequestsByRequesterAndIdea(RegularUser requester, Idea idea);
}
