
package ca.mcgill.ecse321.libraryservice.dto;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

public class DetailedItemDTO {

    private String id;
    private boolean isPaid;
    private boolean isPrivate;
    private boolean inProgress;
    private String title;
    private String purpose;
    private String descriptions;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date date;
    private Set<DomainDTO> domains;
    private Set<TechDTO> techs;
    private Set<TopicDTO> topics;
    private List<URLDTO> imgUrls;
    private URLDTO iconUrl;

    public DetailedItemDTO() {
    }

    public DetailedItemDTO(String title, String purpose, String descriptions, Date date, boolean isPaid, boolean inProgress, boolean isPrivate, Set<DomainDTO> domains, Set<TechDTO> techs, Set<TopicDTO> topics, List<URLDTO> imgUrls, URLDTO iconUrl)
    {
        this.isPaid = isPaid;
        this.isPrivate = isPrivate;
        this.inProgress = inProgress;
        this.title = title;
        this.purpose = purpose;
        this.descriptions = descriptions;
        this.date = date;
        this.domains = domains;
        this.techs = techs;
        this.topics = topics;
        this.imgUrls = imgUrls;
        this.iconUrl = iconUrl;
    }

    public DetailedItemDTO(String id, String title, String purpose, String descriptions, Date date, boolean isPaid, boolean inProgress, boolean isPrivate, Set<DomainDTO> domains, Set<TechDTO> techs, Set<TopicDTO> topics, List<URLDTO> imgUrls, URLDTO iconUrl)
    {
        this.id = id;
        this.isPaid = isPaid;
        this.isPrivate = isPrivate;
        this.inProgress = inProgress;
        this.title = title;
        this.purpose = purpose;
        this.descriptions = descriptions;
        this.date = date;
        this.domains = domains;
        this.techs = techs;
        this.topics = topics;
        this.imgUrls = imgUrls;
        this.iconUrl = iconUrl;
    }

    public String getId()
    {
        return id;
    }

    public String getTitle()
    {
        return title;
    }

    public String getPurpose()
    {
        return purpose;
    }

    public String getDescription()
    {
        return this.descriptions;
    }

    public boolean getIsPaid()
    {
        return isPaid;
    }

    public boolean getIsPrivate()
    {
        return isPrivate;
    }

    public boolean getInProgress()
    {
        return inProgress;
    }

    public Date getDate()
    {
        return date;
    }

    public DomainDTO getDomains(){
        return domains;
    }

    public TechDTO getTechs(){
        return techs;
    }

    public TopicsDTO getTechs(){
        return techs;
    }

    public URLDTO getImgUrls(){
        return imgUrls;
    }

    public URLDTO getIconUrl(){
        return iconUrl;
    }

}
