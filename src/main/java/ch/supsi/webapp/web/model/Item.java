package ch.supsi.webapp.web.model;

import javax.persistence.*;
import java.util.*;

@Entity
public class Item {
    private String title;
    @Column(columnDefinition = "TEXT")
    private String description;
    @ManyToOne(cascade =  { CascadeType.PERSIST,  CascadeType.MERGE} )
    private Author author;
    @ManyToOne(cascade =  { CascadeType.PERSIST,  CascadeType.MERGE} )
    private Category category;
    private Date date;
    private AnnouncementType annuncio;
    @Lob
    private byte[] image;
    @Id
    @GeneratedValue
    private int id;
    @ManyToMany(mappedBy = "listaFav", cascade =  { CascadeType.PERSIST,  CascadeType.MERGE})
    List<Author> authorList = new ArrayList<>();
    private double costo;

    public Item()
    {}

    public Item(String title, String description, Author author,Category category, AnnouncementType annuncio, byte[] image, double costo)
    {
        this.title = title;
        this.description = description;
        this.author = author;
        this.category = category;
        this.annuncio = annuncio;
        this.image = image;
    }

    public String printHomeDescription() {
        String[] split = getDescription().split(" ");
        if(split.length > 4)
        {
            return "Descrizione: " + split[0] + " " + split[1] + " " + split[2] + " " + split[3] + " ...";
        }
        else
            return printDescription();
    }

    public double getCosto() {
        return costo;
    }

    public void setCosto(double costo) {
        this.costo = costo;
    }

    public String imageToBase64() {
        return Base64.getEncoder().encodeToString(image);
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public String printDate(){ return "Data: " + date; }

    public Date getDate() { return date; }

    public void setDate(Date date) { this.date = date; }

    public String printCosto() {return "Costo dell'articolo: " + costo;}

    public String printAnnuncio() {return "Tipo di annuncio: " + annuncio;}

    public AnnouncementType getAnnuncio() { return annuncio; }

    public void setAnnuncio(AnnouncementType annuncio) { this.annuncio = annuncio; }

    public String printCategory() { return category.toString(); }

    public Category getCategory() {return category; }

    public void setCategory(Category category) { this.category = category; }

    public String getDescription() { return description; }

    public String printDescription() { return "Descrizione: " + description; }

    public void setDescription(String description) { this.description = description; }

    public String printAuthor() { return "Autore: " + author.getUsername(); }

    public Author getAuthor() { return author; }

    public void setAuthor(Author author) { this.author = author; }

    public String printTitle() { return "Titolo: " + title; }

    public String getTitle() { return title; }

    public void setTitle(String title) { this.title = title; }

    public int getId() { return id; }

    public void setId(int id) { this.id = id; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Item item = (Item) o;
        return Objects.equals(title, item.title) &&
                Objects.equals(description, item.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, description, author);
    }
}
