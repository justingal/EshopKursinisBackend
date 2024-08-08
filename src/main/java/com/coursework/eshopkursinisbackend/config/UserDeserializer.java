package com.coursework.eshopkursinisbackend.config;

import com.coursework.eshopkursinisbackend.model.Admin;
import com.coursework.eshopkursinisbackend.model.Customer;
import com.coursework.eshopkursinisbackend.model.Manager;
import com.coursework.eshopkursinisbackend.model.User;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.io.IOException;

public class UserDeserializer extends JsonDeserializer<User> {

    @Override
    public User deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException {
        JsonNode node = jp.getCodec().readTree(jp);
        String type = node.get("type").asText(null);

        if (type == null) {
            throw new IOException("User type is missing or null");
        }

        ((ObjectNode) node).remove("type");

        return switch (type) {
            case "Customer" -> jp.getCodec().treeToValue(node, Customer.class);
            case "Manager" -> jp.getCodec().treeToValue(node, Manager.class);
            case "Admin" -> jp.getCodec().treeToValue(node, Admin.class);
            default -> throw new IOException("Invalid user type: " + type);
        };
    }
}