package org.example.pieces;

import org.example.board.BoardMoves;

public class King implements Piece{

    private final PieceType pieceType;
    private final BoardMoves boardMoves;

    public King(boolean isWhite) {
        this.pieceType = isWhite ? PieceType.WHITE_KING : PieceType.BLACK_KING;
        this.boardMoves = new BoardMoves();
    }

    @Override
    public PieceType pieceType() {
        return pieceType;
    }

    @Override
    public int[][] displayValidMoves(Piece piece, Piece[][] chessBoard, int[] pieceSpot) {
        int[][] validMoves = new int[8][8];
        kingMove(validMoves, chessBoard, pieceSpot, pieceType == PieceType.WHITE_KING);
        return validMoves;
    }

    private void kingMove(int[][] tab, Piece[][] chessBoard, int[] pieceSpot, boolean isWhite) {
        int row = pieceSpot[0];
        int col = pieceSpot[1];

        boardMoves.checkMove(tab, chessBoard, row - 1, col, isWhite); // Top
        boardMoves.checkMove(tab, chessBoard, row + 1, col, isWhite); // Bottom
        boardMoves.checkMove(tab, chessBoard, row, col - 1, isWhite); // Left
        boardMoves.checkMove(tab, chessBoard, row, col + 1, isWhite); // Right
        boardMoves.checkMove(tab, chessBoard, row - 1, col + 1, isWhite); // Top Right
        boardMoves.checkMove(tab, chessBoard, row + 1, col + 1, isWhite); // Bottom Right
        boardMoves.checkMove(tab, chessBoard, row - 1, col - 1, isWhite); // Top Left
        boardMoves.checkMove(tab, chessBoard, row + 1, col - 1, isWhite); // Bottom Left
    }


}
