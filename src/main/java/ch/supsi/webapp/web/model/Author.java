package ch.supsi.webapp.web.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
public class Author {


    @Id
    private String author;

    private String password;
    private String nome;
    private String cognome;
    private double credito = 50;


    @ManyToOne(cascade =  { CascadeType.PERSIST,  CascadeType.MERGE} )
    Role role;

    @JoinTable(name = "lista_favoriti",
            joinColumns = @JoinColumn(name="fk_author"),
            inverseJoinColumns = @JoinColumn(name="fk_item"))
    @ManyToMany(cascade = CascadeType.MERGE)
    List<Item> listaFav = new ArrayList<>();

    public List<Item> getListaFav() {
        return listaFav;
    }

    public void addFav(Item item) {
        listaFav.add(item);
    }

    public Author()
    {}

    public boolean isPresent(Item item)
    {
        return listaFav.contains(item);
    }

    public Author(String admin, Role role_admin) {
        this.author = admin;
        this.role = role_admin;
    }

    public Author(String admin, String role, String password)
    {
        this.author = admin;
        this.password = password;
        this.role = new Role(role);
    }

    public double getCredito() {
        return credito;
    }

    public void setCredito(double credito) {
        this.credito = credito;
    }

    public String getCognome() { return cognome; }

    public String getNome() { return nome; }

    public String getPassword() { return password; }

    public void setNome(String nome) { this.nome = nome; }

    public void setCognome(String cognome) { this.cognome = cognome; }

    public void setPassword(String password) { this.password = password; }

    public Author(String user) { this.author = user; }

    public Role getRole() { return role; }

    public void setRole(Role role) { this.role = role; }

    public String getUsername() { return author; }

    public void setUsername(String username) { this.author = username; }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Author author1 = (Author) o;
        return Objects.equals(author, author1.author);
    }

    @Override
    public int hashCode() {
        return Objects.hash(author);
    }

    @Override
    public String toString() {
        return "Autore : " + author +
                ", Ruolo: " + role;
    }


}
