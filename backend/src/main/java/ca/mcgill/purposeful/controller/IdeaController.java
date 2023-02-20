package ca.mcgill.purposeful.controller;

import ca.mcgill.purposeful.dto.IdeaDTO;
import ca.mcgill.purposeful.model.Idea;
import ca.mcgill.purposeful.service.IdeaService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/** API for demonstrating how permissions work for access to endpoints */
@RestController
@RequestMapping({"api/idea", "api/idea/"})
public class IdeaController {
  @Autowired IdeaService ideaService;

  @GetMapping("{id}")
  @PreAuthorize("hasAnyAuthority('User', 'Moderator', 'Owner')")
  public ResponseEntity<IdeaDTO> getIdeaById(@PathVariable String id) {
    Idea idea = ideaService.getIdeaById(id);
    return ResponseEntity.status(HttpStatus.OK).body(new IdeaDTO(idea));
  }

  /**
   * Filter ideas by topics, domains, and techs. Results are ordered by date.
   *
   * @param topics <></>he topics to filter by (optional so that it can be null if no filter)
   * @param domains The domains to filter by (optional so that it can be null if no filter)
   * @param techs The techs to filter by (optional so that it can be null if no filter)
   * @return A list of idea DTOs that matches the filters
   * @author Wassim Jabbour
   */
  @GetMapping
  @PreAuthorize("hasAnyAuthority('User', 'Moderator', 'Owner')")
  public ResponseEntity<List<IdeaDTO>> filterIdeas(
      @RequestParam(value = "topics", required = false) List<String> topics,
      @RequestParam(value = "domains", required = false) List<String> domains,
      @RequestParam(value = "techs", required = false) List<String> techs) {
    return ResponseEntity.status(HttpStatus.OK)
        .body(IdeaDTO.convertToDto(ideaService.getIdeasByAllCriteria(topics, domains, techs)));
  }
}
