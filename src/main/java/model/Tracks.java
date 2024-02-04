package model;

import lombok.Data;

import javax.xml.bind.annotation.*;
import java.util.List;

@Data
@XmlRootElement(name = "tracks")
@XmlAccessorType(XmlAccessType.FIELD)
public class Tracks {
    @XmlElementWrapper(name="track")
    private List<Track> tracks;
}
