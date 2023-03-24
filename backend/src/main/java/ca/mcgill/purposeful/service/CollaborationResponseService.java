package ca.mcgill.purposeful.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import ca.mcgill.purposeful.dao.CollaborationRequestRepository;
import ca.mcgill.purposeful.dao.CollaborationResponseRepository;
import ca.mcgill.purposeful.dao.RegularUserRepository;
import ca.mcgill.purposeful.exception.GlobalException;
import ca.mcgill.purposeful.model.CollaborationRequest;
import ca.mcgill.purposeful.model.CollaborationResponse;
import ca.mcgill.purposeful.model.Idea;
import ca.mcgill.purposeful.model.RegularUser;
import jakarta.transaction.Transactional;

/**
 * Collaboration response service functions
 */
public class CollaborationResponseService {
    @Autowired
    CollaborationResponseRepository collaborationResponseRepository;

    @Autowired
    CollaborationRequestRepository collaborationRequestRepository;

    @Autowired
    IdeaService ideaService;

    @Autowired 
    RegularUserRepository regularUserRepository;

    /**
     * Get the collaboration response for a given idea
     * 
     * @param ideaUuid the UUID of the idea
     * @return the collaboration response (or null if there is none)
     * @throws GlobalException if the idea does not have a collaboration request from the user
     * 
     * @author Thibaut Baguette
     */
    @Transactional
    public CollaborationResponse getCollaborationResponseForRequesterAndIdea(String requesterUuid, String ideaUuid) {
        Idea idea = ideaService.getIdeaById(ideaUuid);
        RegularUser requester = regularUserRepository.findRegularUserById(requesterUuid);

        if (requester == null) {
            throw new GlobalException(HttpStatus.BAD_REQUEST, "Requester does not exist");
        }

        List<CollaborationRequest> requests = collaborationRequestRepository.findCollaborationRequestsByRequesterAndIdea(requester, idea);

        if (requests.isEmpty()) {
            throw new GlobalException(HttpStatus.BAD_REQUEST, "You did not send a collaboration request for this idea");
        }

        CollaborationRequest request = requests.get(0);

        CollaborationResponse response = request.getCollaborationResponse();

        return response;
    }
    
}
