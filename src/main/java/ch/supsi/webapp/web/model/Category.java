package ch.supsi.webapp.web.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Objects;

@Entity
public class Category {
    @Id
    @GeneratedValue
    private int id;
    private String category;

    public Category(String cat) { this.category = cat; }

    public int getId() { return id; }

    public void setId(int id) { this.id = id; }

    public Category()
    {}

    public String getCategory() { return category; }

    public void setCategory(String category) { this.category= category; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Category category1 = (Category) o;
        return Objects.equals(category, category1.category);
    }

    @Override
    public int hashCode() {
        return Objects.hash(category);
    }

    @Override
    public String toString() {
        return "Categoria: " + category;
    }
}
