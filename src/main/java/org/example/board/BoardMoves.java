package org.example.board;

import org.example.pieces.piece.Piece;

public class BoardMoves {


    public static int[] showSelectedPieceSpot(Piece piece, Piece[][] chessBoard){
        int[] spot = new int[2];
        for (int i = 0; i < chessBoard.length; i++) {
            for (int j = 0; j < chessBoard.length; j++) {
                if(chessBoard[i][j] == piece){
                     spot[0] = i;
                     spot[1] = j;
                }
            }
        }
        return spot;
    }

    public boolean checkIsLegal(int[][] tab, Piece[][] chessBoard, int row, int col, boolean isWhite) {
        if (chessBoard[row][col] == null) {
            tab[row][col] = 1;
        } else {
            boolean enemyPiece = chessBoard[row][col].pieceType().name().startsWith(isWhite ? "B" : "W");
            if (enemyPiece) {
                tab[row][col] = 1;
            }
            return true;
        }
        return false;
    }
    public void checkMove(int[][] tab, Piece[][] chessBoard, int row, int col, boolean isWhite) {
        if (isValidPosition(row, col)) {
            checkIsLegal(tab, chessBoard, row, col, isWhite);
        }
    }
    private boolean isValidPosition(int row, int col) {
        return row >= 0 && row < 8 && col >= 0 && col < 8;
    }
}
