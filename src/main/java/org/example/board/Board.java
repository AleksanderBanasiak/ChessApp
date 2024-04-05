package org.example.board;

import org.example.pieces.PieceType;

import javax.swing.*;

public class Board {

    public int[][] setupBoard(){

        int[][] chessBoard = new int[8][8];

        for (int col = 0; col < 8; col++) {
            chessBoard[1][col] = PieceType.BLACK_PAWN.value;
            chessBoard[6][col] = PieceType.WHITE_PAWN.value;
        }
            chessBoard[0][0] = PieceType.BLACK_ROOK.value;
            chessBoard[0][1] = PieceType.BLACK_KNIGHT.value;
            chessBoard[0][2] = PieceType.BLACK_BISHOP.value;
            chessBoard[0][3] = PieceType.BLACK_QUEEN.value;
            chessBoard[0][4] = PieceType.BLACK_KING.value;
            chessBoard[0][5] = PieceType.BLACK_BISHOP.value;
            chessBoard[0][6] = PieceType.BLACK_KNIGHT.value;
            chessBoard[0][7] = PieceType.BLACK_ROOK.value;

            chessBoard[7][0] = PieceType.WHITE_ROOK.value;
            chessBoard[7][1] = PieceType.WHITE_KNIGHT.value;
            chessBoard[7][2] = PieceType.WHITE_BISHOP.value;
            chessBoard[7][3] = PieceType.WHITE_QUEEN.value;
            chessBoard[7][4] = PieceType.WHITE_KING.value;
            chessBoard[7][5] = PieceType.WHITE_BISHOP.value;
            chessBoard[7][6] = PieceType.WHITE_KNIGHT.value;
            chessBoard[7][7] = PieceType.WHITE_ROOK.value;

        return chessBoard;
    }


    public void printBoard(int[][] board){
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                System.out.print(board[i][j]+"");
            }
            System.out.println(" ");
        }

    }


}
