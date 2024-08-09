package com.coursework.eshopkursinisbackend.config;

import com.coursework.eshopkursinisbackend.model.Comment;

import com.coursework.eshopkursinisbackend.model.Review;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.io.IOException;

public class CommentDeserializer extends JsonDeserializer<Comment> {

    @Override
    public Comment deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException {
        JsonNode node = jp.getCodec().readTree(jp);
        String type = node.get("type").asText(null);

        if (type == null) {
            throw new IOException("Comment type is missing or null");
        }

        ((ObjectNode) node).remove(type);

        return switch (type) {
            case "Review" -> jp.getCodec().treeToValue(node, Review.class);
            case "Comment" -> jp.getCodec().treeToValue(node, Comment.class);
            default -> throw new IOException("Invalid comment type: " + type);
        };
    }

}
