package ca.mcgill.purposeful.dto;

import ca.mcgill.purposeful.model.Topic;

public class TopicDTO {
    
    private String id;
    private String name;


    public TopicDTO(Topic topic) {
        this.id = topic.getId();
        this.name = topic.getName();
    }

    public String getId(){
        return id;
    }

    public String getName(){
        return name;
    }
}
