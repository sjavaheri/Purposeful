package ca.mcgill.purposeful.controller;

import ca.mcgill.purposeful.dto.IdeaDTO;
import ca.mcgill.purposeful.model.Idea;
import ca.mcgill.purposeful.service.IdeaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/** API for demonstrating how permissions work for access to endpoints */
@RestController
@RequestMapping({"/idea", "/idea/"})
public class IdeaController {
  @Autowired IdeaService ideaService;

  @GetMapping("/api/idea/{id}")
  public ResponseEntity<IdeaDTO> getIdeaById(@PathVariable String id) {
    Idea idea = ideaService.getIdeaById(id);
    return ResponseEntity.status(HttpStatus.OK).body(new IdeaDTO(idea));
  }

  //  @GetMapping("/api/idea")
  //  public List<IdeaDTO> filterIdeas(
  //      @RequestParam(value = "topics") String[] topics,
  //      @RequestParam(value = "domains") String[] domains,
  //      @RequestParam(value = "techs") String[] techs) {
  //    return ideaService.(topics, domains, techs);;
  //  }
}
