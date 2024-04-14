package org.example.pieces;

import org.example.pieces.piece.Piece;
import org.example.pieces.piece.PieceType;

public class Pawn implements Piece {
    private final PieceType pieceType;

    public static boolean attackedPiece;

    public Pawn(boolean isWhite) {
        this.pieceType = isWhite ? PieceType.WHITE_PAWN : PieceType.BLACK_PAWN;
    }

    @Override
    public PieceType pieceType() {
        return pieceType;
    }

    @Override
    public int[][] displayValidMoves(Piece piece, Piece[][] chessBoard, int[] pieceSpot) {
        int[][] validMoves = new int[8][8];
        pawnMoves(validMoves, chessBoard, pieceSpot, piece.pieceType() == PieceType.WHITE_PAWN);
        return validMoves;
    }

    private void pawnMoves(int[][] tab, Piece[][] chessBoard, int[] pieceSpot,boolean isWhite) {

        if (!attackedPiece) {
            generateMoves(tab, chessBoard, pieceSpot[0], pieceSpot[1], -1, isWhite);
        }
        else {
            generateMoves(tab, chessBoard, pieceSpot[0], pieceSpot[1], 1, isWhite);
        }
    }

    private void generateMoves(int[][] tab, Piece[][] chessBoard, int row, int col, int direction, boolean isWhite) {
        int forwardOneStep = row + direction;
        int forwardTwoSteps = row + 2 * direction;

        if (row == 6 && chessBoard[forwardOneStep][col] == null) {
            ifLegalAdd(forwardOneStep, col, tab, chessBoard, isWhite);
            if (chessBoard[forwardTwoSteps][col] == null){
                ifLegalAdd(forwardTwoSteps, col, tab, chessBoard, isWhite);
            }
        }
        else if(isValidMove(forwardOneStep, col, chessBoard) && chessBoard[forwardOneStep][col] == null){
            ifLegalAdd(forwardOneStep, col, tab, chessBoard, isWhite);
        }
        if (isValidAttackMove(forwardOneStep, col - 1, chessBoard)) {
            ifLegalAdd(forwardOneStep, (col - 1), tab, chessBoard, isWhite);
        }
        if (isValidAttackMove(forwardOneStep, col + 1, chessBoard)) {
            ifLegalAdd(forwardOneStep, (col + 1), tab, chessBoard, isWhite);
        }

    }

    private boolean isValidMove(int row, int col, Piece[][] chessBoard) {
        return row >= 0 && row < 8 && col >= 0 && col < 8 && chessBoard[row][col] == null;
    }

    private boolean isValidAttackMove(int row, int col, Piece[][] chessBoard) {
        return row >= 0 && row < 8 && col >= 0 && col < 8 && chessBoard[row][col] != null;
    }

    private void ifLegalAdd(int row, int col, int[][] tab,Piece[][] chessBoard, boolean isWhite){

            if (isWhite) {
                if (chessBoard[row][col] == null  || chessBoard[row][col].pieceType().name().startsWith("B")) {
                    tab[row][col] = 1;
                }
            } else {
                if (chessBoard[row][col] == null || chessBoard[row][col].pieceType().name().startsWith("W")) {
                    tab[row][col] = 1;
                }
            }
    }

}
