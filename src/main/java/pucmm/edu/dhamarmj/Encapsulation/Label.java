package pucmm.edu.dhamarmj.Encapsulation;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
public class Label implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String labelText;



    @ManyToMany
    private Set<Article> articles = new HashSet<>();


    public Label() {
    }

    public Label(String labelText,Set<Article> articles ) {
        this.labelText = labelText;
        this.articles = articles;
    }
    public Label(String labelText) {
        this.labelText = labelText;
        this.articles = new HashSet<>();
    }

    public Set<Article> getArticles() {
        return articles;
    }

    public void setArticles(Set<Article> articles) {
        this.articles = articles;
    }

    public long getId() {
        return id;
    }

    public String getLabelText() {
        return labelText;
    }

    public void setLabelText(String labelText) {
        this.labelText = labelText;
    }
}
