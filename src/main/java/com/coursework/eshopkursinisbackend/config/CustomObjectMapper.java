package com.coursework.eshopkursinisbackend.config;

import com.coursework.eshopkursinisbackend.model.Comment;
import com.coursework.eshopkursinisbackend.model.Product;
import com.coursework.eshopkursinisbackend.model.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CustomObjectMapper {

    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        SimpleModule module = new SimpleModule();
        module.addDeserializer(User.class, new UserDeserializer());
        module.addDeserializer(Product.class, new ProductDeserializer());
        module.addDeserializer(Comment.class, new CommentDeserializer());
        objectMapper.registerModule(module);
        objectMapper.registerModule(new JavaTimeModule());
        return objectMapper;
    }
}
