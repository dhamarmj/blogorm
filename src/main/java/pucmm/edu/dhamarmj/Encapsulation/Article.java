package pucmm.edu.dhamarmj.Encapsulation;

import com.sun.istack.NotNull;

import javax.persistence.*;
import java.io.Serializable;
import java.util.*;

@Entity
public class Article implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String title;
    @Column(name = "body",
            columnDefinition = "CLOB NOT NULL")
    private String body;
    private Date fecha;
    private String teaser;
    private int likes;
    private String stringEtiqueta = "";
    @NotNull // JSR303
    @Column(nullable = false) // Hibernate / JPA
   private boolean addLike=true;

    @ManyToOne
    private User user;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "article")
    private Set<Comment> comment;

    @ManyToMany (fetch=FetchType.EAGER)
    @JoinTable(name = "article_label",
            joinColumns = {@JoinColumn(name = "fk_article")},
            inverseJoinColumns = {@JoinColumn(name = "fk_label")})
    private Set<Label> labels;

    public Article() {
    }

    public Article(String title, String body, Date fecha, User user, Set<Comment> comments, Set<Label> labels) {
        this.title = title;
        this.body = body;
        this.fecha = fecha;
        this.user = user;
        this.comment = comments;
        this.labels = labels;
        StartTeaser();
        this.likes = 0;
    }

    public Article(String title, String body, Date fecha, User user, Set<Comment> comments, Set<Label> labels, String stringEtiqueta) {
        this.title = title;
        this.body = body;
        this.fecha = fecha;
        this.user = user;
        this.comment = comments;
        this.labels = labels;
        StartTeaser();
        this.stringEtiqueta = stringEtiqueta;
        this.likes = 0;
    }

    public void StartTeaser() {
        if (this.body.length() < 70)
            this.teaser = this.body;
        else
            this.teaser = this.body.substring(0, 70);
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public void updateLikes(User CurrentUser) {
        if (addLike)
            this.likes++;
        else
            this.likes--;
        this.addLike = !addLike;
    }

    public boolean isAddLike() {
        return addLike;
    }

    public void setAddLike(boolean addLike) {
        this.addLike = addLike;
    }

    public Set<Comment> getComments() {
        return comment;
    }

    public void setComments(Set<Comment> comments) {
        this.comment = comments;
    }

    public void setComments(Comment comments) {
        if (comments == null) {
            this.comment = new HashSet<Comment>();
        }
        this.comment.add(comments);
    }

    public Set<Label> getLabels() {
        return labels;
    }

    public void setLabels(Set<Label> labels) {
        this.labels = labels;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getTeaser() {
        return teaser;
    }

    public void setTeaser(String teaser) {
        this.teaser = teaser;
    }

    public String getStringEtiqueta() {
        return stringEtiqueta;
    }

    public void setStringEtiqueta(String stringEtiqueta) {
        this.stringEtiqueta = stringEtiqueta;
    }
}
