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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.RequestParam;

import ca.mcgill.purposeful.exception.GlobalException;
import ca.mcgill.purposeful.model.*;
import ca.mcgill.purposeful.service.IdeaService;
import ca.mcgill.purposeful.dto.*;
import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * API for demonstrating how permissions work for access to endpoints
 */

@RestController
@RequestMapping({ "api/idea", "api/idea/" })
public class IdeaController {
  @Autowired
  IdeaService ideaService;

  @GetMapping("{id}")
  @PreAuthorize("hasAnyAuthority('User', 'Moderator', 'Owner')")
  public ResponseEntity<IdeaDTO> getIdeaById(@PathVariable String id) {
    Idea idea = ideaService.getIdeaById(id);
    return ResponseEntity.status(HttpStatus.OK).body(new IdeaDTO(idea));
  }

  /**
   * Filter ideas by topics, domains, and techs. Results are ordered by date.
   *
   * @param topics  <></>he topics to filter by (optional so that it can be null
   *                if no filter)
   * @param domains The domains to filter by (optional so that it can be null if
   *                no filter)
   * @param techs   The techs to filter by (optional so that it can be null if no
   *                filter)
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

  /**
   * This method modifies an idea
   * 
   * @author Ramin Akhavan
   * @return update idea
   * @throws Exception
   */
  @PutMapping(value = { "/idea/edit", "/idea/edit/" })
  public IdeaDTO modifyIdea(
      @RequestParam("id") String id,
      @RequestParam("title") String title,
      @RequestParam("purpose") String purpose,
      @RequestParam("descriptions") String descriptions,
      @RequestParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.TIME, pattern = "yyyy-mm-dd") Date date,
      @RequestParam("isPaid") boolean isPaid,
      @RequestParam("inProgress") boolean inProgress,
      @RequestParam("isPrivate") boolean isPrivate,
      @RequestParam("domains") List<String> domainIds,
      @RequestParam("techs") List<String> techIds,
      @RequestParam("topics") List<String> topicIds,
      @RequestParam("imgUrls") List<String> imgUrlIds,
      @RequestParam("iconUrl") String iconUrlId) throws Exception {
    Idea modifiedIdea = ideaService.modifyIdea(id, title, date, descriptions, purpose, isPaid, inProgress, isPrivate,
        domainIds, techIds, topicIds, imgUrlIds, iconUrlId);
    return new IdeaDTO(modifiedIdea);
  }
}