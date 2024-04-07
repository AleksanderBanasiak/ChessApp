package org.example.pieces;

public interface Piece {
    PieceType pieceType();

    int[][] displayValidMoves(Piece piece, Piece[][] chessBoard, int[] pieceSpot);

}
