package model;

import lombok.Data;
import util.LocalDateAdapter;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.time.LocalDate;

@Data
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement
public class Pod {
    @XmlElement(name = "id")
    long id;
    @XmlElement(name = "tssnum")
    @XmlJavaTypeAdapter(value = LocalDateAdapter.class)
    LocalDate tssnum;
}
