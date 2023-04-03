package ca.mcgill.purposeful.dao;

import ca.mcgill.purposeful.model.CollaborationRequest;
import ca.mcgill.purposeful.model.Idea;
import ca.mcgill.purposeful.model.RegularUser;
import java.util.List;
import org.springframework.data.repository.CrudRepository;

/** Repository for CollaborationRequest */
public interface CollaborationRequestRepository
    extends CrudRepository<CollaborationRequest, Integer> {

  /**
   * Find a CollaborationRequest by id
   *
   * @param id - the id of the CollaborationRequest
   * @return the CollaborationRequest with the given id
   */
  CollaborationRequest findCollaborationRequestById(String id);

  /**
   * Find a CollaborationRequest by requester and idea
   *
   * @param requester - the requester of the CollaborationRequest
   * @param idea - the idea of the CollaborationRequest
   * @return the CollaborationRequest with the given requester and idea
   */
  List<CollaborationRequest> findCollaborationRequestsByRequesterAndIdea(
      RegularUser requester, Idea idea);

  /**
   * Find all CollaborationRequests by Idea
   *
   * @param idea - the idea of the CollaborationRequest
   * @return a list of all CollaborationRequests
   */
  List<CollaborationRequest> findCollaborationRequestsByIdea(Idea idea);
}
