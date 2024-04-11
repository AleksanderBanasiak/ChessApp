package org.example.pieces.piece;

public interface Piece {
    PieceType pieceType();

    int[][] displayValidMoves(Piece piece, Piece[][] chessBoard, int[] pieceSpot);

}
