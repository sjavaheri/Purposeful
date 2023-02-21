package ca.mcgill.purposeful.service;

import static org.junit.Assert.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import ca.mcgill.purposeful.dao.IdeaRepository;
import ca.mcgill.purposeful.dao.ReactionRepository;
import ca.mcgill.purposeful.dao.RegularUserRepository;
import ca.mcgill.purposeful.model.Idea;
import ca.mcgill.purposeful.model.Reaction;
import ca.mcgill.purposeful.service.TestIdeaService.MockDatabase;
import ca.mcgill.purposeful.service.TestIdeaService.MockRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

/**
 * To test the idea service methods
 *
 * @author Athmane Benarous
 */
@ExtendWith(MockitoExtension.class)
public class TestReactionService {

  // Mocks
  @Mock
  private ReactionRepository reactionRepository;
  @Mock
  private IdeaRepository ideaRepository;
  @Mock
  private RegularUserRepository regularUserRepository;

  // Inject mocks
  @InjectMocks
  private ReactionService reactionService;
  @InjectMocks
  private IdeaService ideaService;

  // Set the mock output of each function in the repository
  @BeforeEach
  public void setMockOutput() {
    lenient()
        .when(reactionRepository.findReactionById(anyString()))
        .thenAnswer(MockRepository::findReactionById);
    lenient()
        .when(ideaRepository.findIdeaById(anyString()))
        .thenAnswer(MockRepository::findIdeaById);
    lenient()
        .when(regularUserRepository.findRegularUserById(anyString()))
        .thenAnswer(MockRepository::findRegularUserById);
    lenient().when(reactionRepository.save(any(Reaction.class)))
        .thenAnswer(MockRepository::saveReaction);
  }

  /**
   * Test the  method (Reaction delete case)
   *
   * @author Athmane Benarous
   */
  @Test
  public void testReact_Delete() {
    // Init idea
    Idea idea = ideaService.getIdeaById(MockDatabase.idea1.getId());

    // Call service layer
    Reaction reaction = reactionService.getReactionByIdeaAndRegularUser(idea.getId(),
        MockDatabase.user1.getId());

    // Verify
    assertNull(reaction);
    verify(reactionRepository, times(1)).deleteById(MockDatabase.reaction1.getId());
  }
}