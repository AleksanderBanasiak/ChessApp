package org.example.pieces;

import org.example.board.BoardMoves;
import org.example.pieces.piece.Piece;
import org.example.pieces.piece.PieceType;

public class Bishop implements Piece {

    private final PieceType pieceType;

    private final BoardMoves boardMoves;

    public Bishop(boolean isWhite) {
        this.pieceType = isWhite ? PieceType.WHITE_BISHOP : PieceType.BLACK_BISHOP;
        this.boardMoves = new BoardMoves();
    }

    @Override
    public PieceType pieceType() {
        return pieceType;
    }

    @Override
    public int[][] displayValidMoves(Piece piece, Piece[][] chessBoard, int[] pieceSpot) {
        int[][] validMoves = new int[8][8];
        bishopPrepare(validMoves, chessBoard, pieceSpot);
        return validMoves;
    }

    private void bishopPrepare(int[][] tab, Piece[][] chessBoard, int[] pieceSpot) {
        boolean[] blockedDirections = new boolean[4];
        bishopMoves(tab, chessBoard, pieceSpot, blockedDirections, pieceType == PieceType.WHITE_BISHOP, boardMoves);
    }

    protected static void bishopMoves(int[][] tab, Piece[][] chessBoard, int[] pieceSpot, boolean[] blockedDirections, boolean isWhite, BoardMoves boardMoves) {
        for (int i = 0; i < 8; i++) {
            int rowDec = pieceSpot[0] - 1 - i;
            int colDec = pieceSpot[1] - 1 - i;
            int colInc = pieceSpot[1] + 1 + i;
            int rowInc = pieceSpot[0] + 1 + i;

            // left top
            if (!blockedDirections[0] && rowDec >= 0 && colDec >= 0) {
                blockedDirections[0] = boardMoves.checkIsLegal(tab, chessBoard, rowDec, colDec, isWhite);
            }

            // right top
            if (!blockedDirections[1] && rowDec >= 0 && colInc < 8) {
                blockedDirections[1] = boardMoves.checkIsLegal(tab, chessBoard, rowDec, colInc, isWhite);
            }

            // left bottom
            if (!blockedDirections[2] && rowInc < 8 && colDec >= 0) {
                blockedDirections[2] = boardMoves.checkIsLegal(tab, chessBoard, rowInc, colDec, isWhite);
            }

            // right bottom
            if (!blockedDirections[3] && rowInc < 8 && colInc < 8) {
                blockedDirections[3] = boardMoves.checkIsLegal(tab, chessBoard, rowInc, colInc, isWhite);
            }
        }
    }


}