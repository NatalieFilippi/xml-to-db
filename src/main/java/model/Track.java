package model;

import lombok.Data;

import javax.xml.bind.annotation.*;

@Data
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement
public class Track {
    @XmlElement(name = "dugf")
    private long dugf;
    @XmlElement(name = "hhrs")
    private long hhrs;
    @XmlElement(name = "lcount")
    private long lcount;
    @XmlElement(name = "btrfsnum")
    private long btrfsnum;
    private Pod pod;


    @Override
    public String toString() {
        return "Track{" +
                "dugf=" + dugf +
                ", hhrs=" + hhrs +
                ", lcount=" + lcount +
                ", btrfsnum=" + btrfsnum +
                ", pod=" + pod +
                '}';
    }
}

