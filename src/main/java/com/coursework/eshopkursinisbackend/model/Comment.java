package com.coursework.eshopkursinisbackend.model;

import jakarta.persistence.*;
import jakarta.persistence.CascadeType;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


@Setter
@Getter
@Entity
public class Comment {


    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    private int id;
    private String commentTitle;
    private String commentBody;
    private LocalDate dateCreated;
    @OneToMany(mappedBy = "parentComment", cascade = {CascadeType.PERSIST, CascadeType.MERGE}, orphanRemoval = true)
    @Fetch(FetchMode.SELECT)
    private List<Comment> replies = new ArrayList<>();

    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Comment parentComment;

    @ManyToOne
    private User user;

    @ManyToOne
    private CustomerOrder customerOrder;

    public Comment() {
    }

    public Comment(int id, String commentTitle, String commentBody, LocalDate dateCreated, List<Comment> replies, Comment parentComment, User user) {
        this.id = id;
        this.commentTitle = commentTitle;
        this.commentBody = commentBody;
        this.dateCreated = dateCreated;
        this.replies = replies;
        this.parentComment = parentComment;
        this.user = user;
    }
    public Comment(String commentTitle, String commentBody, User user) {
        this.commentTitle = commentTitle;
        this.commentBody = commentBody;
        this.dateCreated = LocalDate.now();
        this.user = user;
        this.replies = new ArrayList<>();
    }

    public Comment(String commentTitle, String commentBody, Comment parentComment, User user) {
        this.commentTitle = commentTitle;
        this.commentBody = commentBody;
        this.dateCreated = LocalDate.now();
        this.user = user;
        this.parentComment = parentComment;
        this.replies = new ArrayList<>();
    }

    @Override
    public String toString() {
        return user.getName() + " " + user.getSurname() + ":" + commentTitle + ":" + dateCreated;
    }


}