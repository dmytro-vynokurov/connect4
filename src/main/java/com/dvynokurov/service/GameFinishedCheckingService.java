package com.dvynokurov.service;

import com.dvynokurov.model.Cell;
import com.dvynokurov.model.Column;
import com.dvynokurov.model.Grid;
import com.dvynokurov.model.Player;
import org.springframework.stereotype.Service;

@Service
public class GameFinishedCheckingService {

    private static final int FOUR = 4;

    public static final int NO_OWNER_INDEX = 0;
    public static final int FIRST_PLAYER_INDEX = 1;
    public static final int SECOND_PLAYER_INDEX = 2;

    public boolean checkFinished(Grid grid, Player player) {
        int[][] matrix = convertToMatrix(grid);
        int playerIndex = getPlayerIndex(player);
        return playerWon(matrix, playerIndex);
    }

    private int getPlayerIndex(Player player) {
        if(player.equals(Player.FIRST)) return FIRST_PLAYER_INDEX;
        else return SECOND_PLAYER_INDEX;
    }

    private boolean playerWon(int[][] matrix, int playerIndex) {
        return
            checkHorizontal(matrix, playerIndex) ||
            checkVertical(matrix, playerIndex) ||
            checkLeftUpperToRightLowerDiagonal(matrix, playerIndex) ||
            checkRightUpperToLeftLowerDiagonal(matrix, playerIndex);
    }

    private boolean checkHorizontal(int[][] matrix, int playerIndex) {
        for (int i = 0; i < matrix.length; i++) {
            int counter = 0;
            for (int j = 0; j < matrix[0].length; j++) {
                if(matrix[i][j]==playerIndex) counter++;
                else counter = 0;
                if(counter==FOUR) return true;
            }
        }
        return false;
    }

    private boolean checkVertical(int[][] matrix, int playerIndex) {
        for (int i = 0; i < matrix[0].length; i++) {
            int counter = 0;
            for (int j = 0; j < matrix.length; j++) {
                if(matrix[j][i]==playerIndex) counter++;
                else counter = 0;
                if(counter==FOUR) return true;
            }
        }
        return false;
    }

    private boolean checkLeftUpperToRightLowerDiagonal(int[][] matrix, int playerIndex) {
        return checkAllDiagonals(matrix, playerIndex, 0, 1);
    }

    private boolean checkRightUpperToLeftLowerDiagonal(int[][] matrix, int playerIndex) {
        return checkAllDiagonals(matrix, playerIndex, matrix[0].length-1, -1);
    }

    private boolean checkAllDiagonals(int[][] matrix, int playerIndex, int startColumn, int leftUpperToRightLowerColumnShift) {
        for (int i = matrix.length-FOUR; i > 0; i--) {
            if (checkDiagonal(matrix, playerIndex, i, startColumn, leftUpperToRightLowerColumnShift)) return true;
        }
        for (int i = 0; i < matrix[0].length; i++) {
            if(checkDiagonal(matrix, playerIndex, 0, i, leftUpperToRightLowerColumnShift)) return true;
        }
        return false;
    }

    private boolean checkDiagonal(int[][] matrix, int playerIndex, int row, int col, int columnShift) {
        int counter = 0;
        while (row < matrix.length && col < matrix[0].length && col >= 0) {
            if (matrix[row][col] == playerIndex) counter++;
            else counter = 0;
            if (counter == FOUR) return true;
            row++;
            col += columnShift;
        }
        return false;
    }

    private int[][] convertToMatrix(Grid grid){
        int[][] matrix = new int[grid.getHeight()][grid.getWidth()];
        for (int i = 0; i < grid.getWidth(); i++) {
            Column column = grid.getColumns().get(i);
            for (int j = 0; j < grid.getHeight(); j++) {
                Cell cell = column.getCells().get(j);
                matrix[j][i] = encodeCellOwner(cell);
            }
        }
        return matrix;
    }

    private int encodeCellOwner(Cell cell) {
        if(cell.getOwner()==null) return NO_OWNER_INDEX;
        return getPlayerIndex(cell.getOwner());
    }

}
