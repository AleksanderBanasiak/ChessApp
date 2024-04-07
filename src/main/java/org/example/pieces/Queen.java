package org.example.pieces;

import org.example.board.BoardMoves;

public class Queen implements Piece{
    private final PieceType pieceType;
    private final BoardMoves boardMoves;

    public Queen(boolean isWhite) {
        this.pieceType = isWhite ? PieceType.WHITE_QUEEN : PieceType.BLACK_QUEEN;
        this.boardMoves = new BoardMoves();
    }

    @Override
    public PieceType pieceType() {
        return pieceType;
    }

    @Override
    public int[][] displayValidMoves(Piece piece, Piece[][] chessBoard, int[] pieceSpot) {
        int[][] validMoves = new int[8][8];
        queenMoves(validMoves, chessBoard, pieceSpot);
        return validMoves;
    }

    private void queenMoves(int[][] tab, Piece[][] chessBoard, int[] pieceSpot) {
        boolean[] blockedDirectionsForRook = new boolean[4];
        boolean[] blockedDirectionsForBishop = new boolean[4];

        boolean isWhite = pieceType == PieceType.WHITE_QUEEN;

        // Rook moves
        Rook.rookMoves(tab, chessBoard, pieceSpot, blockedDirectionsForRook, isWhite, boardMoves);

        // Bishop moves
        Bishop.bishopMoves(tab, chessBoard, pieceSpot, blockedDirectionsForBishop, isWhite, boardMoves);
    }
}
