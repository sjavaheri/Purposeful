package ca.mcgill.purposeful.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ca.mcgill.purposeful.dto.CollaborationResponseDTO;
import ca.mcgill.purposeful.exception.GlobalException;
import ca.mcgill.purposeful.model.CollaborationResponse;
import ca.mcgill.purposeful.service.CollaborationResponseService;

@RestController
@RequestMapping({ "/api/collaborationResponse", "/api/collaborationResponse/" })
public class CollaborationResponseController {

    @Autowired
    CollaborationResponseService collaborationResponseService;

    /**
     * This method returns the collaboration response for the requester and the idea
     * 
     * @param id the idea's id
     * @return collaboration response
     */
    @GetMapping({"/{ideaId}", "/{ideaId}/"})
    @PreAuthorize("hasAnyAuthority('User', 'Moderator', 'Owner')")
    public ResponseEntity<CollaborationResponseDTO> getCollaborationResponseForRequesterAndIdea(
            @PathVariable String ideaId) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null) {
            throw new GlobalException(HttpStatus.UNAUTHORIZED, "User is not authenticated.");
        }

        CollaborationResponse response = collaborationResponseService.getCollaborationResponseForRequesterAndIdea(
                auth.getName(),
                ideaId);
        CollaborationResponseDTO responseDTO = new CollaborationResponseDTO(response);

        return new ResponseEntity<CollaborationResponseDTO>(responseDTO, HttpStatus.OK);
    }
}
