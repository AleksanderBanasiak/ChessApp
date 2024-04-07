package org.example;

import org.example.pieces.Piece;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import static org.example.board.BoardMoves.showValidMoves;

public class TileMouseListener extends MouseAdapter {
    private final ChessGame chessGame;
    private final int row;
    private final int col;
    boolean isWhiteTurn = true;
    static int move = 0;

    public TileMouseListener(ChessGame chessGame, int row, int col) {
        this.chessGame = chessGame;
        this.row = row;
        this.col = col;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        JButton clickedButton = (JButton) e.getSource();

        if (chessGame.getSelectedRow() == -1) {
            isWhiteTurn = move % 2 == 0;
            showValidMovesForPiece(row, col, isWhiteTurn);
            chessGame.setSelectedRow(row);
            chessGame.setSelectedCol(col);
        } else {
            if (clickedButton.getBackground().equals(new Color(255, 136, 108))) {
                chessGame.movePiece(chessGame.getSelectedRow(), chessGame.getSelectedCol(), row, col);
                move++;
                chessGame.setSelectedRow(-1);
                chessGame.setSelectedCol(-1);
            } else {
                resetBoardColors();
                isWhiteTurn = move % 2 == 0;
                showValidMovesForPiece(row, col, isWhiteTurn);
                chessGame.setSelectedRow(row);
                chessGame.setSelectedCol(col);
            }
        }
    }
    private void showValidMovesForPiece(int row, int col, boolean isWhiteTurn) {
        int[][] pieceType = showValidMoves(row, col, chessGame.getChessBoard(), isWhiteTurn);
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (pieceType[i][j] == 1) {
                    chessGame.getBoard()[i][j].setBackground(new Color(255, 136, 108));
                }
            }
        }
    }
    public void resetBoardColors() {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
              chessGame.board[i][j].setBackground(chessGame.originalColors[i][j]);
            }
        }
    }
}
