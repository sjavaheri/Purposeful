package ca.mcgill.purposeful.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import ca.mcgill.purposeful.dao.CollaborationRequestRepository;
import ca.mcgill.purposeful.dao.CollaborationResponseRepository;
import ca.mcgill.purposeful.exception.GlobalException;
import ca.mcgill.purposeful.model.CollaborationRequest;
import ca.mcgill.purposeful.model.CollaborationResponse;
import ca.mcgill.purposeful.model.Idea;
import jakarta.transaction.Transactional;

/**
 * Collaboration response service functions
 * 
 * @author Thibaut Baguette
 */
public class CollaborationResponseService {
    @Autowired
    CollaborationResponseRepository collaborationResponseRepository;

    @Autowired
    CollaborationRequestRepository collaborationRequestRepository;

    @Autowired
    IdeaService ideaService;

    @Transactional
    public CollaborationResponse getCollaborationResponseListForIdea(String ideaUuid) {
        Idea idea = ideaService.getIdeaById(ideaUuid);
        List<CollaborationRequest> requests = collaborationRequestRepository.findCollaborationRequestsByIdea(idea);

        if (requests.isEmpty()) {
            throw new GlobalException(HttpStatus.BAD_REQUEST, "You did not send a collaboration request for this idea");
        }

        CollaborationRequest request = requests.get(0);

        CollaborationResponse response = request.getCollaborationResponse();

        return response;
    }
    
}
