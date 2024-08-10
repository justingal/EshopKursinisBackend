package com.coursework.eshopkursinisbackend.util;

import com.coursework.eshopkursinisbackend.dto.CommentDTO;
import com.coursework.eshopkursinisbackend.dto.ReviewDTO;
import com.coursework.eshopkursinisbackend.model.Comment;
import com.coursework.eshopkursinisbackend.model.Review;

public class CommentMapper {

    public static CommentDTO toDTO(Comment comment) {
        if (comment instanceof Review) {
            return toReviewDTO((Review) comment);
        } else if (comment != null) {
            return toCommentDTO(comment);
        } else {
            throw new IllegalArgumentException("Invalid comment type: " + (comment != null ? comment.getClass().getSimpleName() : "null"));
        }
    }

    public static CommentDTO toCommentDTO(Comment comment) {
        CommentDTO commentDTO = new CommentDTO();
        commentDTO.setId(comment.getId());
        commentDTO.setCommentBody(comment.getCommentBody());
        commentDTO.setCommentTitle(comment.getCommentTitle());
        commentDTO.setDateCreated(comment.getDateCreated());
        return commentDTO;
    }

    private static ReviewDTO toReviewDTO(Review review) {
        ReviewDTO reviewDTO = new ReviewDTO();
        reviewDTO.setId(review.getId());
        reviewDTO.setCommentBody(review.getCommentBody());
        reviewDTO.setCommentTitle(review.getCommentTitle());
        reviewDTO.setDateCreated(review.getDateCreated());
        reviewDTO.setRating((int) review.getRating());
        return reviewDTO;
    }
}
