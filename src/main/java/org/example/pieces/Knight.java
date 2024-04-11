package org.example.pieces;

import org.example.board.BoardMoves;
import org.example.pieces.piece.Piece;
import org.example.pieces.piece.PieceType;

public class Knight implements Piece {

    private final PieceType pieceType;
    private final BoardMoves boardMoves;

    public Knight(boolean isWhite) {
        this.pieceType = isWhite ? PieceType.WHITE_KNIGHT : PieceType.BLACK_KNIGHT;
        this.boardMoves = new BoardMoves();
    }

    @Override
    public PieceType pieceType() {
        return pieceType;
    }

    @Override
    public int[][] displayValidMoves(Piece piece, Piece[][] chessBoard, int[] pieceSpot) {
        int[][] validMoves = new int[8][8];
        knightMoves(validMoves, chessBoard, pieceSpot, pieceType == PieceType.WHITE_KNIGHT);
        return validMoves;
    }

    private void knightMoves(int[][] tab, Piece[][] chessBoard, int[] pieceSpot, boolean isWhite) {

        boardMoves.checkMove(tab, chessBoard, pieceSpot[0] +2 , pieceSpot[1] +1 , isWhite);
        boardMoves.checkMove(tab, chessBoard, pieceSpot[0] +2 , pieceSpot[1] -1, isWhite);
        boardMoves.checkMove(tab, chessBoard, pieceSpot[0] -2 , pieceSpot[1] -1, isWhite);
        boardMoves.checkMove(tab, chessBoard, pieceSpot[0] -2 , pieceSpot[1] +1, isWhite);

        boardMoves.checkMove(tab, chessBoard, pieceSpot[0] +1 , pieceSpot[1] +2, isWhite);
        boardMoves.checkMove(tab, chessBoard, pieceSpot[0] -1 , pieceSpot[1] +2, isWhite);
        boardMoves.checkMove(tab, chessBoard, pieceSpot[0] +1 , pieceSpot[1] -2, isWhite);
        boardMoves.checkMove(tab, chessBoard, pieceSpot[0] -1 , pieceSpot[1] -2, isWhite);
    }
}
