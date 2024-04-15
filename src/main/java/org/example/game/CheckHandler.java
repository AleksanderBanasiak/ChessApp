package org.example.game;

import org.example.pieces.Pawn;
import org.example.pieces.piece.Piece;
import org.example.pieces.piece.PieceType;

import javax.swing.*;
import java.awt.*;

import static org.example.board.BoardMoves.showSelectedPieceSpot;

public class CheckHandler {

    private final ChessGame chessGame;

    public CheckHandler(ChessGame chessGame) {
        this.chessGame = chessGame;
    }

    public void handleCheckmate(boolean isWhiteTurn) {

        if(isWhiteTurn){
            JOptionPane.showMessageDialog(chessGame.frame, "Checkmate! Black wins");
            restartGame();
        }else {
            JOptionPane.showMessageDialog(chessGame.frame, "Checkmate! White wins");
            restartGame();
        }
    }

    private void restartGame() {
        ChessGame.moveCount = 0;
        chessGame.restartGame();
    }

    public void isCheck(boolean isWhiteTurn) {
        int[] kingPosition = getKingPosition(isWhiteTurn, chessGame.chessBoard);
        int[][] attackedSquares = getAttackedSquares(isWhiteTurn, chessGame.chessBoard);

        if (attackedSquares[kingPosition[0]][kingPosition[1]] == 1) {
            chessGame.board[kingPosition[0]][kingPosition[1]].setBackground(Color.red);
        }
    }

    protected int[] getKingPosition(boolean isWhiteTurn, Piece[][] chessBoard) {
        PieceType typeOfKing = isWhiteTurn ? PieceType.WHITE_KING : PieceType.BLACK_KING;
        int[] kingPosition = new int[2];

        for (int i = 0; i < chessBoard.length; i++) {
            for (int j = 0; j < chessBoard.length; j++) {
                if (chessBoard[i][j] != null && chessBoard[i][j].pieceType().equals(typeOfKing)) {
                    kingPosition[0] = i;
                    kingPosition[1] = j;
                    break;
                }
            }
        }
        return kingPosition;
    }

    protected int[][] getAttackedSquares(boolean isWhiteTurn, Piece[][] chessBoard) {
        int[][] attackedSquares = new int[8][8];

        for (Piece[] row : chessBoard) {
            for (Piece piece : row) {
                if (piece != null && isPieceOfCorrectColor(piece, isWhiteTurn)) {

                    if(piece.pieceType() == PieceType.BLACK_PAWN || piece.pieceType() == PieceType.WHITE_PAWN){
                        Pawn.attackedPiece = true;
                    }
                    int[][] validMoves = piece.displayValidMoves(piece, chessBoard, showSelectedPieceSpot(piece, chessBoard));
                    mergeAttackedSquares(attackedSquares, validMoves);
                }
            }
        }
        if(Pawn.attackedPiece){
            Pawn.attackedPiece = false;
        }
        return attackedSquares;
    }

    private void mergeAttackedSquares(int[][] attackedSquares, int[][] validMoves) {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (validMoves[i][j] == 1) {
                    attackedSquares[i][j] = 1;
                }
            }
        }
    }

    private boolean isPieceOfCorrectColor(Piece piece, boolean isWhiteTurn) {
        return (piece.pieceType().name().startsWith("B") && isWhiteTurn) ||
                (piece.pieceType().name().startsWith("W") && !isWhiteTurn);
    }

}
