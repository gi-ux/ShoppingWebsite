package ch.supsi.webapp.web.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Role {
    @Id
    @GeneratedValue
    private int id;
    private String role;

    public Role()
    {}

    public int getId() { return id; }

    public void setId(int id) { this.id = id; }

    public Role(String role) { this.role = role; }

    public String getRole() { return role; }

    public void setRole(String role) { this.role = role; }

}
