package com.coursework.eshopkursinisbackend.util;

import com.coursework.eshopkursinisbackend.dto.BoardGameDTO;
import com.coursework.eshopkursinisbackend.dto.DiceDTO;
import com.coursework.eshopkursinisbackend.dto.ProductDTO;
import com.coursework.eshopkursinisbackend.dto.PuzzleDTO;
import com.coursework.eshopkursinisbackend.model.BoardGame;
import com.coursework.eshopkursinisbackend.model.Product;
import com.coursework.eshopkursinisbackend.model.Dice;
import com.coursework.eshopkursinisbackend.model.Puzzle;

public class ProductMapper {

    public static ProductDTO toDTO( Product product){
        return switch (product){
            case BoardGame boardGame -> toBoardGameDTO(boardGame);
            case Dice dice -> toDiceDTO(dice);
            case Puzzle puzzle -> toPuzzleDto(puzzle);
            case null, default -> {
                assert product != null;
                throw new IllegalArgumentException("Invalid product type" + product.getClass().getSimpleName());
            }
        };
    }

    private static PuzzleDTO toPuzzleDto(Puzzle puzzle) {
        PuzzleDTO dto = new PuzzleDTO();
        dto.setId(puzzle.getId());
        dto.setTitle(puzzle.getTitle());
        dto.setAuthor(puzzle.getAuthor());
        dto.setPrice(puzzle.getPrice());
        dto.setDescription(puzzle.getDescription());
        dto.setWarehouse(puzzle.getWarehouse());
        //puzzle
        dto.setPuzzleMaterial(puzzle.getPuzzleMaterial());
        dto.setPiecesQuantity(puzzle.getPiecesQuantity());
        dto.setPuzzleSize(puzzle.getPuzzleSize());

        return dto;
    }

    private static DiceDTO toDiceDTO(Dice dice) {
        DiceDTO dto = new DiceDTO();
        dto.setId(dice.getId());
        dto.setTitle(dice.getTitle());
        dto.setAuthor(dice.getAuthor());
        dto.setPrice(dice.getPrice());
        dto.setDescription(dice.getDescription());
        dto.setWarehouse(dice.getWarehouse());
        //dice
        dto.setDiceNumber(dto.getDiceNumber());
        return dto;
    }

    private static BoardGameDTO toBoardGameDTO(BoardGame boardGame) {
        BoardGameDTO dto = new BoardGameDTO();
        dto.setId(boardGame.getId());
        dto.setTitle(boardGame.getTitle());
        dto.setAuthor(boardGame.getAuthor());
        dto.setPrice(boardGame.getPrice());
        dto.setDescription(boardGame.getDescription());
        dto.setWarehouse(boardGame.getWarehouse());
        //BG
        dto.setGameDuration(boardGame.getGameDuration());
        dto.setPlayersQuantity(boardGame.getPlayersQuantity());
        return dto;
    }
}
