package org.example.pieces;

public class Pawn implements Piece {
    private final PieceType pieceType;

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


    private void pawnMoves(int[][] tab, Piece[][] chessBoard, int[] pieceSpot,boolean isWhite){
        if (pieceSpot[0] == 6) {
            ifLegalAdd((pieceSpot[0] - 1), pieceSpot[1], tab, chessBoard, isWhite);
            ifLegalAdd((pieceSpot[0] - 2), pieceSpot[1], tab, chessBoard, isWhite);
        } else if (pieceSpot[0] - 1 >= 0 && chessBoard[pieceSpot[0] - 1][pieceSpot[1]] == null) {
            ifLegalAdd((pieceSpot[0] - 1), pieceSpot[1], tab, chessBoard, isWhite);
        }
        if (pieceSpot[0] - 1 >= 0 && pieceSpot[1] - 1 >= 0 && chessBoard[pieceSpot[0] - 1][pieceSpot[1] - 1] != null) {
            ifLegalAdd((pieceSpot[0] - 1), (pieceSpot[1] - 1), tab, chessBoard, isWhite);
        }
        if (pieceSpot[0] - 1 >= 0 && pieceSpot[1] + 1 < 8 && chessBoard[pieceSpot[0] - 1][pieceSpot[1] + 1] != null) {
            ifLegalAdd((pieceSpot[0] - 1), (pieceSpot[1] + 1), tab, chessBoard, isWhite);
        }
    }

    public void ifLegalAdd(int row, int col, int[][] tab,Piece[][] chessBoard, boolean isWhite){

        int numRows = chessBoard.length;
        int numCols = chessBoard[0].length;

        if (row >= 0 && row < numRows && col >= 0 && col < numCols) {
            if (isWhite) {
                if (chessBoard[row][col] == null || chessBoard[row][col].pieceType().name().startsWith("B")) {
                    tab[row][col] = 1;
                }
            } else {
                if (chessBoard[row][col] == null || chessBoard[row][col].pieceType().name().startsWith("W")) {
                    tab[row][col] = 1;
                }
            }
        }



    }

}
