package ca.mcgill.purposeful.service;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.lenient;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.jupiter.MockitoExtension;

import ca.mcgill.purposeful.dao.CollaborationRequestRepository;
import ca.mcgill.purposeful.exception.GlobalException;
import ca.mcgill.purposeful.model.CollaborationRequest;
import ca.mcgill.purposeful.model.CollaborationResponse;
import ca.mcgill.purposeful.model.Idea;

/**
 * Tests for the collaboration response service
 */
@ExtendWith(MockitoExtension.class)
public class TestCollaborationResponseService {

    @Mock
    private CollaborationRequestRepository collaborationRequestRepository;

    @Mock
    private IdeaService ideaService;

    @InjectMocks
    private CollaborationResponseService collaborationResponseService;

    private static final String IDEA_ID = "1";
    private static final Idea IDEA = new Idea();
    private static final String RESPONSE_MESSAGE = "This is a response message.";
    private static final String ADDITIONAL_CONTACT = "123-456-7890";

    private static final String IDEA_ID_NO_RESPONSE = "2";
    private static final Idea IDEA_NO_RESPONSE = new Idea();
    private static final String IDEA_ID_NO_REQUEST = "3";
    private static final Idea IDEA_NO_REQUEST = new Idea();

    @BeforeEach
    public void setMockOutput() {
        lenient()
                .when(collaborationRequestRepository.findCollaborationRequestsByIdea(any(Idea.class)))
                .thenAnswer((InvocationOnMock invocation) -> {
                    Idea arg = invocation.getArgument(0);
                    if (arg.equals(IDEA)) {
                        CollaborationResponse response = new CollaborationResponse();
                        response.setMessage(RESPONSE_MESSAGE);
                        response.setAdditionalContact(ADDITIONAL_CONTACT);
                        CollaborationRequest request = new CollaborationRequest();
                        request.setCollaborationResponse(response);
                        ArrayList<CollaborationRequest> requests = new ArrayList<>();
                        requests.add(request);
                        return requests;
                    } else if (arg.equals(IDEA_NO_RESPONSE)) {
                        CollaborationRequest request = new CollaborationRequest();
                        ArrayList<CollaborationRequest> requests = new ArrayList<>();
                        requests.add(request);
                        return requests;
                    } else if (arg.equals(IDEA_NO_REQUEST)) {
                        return new ArrayList<>();
                    }
                    return null;
                });
        lenient()
                .when(ideaService.getIdeaById(anyString()))
                .thenAnswer((InvocationOnMock invocation) -> {
                    switch (invocation.getArgument(0).toString()) {
                        case IDEA_ID:
                            return IDEA;
                        case IDEA_ID_NO_RESPONSE:
                            return IDEA_NO_RESPONSE;
                        case IDEA_ID_NO_REQUEST:
                            return IDEA_NO_REQUEST;
                        default:
                            return null;
                    }
                });
    }

    /**
     * Test that the collaboration response is returned when it exists
     * 
     * @author Thibaut Baguette
     */
    @Test
    public void testViewCollaborationResponse_successful() {
        CollaborationResponse response = collaborationResponseService.getCollaborationResponseForIdea(IDEA_ID);

        assertNotNull(response);
        assertEquals(RESPONSE_MESSAGE, response.getMessage());
        assertEquals(ADDITIONAL_CONTACT, response.getAdditionalContact());
    }

    /**
     * Test that null is returned when there is no collaboration response
     * 
     * @author Thibaut Baguette
     */
    @Test
    public void testViewCollaborationResponse_alternate() {
        CollaborationResponse response = collaborationResponseService
                .getCollaborationResponseForIdea(IDEA_ID_NO_RESPONSE);

        assertNull(response);
    }

    /**
     * Test that an exception is thrown when the idea has no collaboration request
     * 
     * @author Thibaut Baguette
     */
    @Test
    public void testViewCollaborationResponse_error() {
        assertThrows(
                GlobalException.class,
                () -> collaborationResponseService.getCollaborationResponseForIdea(IDEA_ID_NO_REQUEST));
    }
}
