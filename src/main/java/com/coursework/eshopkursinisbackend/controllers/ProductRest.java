package com.coursework.eshopkursinisbackend.controllers;

import com.coursework.eshopkursinisbackend.dto.BaseUserDTO;
import com.coursework.eshopkursinisbackend.dto.ProductDTO;
import com.coursework.eshopkursinisbackend.exceptions.ProductNotFoundException;
import com.coursework.eshopkursinisbackend.exceptions.UserNotFoundException;
import com.coursework.eshopkursinisbackend.model.*;
import com.coursework.eshopkursinisbackend.repos.BoardGameRepository;
import com.coursework.eshopkursinisbackend.repos.PuzzleRepository;
import com.coursework.eshopkursinisbackend.repos.DiceRepository;

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
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
public class ProductRest {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private BoardGameRepository boardGameRepository;
    @Autowired
    private PuzzleRepository puzzleRepository;
    @Autowired
    private DiceRepository diceRepository;


    @Autowired
    private Validator validator;

    @Autowired
    @Qualifier("objectMapper")
    private ObjectMapper objectMapper;

    @GetMapping(value = "/products")
    public ResponseEntity<List<ProductDTO>> getAllProducts() {
        List<BoardGame> boardGames = boardGameRepository.findAll();
        List<Puzzle> puzzles = puzzleRepository.findAll();
        List<Dice> dices = diceRepository.findAll();

        // Map each entity type to a ProductDTO
        List<ProductDTO> boardGameDTOs = boardGames.stream().map(ProductMapper::toDTO).collect(Collectors.toList());
        List<ProductDTO> puzzleDTOs = puzzles.stream().map(ProductMapper::toDTO).collect(Collectors.toList());
        List<ProductDTO> diceDTOs = dices.stream().map(ProductMapper::toDTO).collect(Collectors.toList());

        // Combine all the lists into one
        List<ProductDTO> allProductDTOs = new ArrayList<>();
        allProductDTOs.addAll(boardGameDTOs);
        allProductDTOs.addAll(puzzleDTOs);
        allProductDTOs.addAll(diceDTOs);

        return ResponseEntity.ok(allProductDTOs);
    }
    @GetMapping(value = "/product/{id}")
    public ResponseEntity<EntityModel<ProductDTO>> getProductById(@PathVariable(name = "id") int id) {
        Optional<BoardGame> boardGameOpt = boardGameRepository.findById(id);
        Optional<Puzzle> puzzleOpt = puzzleRepository.findById(id);
        Optional<Dice> diceOpt = diceRepository.findById(id);

        ProductDTO productDTO = null;

        if (boardGameOpt.isPresent()) {
            productDTO = ProductMapper.toDTO(boardGameOpt.get());
        } else if (puzzleOpt.isPresent()) {
            productDTO = ProductMapper.toDTO(puzzleOpt.get());
        } else if (diceOpt.isPresent()) {
            productDTO = ProductMapper.toDTO(diceOpt.get());
        } else {
            throw new ProductNotFoundException(id);
        }

        return ResponseEntity.ok(EntityModel.of(
                productDTO,
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(ProductRest.class).getProductById(id)).withSelfRel(),
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(ProductRest.class).getProductById(id)).withRel("products"))
        );
    }

    @PutMapping(value = "updateProduct/{id}")
    public ResponseEntity<?> updateProduct(@PathVariable(name = "id") int id, @Valid @RequestBody String productInfo) {
        try {
            // Retrieve the product by ID, throwing an exception if not found
            Optional<BoardGame> boardGameOpt = boardGameRepository.findById(id);
            Optional<Puzzle> puzzleOpt = puzzleRepository.findById(id);
            Optional<Dice> diceOpt = diceRepository.findById(id);

            Product productToUpdate;

            if (boardGameOpt.isPresent()) {
                productToUpdate = boardGameOpt.get();
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
                if (newBoardGameInfo.getPlayersQuantity() != null) {
                    ((BoardGame) productToUpdate).setPlayersQuantity(newBoardGameInfo.getPlayersQuantity());
                }
                if (newBoardGameInfo.getGameDuration() != null) {
                    ((BoardGame) productToUpdate).setGameDuration(newBoardGameInfo.getGameDuration());
                }

            } else if (puzzleOpt.isPresent()) {
                productToUpdate = puzzleOpt.get();
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
                if (newPuzzleInfo.getPuzzleMaterial() != null) {
                    ((Puzzle) productToUpdate).setPuzzleMaterial(newPuzzleInfo.getPuzzleMaterial());
                }
                if (newPuzzleInfo.getPuzzleSize() != null) {
                    ((Puzzle) productToUpdate).setPuzzleSize(newPuzzleInfo.getPuzzleSize());
                }
                if (newPuzzleInfo.getPiecesQuantity() != 0) {
                    ((Puzzle) productToUpdate).setPiecesQuantity(newPuzzleInfo.getPiecesQuantity());
                }

            } else if (diceOpt.isPresent()) {
                productToUpdate = diceOpt.get();
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
                if (newDiceInfo.getDiceNumber() != 0) {
                    ((Dice) productToUpdate).setDiceNumber(newDiceInfo.getDiceNumber());
                }

            } else {
                throw new ProductNotFoundException(id);
            }

            // Validate the updated product
            Set<ConstraintViolation<Product>> violations = validator.validate(productToUpdate);
            if (!violations.isEmpty()) {
                throw new ConstraintViolationException(violations);
            }

            // Save the updated product
            Product savedProduct = productRepository.saveAndFlush(productToUpdate);

            // Map the saved product to a ProductDTO and return it
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
            BoardGame boardGame = boardGameRepository.findById(id).orElse(null);
            if (boardGame != null) {
                detachAndDelete(boardGame);
                return new ResponseEntity<>("BoardGame with id = " + id + " was successfully deleted", HttpStatus.OK);
            }

            Puzzle puzzle = puzzleRepository.findById(id).orElse(null);
            if (puzzle != null) {
                detachAndDelete(puzzle);
                return new ResponseEntity<>("Puzzle with id = " + id + " was successfully deleted", HttpStatus.OK);
            }

            Dice dice = diceRepository.findById(id).orElse(null);
            if (dice != null) {
                detachAndDelete(dice);
                return new ResponseEntity<>("Dice with id = " + id + " was successfully deleted", HttpStatus.OK);
            }

            return new ResponseEntity<>("Product with id = " + id + " not found", HttpStatus.NOT_FOUND);
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
    public void detachAndDelete(Product product) {
        if (product instanceof BoardGame) {
            BoardGame boardGame = (BoardGame) product;
            boardGame.setWarehouse(null);
            boardGameRepository.delete(boardGame);
        } else if (product instanceof Puzzle) {
            Puzzle puzzle = (Puzzle) product;
            puzzle.setWarehouse(null);
            puzzleRepository.delete(puzzle);
        } else if (product instanceof Dice) {
            Dice dice = (Dice) product;
            dice.setWarehouse(null);
            diceRepository.delete(dice);
        }
        }
}
