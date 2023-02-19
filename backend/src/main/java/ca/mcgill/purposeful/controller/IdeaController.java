package ca.mcgill.purposeful.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ca.mcgill.purposeful.dto.DetailedIdeaDTO;
import ca.mcgill.purposeful.model.Idea;
import ca.mcgill.purposeful.service.IdeaService;

/**
 * API for demonstrating how permissions work for access to endpoints
 */
@RestController
@RequestMapping({ "/idea", "/idea/" })
public class IdeaController {
    @Autowired
    IdeaService ideaService;

    /**
     * This methods gets all librarians for the database
     * 
     * @author Ramin Akhavan
     * @return update idea
     * @throws Exception
     */
    // @PutMapping
    // public IdeaDTO modifyIdea(@RequestParam String title, @RequestParam String
    // purpose, @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.TIME,
    // pattern="yyyy-mm-dd") date, ) throws Exception{

    // }

    @GetMapping("/{id}/details")
    public DetailedIdeaDTO getIdeaDetailsById(@PathVariable String id) {
        Idea idea = ideaService.getIdeaById(id);
        return new DetailedIdeaDTO(idea);
    }
}