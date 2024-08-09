package com.coursework.eshopkursinisbackend;

import com.coursework.eshopkursinisbackend.config.CustomObjectMapper;
import com.coursework.eshopkursinisbackend.model.Comment;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.io.IOException;

public class Verkiu {

    public static void main(String[] args) {
        // Initialize Spring Application Context
        ApplicationContext context = new AnnotationConfigApplicationContext(CustomObjectMapper.class);

        // Get the custom-configured ObjectMapper bean
        ObjectMapper objectMapper = context.getBean(ObjectMapper.class);

        try {
            // Example JSON string that represents a comment
            String commentInfo = "{ \"commentBody\": \"Test comment\", \"commentTitle\": \"Test title\", \"dateCreated\": \"2024-08-07\", \"user\": {\"id\": 2}, \"type\": \"Comment\" }";

            // Deserializing the JSON string into a Comment object using the custom ObjectMapper
            Comment comment = objectMapper.readValue(commentInfo, Comment.class);

            // Printing the deserialized Comment object to the console
            System.out.println("Deserialized Comment: ");
            System.out.println("Title: " + comment.getCommentTitle());
            System.out.println("Body: " + comment.getCommentBody());
            System.out.println("Date Created: " + comment.getDateCreated());
            System.out.println("User ID: " + (comment.getUser() != null ? comment.getUser().getId() : "null"));

        } catch (IOException e) {
            // Handling any potential exceptions that might occur during deserialization
            System.out.println("Error during deserialization: " + e.getMessage());
        }
    }
}
