package org.example.pieces;

import org.example.board.BoardMoves;
import org.example.pieces.piece.Piece;
import org.example.pieces.piece.PieceType;

public class Rook implements Piece {

    private final PieceType pieceType;

    private final BoardMoves boardMoves;

    public Rook(boolean isWhite) {
        this.pieceType = isWhite ? PieceType.WHITE_ROOK : PieceType.BLACK_ROOK;
        this.boardMoves = new BoardMoves();
    }

    @Override
    public PieceType pieceType() {
        return pieceType;
    }

    @Override
    public int[][] displayValidMoves(Piece piece, Piece[][] chessBoard, int[] pieceSpot) {
        int[][] validMoves = new int[8][8];
        rookPrepare(validMoves, chessBoard, pieceSpot);
        return validMoves;
    }

    private void rookPrepare(int[][] tab, Piece[][] chessBoard, int[] pieceSpot) {
        boolean[] blockedDirections = new boolean[4];
        rookMoves(tab, chessBoard, pieceSpot, blockedDirections, pieceType == PieceType.WHITE_ROOK, boardMoves);
    }

    protected static void rookMoves(int[][] tab, Piece[][] chessBoard, int[] pieceSpot, boolean[] blockedDirections, boolean isWhite, BoardMoves boardMoves) {
        for (int i = 0; i < 8; i++) {

            int left = pieceSpot[1] -1 -i;
            int right = pieceSpot[1] +1 +i;
            int top = pieceSpot[0] -1 -i;
            int bottom = pieceSpot[0] +1 +i;

            if(!blockedDirections[0] && left >=0){
                blockedDirections[0] = boardMoves.checkIsLegal(tab, chessBoard, pieceSpot[0], left,  isWhite);
            }
             if(!blockedDirections[1] && right < 8){
                blockedDirections[1] = boardMoves.checkIsLegal(tab, chessBoard, pieceSpot[0], right,  isWhite);
            }
             if(!blockedDirections[2] && top >=0){
                blockedDirections[2] = boardMoves.checkIsLegal(tab, chessBoard, top, pieceSpot[1],  isWhite);
            }
             if(!blockedDirections[3] && bottom <8){
                blockedDirections[3] = boardMoves.checkIsLegal(tab, chessBoard, bottom, pieceSpot[1],  isWhite);
            }
        }
    }


}
