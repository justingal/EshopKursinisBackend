package com.coursework.eshopkursinisbackend.controllers;

import com.coursework.eshopkursinisbackend.dto.BaseUserDTO;
import com.coursework.eshopkursinisbackend.dto.ProductDTO;
import com.coursework.eshopkursinisbackend.exceptions.ProductNotFoundException;
import com.coursework.eshopkursinisbackend.exceptions.UserNotFoundException;
import com.coursework.eshopkursinisbackend.model.*;
import com.coursework.eshopkursinisbackend.repos.ProductRepository;
import com.coursework.eshopkursinisbackend.util.ProductMapper;
import com.coursework.eshopkursinisbackend.util.UserMapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Valid;
import jakarta.validation.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
public class ProductRest {
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private Validator validator;

    @Autowired
    @Qualifier("objectMapper")
    private ObjectMapper objectMapper;

    @GetMapping(value = "/products")
    public ResponseEntity<List<ProductDTO>> getAllProducts() {
        List<Product> products = productRepository.findAll();
        List<ProductDTO> productDTOs = products.stream().map(ProductMapper::toDTO).collect(Collectors.toList());
        return ResponseEntity.ok(productDTOs);
    }

    @GetMapping(value = "/product/{id}")
    public ResponseEntity<EntityModel<ProductDTO>> getProductById(@PathVariable(name = "id") int id) {
        Product product = productRepository.findById(id).orElseThrow(() -> new ProductNotFoundException(id));
        ProductDTO productDTO = ProductMapper.toDTO(product);
        return ResponseEntity.ok(EntityModel.of(productDTO,
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(ProductRest.class).getProductById(id)).withSelfRel(),
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(ProductRest.class).getProductById(id)).withRel("Users")));
    }

    @PutMapping(value = "updateProduct/{id}")
    public ResponseEntity<?> updateProduct(@PathVariable(name = "id") int id, @Valid @RequestBody String productInfo) {
        try {
            Product productToUpdate = productRepository.findById(id).orElseThrow(() -> new ProductNotFoundException(id));
            if (productToUpdate instanceof BoardGame) {
                BoardGame newBoardGameInfo = objectMapper.readValue(productInfo, BoardGame.class);
                if (newBoardGameInfo.getTitle() != null) {
                    productToUpdate.setTitle(newBoardGameInfo.getTitle());
                }
                if (newBoardGameInfo.getAuthor() != null) {
                    productToUpdate.setAuthor(newBoardGameInfo.getAuthor());
                }
                if (newBoardGameInfo.getDescription() != null) {
                    productToUpdate.setDescription(newBoardGameInfo.getDescription());
                }
                if (newBoardGameInfo.getPrice() != 0) {
                    productToUpdate.setPrice(newBoardGameInfo.getPrice());
                }
                if (newBoardGameInfo.getWarehouse() != null) {
                    productToUpdate.setWarehouse(newBoardGameInfo.getWarehouse());
                }
                //BG
                if (newBoardGameInfo.getPlayersQuantity() != null) {
                    ((BoardGame) productToUpdate).setPlayersQuantity(newBoardGameInfo.getPlayersQuantity());
                }
                if (newBoardGameInfo.getGameDuration() != null) {
                    ((BoardGame) productToUpdate).setGameDuration(newBoardGameInfo.getGameDuration());
                }
            }
            if (productToUpdate instanceof Puzzle) {
                Puzzle newPuzzleInfo = objectMapper.readValue(productInfo, Puzzle.class);

                if (newPuzzleInfo.getTitle() != null) {
                    productToUpdate.setTitle(newPuzzleInfo.getTitle());
                }
                if (newPuzzleInfo.getAuthor() != null) {
                    productToUpdate.setAuthor(newPuzzleInfo.getAuthor());
                }
                if (newPuzzleInfo.getDescription() != null) {
                    productToUpdate.setDescription(newPuzzleInfo.getDescription());
                }
                if (newPuzzleInfo.getPrice() != 0) {
                    productToUpdate.setPrice(newPuzzleInfo.getPrice());
                }
                if (newPuzzleInfo.getWarehouse() != null) {
                    productToUpdate.setWarehouse(newPuzzleInfo.getWarehouse());
                }
                //Puzzle
                if (newPuzzleInfo.getPuzzleMaterial() != null) {
                    ((Puzzle) productToUpdate).setPuzzleMaterial(newPuzzleInfo.getPuzzleMaterial());
                }
                if (newPuzzleInfo.getPuzzleSize() != null) {
                    ((Puzzle) productToUpdate).setPuzzleSize(newPuzzleInfo.getPuzzleSize());
                }
                if (newPuzzleInfo.getPiecesQuantity() != 0) {
                    ((Puzzle) productToUpdate).setPiecesQuantity(newPuzzleInfo.getPiecesQuantity());
                }
            }
            if (productToUpdate instanceof Dice) {
                Dice newDiceInfo = objectMapper.readValue(productInfo, Dice.class);
                if (newDiceInfo.getTitle() != null) {
                    productToUpdate.setTitle(newDiceInfo.getTitle());
                }
                if (newDiceInfo.getAuthor() != null) {
                    productToUpdate.setAuthor(newDiceInfo.getAuthor());
                }
                if (newDiceInfo.getDescription() != null) {
                    productToUpdate.setDescription(newDiceInfo.getDescription());
                }
                if (newDiceInfo.getPrice() != 0) {
                    productToUpdate.setPrice(newDiceInfo.getPrice());
                }
                if (newDiceInfo.getWarehouse() != null) {
                    productToUpdate.setWarehouse(newDiceInfo.getWarehouse());
                }
                //BG
                if (newDiceInfo.getDiceNumber() != 0) {
                    ((Dice) productToUpdate).setDiceNumber(newDiceInfo.getDiceNumber());
                }
            }

            Set<ConstraintViolation<Product>> violations = validator.validate(productToUpdate);
            if (!violations.isEmpty()) {
                throw new ConstraintViolationException(violations);
            }
            Product savedProduct = productRepository.saveAndFlush(productToUpdate);
            ProductDTO productDTO = ProductMapper.toDTO(savedProduct);
            return new ResponseEntity<>(productDTO, HttpStatus.OK);
        } catch (IOException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (ConstraintViolationException e) {
            return new ResponseEntity<>(e.getConstraintViolations().toString(), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping(value = "/deleteProduct/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable(name = "id") int id) {
        try {
            productRepository.findById(id).orElseThrow(() -> new UserNotFoundException(id));

            productRepository.deleteById(id);
            return new ResponseEntity<>("User with id = " + id + " was successfully deleted", HttpStatus.OK);
        } catch (ConstraintViolationException e) {
            return new ResponseEntity<>(e.getConstraintViolations().toString(), HttpStatus.BAD_REQUEST);
        }
    }



    @PostMapping(value = "/createProduct")
    public ResponseEntity<?> createProduct(@Valid @RequestBody String productInfo) {
        try {
            Product product = objectMapper.readValue(productInfo, Product.class);

            Set<ConstraintViolation<Product>> violations = validator.validate(product);
            if (!violations.isEmpty()) {
                throw new ConstraintViolationException(violations);
            }

            Product savedProduct = productRepository.saveAndFlush(product);
            ProductDTO productDTO = ProductMapper.toDTO(savedProduct);
            return new ResponseEntity<>(productDTO, HttpStatus.CREATED);
        } catch (IOException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (ConstraintViolationException e) {
            return new ResponseEntity<>(e.getConstraintViolations().toString(), HttpStatus.BAD_REQUEST);
        }
    }

}
