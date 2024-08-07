package com.coursework.eshopkursinisbackend.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.*;

@Setter
@Getter
@Entity
public class Review extends Comment {

    private double rating;

    @ManyToOne
    @Fetch(FetchMode.SELECT)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonBackReference("puzzle-review")
    private Puzzle puzzle;

    @ManyToOne
    @Fetch(FetchMode.SELECT)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonBackReference("boardgame-review")
    private BoardGame boardGame;

    @ManyToOne
    @Fetch(FetchMode.SELECT)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonBackReference("dice-review")
    private Dice dice;


    public Review(String commentTitle, String commentBody, Comment parentComment, User user) {
        super(commentTitle, commentBody, parentComment, user);
    }

    public Review(String commentTitle, String commentBody, Comment parentComment, User user, double rating, Puzzle puzzle, BoardGame boardGame, Dice dice) {
        super(commentTitle, commentBody, parentComment, user);
        this.rating = rating;
        this.puzzle = puzzle;
        this.boardGame = boardGame;
        this.dice = dice;
    }

    public Review(String commentTitle, String commentBody, User user, double rating, BoardGame boardGame) {
        super(commentTitle, commentBody, user);
        this.rating = rating;
        this.boardGame = boardGame;
    }

    public Review(String commentTitle, String commentBody, User user, double rating, Dice dice) {
        super(commentTitle, commentBody, user);
        this.rating = rating;
        this.dice = dice;
    }

    public Review(String commentTitle, String commentBody, User user, double rating, Puzzle puzzle) {
        super(commentTitle, commentBody, user);
        this.rating = rating;
        this.puzzle = puzzle;
    }

    public Review() {

    }


    @Override
    public String toString() {
        return "(" + rating + ") " + getCommentBody();
    }


}