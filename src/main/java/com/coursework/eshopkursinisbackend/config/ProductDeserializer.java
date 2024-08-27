package com.coursework.eshopkursinisbackend.config;

import com.coursework.eshopkursinisbackend.model.BoardGame;
import com.coursework.eshopkursinisbackend.model.Dice;
import com.coursework.eshopkursinisbackend.model.Product;
import com.coursework.eshopkursinisbackend.model.Puzzle;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.io.IOException;

public class ProductDeserializer extends JsonDeserializer<Product> {
    @Override
    public Product deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException {
        JsonNode node = jp.getCodec().readTree(jp);
        String type = node.get("type").asText(null);

        if (type == null) {
            throw new IOException("Product type is missing or null");
        }

        ((ObjectNode) node).remove("type");

        return switch (type) {
            case "BoardGame" -> jp.getCodec().treeToValue(node, BoardGame.class);
            case "Puzzle" -> jp.getCodec().treeToValue(node, Puzzle.class);
            case "Dice" -> jp.getCodec().treeToValue(node, Dice.class);
            default -> throw new IOException("Invalid Product type: " + type);
        };
    }
}
