package ca.mcgill.purposeful.dto;

import ca.mcgill.purposeful.model.Domain;

public class DomainDTO {
    
    private String id;
    private String name;

    public DomainDTO(Domain domain) {
        this.id = domain.getId();
        this.name = domain.getName();
    }

    public String getId(){
        return id;
    }

    public String getName(){
        return name;
    }
}
