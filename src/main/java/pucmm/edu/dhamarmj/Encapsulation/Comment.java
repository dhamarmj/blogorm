package pucmm.edu.dhamarmj.Encapsulation;

import javax.persistence.*;
import java.io.Serializable;


@Entity
public class Comment implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String commentText;

    @ManyToOne
    private User user;

    @ManyToOne
    private Article article;


    public Comment() {
    }

    public Comment(String commentText, User user, Article article) {
        this.commentText = commentText;
        this.user = user;
        this.article = article;
    }

    public Article getArticle() {
        return article;
    }

    public void setArticle(Article article) {
        this.article = article;
    }

    public long getId() {
        return id;
    }

    public String getCommentText() {
        return commentText;
    }

    public void setCommentText(String commentText) {
        this.commentText = commentText;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
