package com.coursework.eshopkursinisbackend.config;

import com.coursework.eshopkursinisbackend.model.Comment;

import com.coursework.eshopkursinisbackend.model.Review;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.io.IOException;
import java.time.LocalDate;

public class CommentDeserializer extends JsonDeserializer<Comment> {
    @Override
    public Comment deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException {
        JsonNode node = jp.getCodec().readTree(jp);
        String type = node.get("type").asText(null);

        if (type == null) {
            throw new IOException("Comment type is missing or null");
        }

        ((ObjectNode) node).remove(type);

        if ("Comment".equals(type)) {
            Comment comment = new Comment();
            comment.setCommentBody(node.get("commentBody").asText());
            comment.setCommentTitle(node.get("commentTitle").asText());
            JsonNode dateCreatedNode = node.get("dateCreated");
            if (dateCreatedNode != null) {
                String dateCreatedStr = dateCreatedNode.asText();
                LocalDate dateCreated = LocalDate.parse(dateCreatedStr);
                comment.setDateCreated(dateCreated);
            }
            return comment;
        } else if ("Review".equals(type)) {
            return jp.getCodec().treeToValue(node, Review.class);
        } else {
            throw new IOException("Invalid comment type: " + type);
        }
    }
}
