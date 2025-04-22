package entity;
import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Entity;
import com.fasterxml.jackson.annotation.JsonProperty;
@Entity
public class Person extends PanacheEntity {



    @JsonProperty("name")
    public String name;
    @JsonProperty("fname")
    public String fname;
    @JsonProperty("alt")
    public Integer alt;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public Integer getAlt() {
        return alt;
    }

    public void setAlt(Integer alt) {
        this.alt = alt;
    }
    public Person() {}


    public Person(String name, String fname, int alt) {
        this.name = name;
        this.fname = fname;
        this.alt = alt;
    }



}
