package org.example.game;

import java.awt.*;
import java.util.List;

public class BoardColorHandler {

    private final ChessGame chessGame;

    public BoardColorHandler(ChessGame chessGame) {
        this.chessGame = chessGame;
    }

    protected void resetBoardColors() {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                chessGame.board[i][j].setBackground(chessGame.originalColors[i][j]);
            }
        }
    }

    protected void showValidMovesRemovingCheck(List<int[]> validMoves) {
        int[][] resultTab = new int[8][8];

        for (int[] move : validMoves) {
            int moveRow = move[0];
            int moveCol = move[1];
            if (moveRow >= 0 && moveRow < resultTab.length && moveCol >= 0 && moveCol < resultTab[0].length) {
                resultTab[moveRow][moveCol] = 1;
            }
        }
        loop(resultTab);
    }

    private void loop(int[][] resultTab) {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (resultTab[i][j] == 1) {
                    chessGame.getBoard()[i][j].setBackground(new Color(255, 136, 108));
                }
            }
        }
    }

}
