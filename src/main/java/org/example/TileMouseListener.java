package org.example;

import org.example.pieces.Piece;
import org.example.pieces.PieceType;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.example.board.BoardMoves.showSelectedPieceSpot;
import static org.example.board.BoardMoves.showValidMoves;

public class TileMouseListener extends MouseAdapter {
    private final ChessGame chessGame;
    private final int row;
    private final int col;
    boolean isWhiteTurn = true;
    static int move = 0;

    public TileMouseListener(ChessGame chessGame, int row, int col) {
        this.chessGame = chessGame;
        this.row = row;
        this.col = col;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        JButton clickedButton = (JButton) e.getSource();
        isWhiteTurn = move % 2 == 0;

        boolean check = isCheck(isWhiteTurn);

        if (chessGame.getSelectedRow() == -1) {
            isWhiteTurn = move % 2 == 0;
            showValidMovesForPiece(row, col, isWhiteTurn);
            chessGame.setSelectedRow(row);
            chessGame.setSelectedCol(col);
        } else {
            if (clickedButton.getBackground().equals(new Color(255, 136, 108))) {
                chessGame.movePiece(chessGame.getSelectedRow(), chessGame.getSelectedCol(), row, col);
                move++;
                chessGame.setSelectedRow(-1);
                chessGame.setSelectedCol(-1);
            } else {
                resetBoardColors();
                isWhiteTurn = move % 2 == 0;
                showValidMovesForPiece(row, col, isWhiteTurn);
                chessGame.setSelectedRow(row);
                chessGame.setSelectedCol(col);
            }
        }
    }
    private void showValidMovesForPiece(int row, int col, boolean isWhiteTurn) {
        int[][] pieceType = showValidMoves(row, col, chessGame.getChessBoard(), isWhiteTurn);
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (pieceType[i][j] == 1) {
                    chessGame.getBoard()[i][j].setBackground(new Color(255, 136, 108));
                }
            }
        }
    }
    public void resetBoardColors() {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
              chessGame.board[i][j].setBackground(chessGame.originalColors[i][j]);
            }
        }
    }


    private int[][] getAttackedSquares(boolean isWhiteTurn, Piece[][] chessBoard) {
        List<int[][]> allAttackedSquares = new ArrayList<>();

        for (Piece[] pieces : chessBoard) {
            for (Piece currentPiece : pieces) {
                if (currentPiece != null) {
                    boolean isPieceOfCorrectColor = (currentPiece.pieceType().name().startsWith("B") && isWhiteTurn) ||
                            (currentPiece.pieceType().name().startsWith("W") && !isWhiteTurn);
                    if (isPieceOfCorrectColor) {
                        int[][] attackedSquares = currentPiece.displayValidMoves(currentPiece, chessBoard, showSelectedPieceSpot(currentPiece, chessBoard));
                        allAttackedSquares.add(attackedSquares);
                    }
                }
            }
        }

        int[][] allAttacked = new int[8][8];
        for (int[][] attackedSquare : allAttackedSquares) {
            for (int i = 0; i < 8; i++) {
                for (int j = 0; j < 8; j++) {
                    if (attackedSquare[i][j] == 1) {
                        allAttacked[i][j] = 1;
                    }
                }
            }
        }
        return allAttacked;
    }

    private int[] getKingPosition(boolean isWhiteTurn) {

        PieceType typeOfKing = isWhiteTurn ? PieceType.WHITE_KING : PieceType.BLACK_KING;


        int[] kingPosition = new int[2];

        for (int i = 0; i < chessGame.chessBoard.length; i++) {
            for (int j = 0; j < chessGame.chessBoard.length; j++) {
                if (chessGame.chessBoard[i][j] != null && chessGame.chessBoard[i][j].pieceType().equals(typeOfKing)) {
                    kingPosition[0] = i;
                    kingPosition[1] = j;
                    break;
                }
            }
        }
        return kingPosition;
    }


    private boolean isCheck(boolean isWhiteTurn){

        int[] kingPosition = getKingPosition(isWhiteTurn);


        int[][] attackedSquares = getAttackedSquares(isWhiteTurn, chessGame.chessBoard);




        if(attackedSquares[kingPosition[0]][kingPosition[1]] == 1){
            System.out.println("check!"+ isWhiteTurn);


            Map<Piece, List<int[]>> allValidDefensiveMoves = getAllValidDefensiveMoves(getAllDefensiveMoves(isWhiteTurn));

            System.out.println(allValidDefensiveMoves.size());


        }

        return true;

    }

    private Map<Piece, List<int[]>> getAllValidDefensiveMoves(Map<Piece, List<int[]>> allDefensiveMoves) {

        Map<Piece, List<int[]>> allValidMoves = new HashMap<>();

        for (Map.Entry<Piece, List<int[]>> entry : allDefensiveMoves.entrySet()) {
            Piece piece = entry.getKey();
            List<int[]> moves = entry.getValue();

            for (int[] ints : moves) {
                Piece[][] temp = copyChessBoard(chessGame.chessBoard);

                int[] pieceSpot = showSelectedPieceSpot(piece, temp);

                temp[ints[0]][ints[1]] = temp[pieceSpot[0]][pieceSpot[1]];
                temp[pieceSpot[0]][pieceSpot[1]] = null;

                int[] kingPosition = getKingPosition(isWhiteTurn);
                int[][] attackedSquares = getAttackedSquares(isWhiteTurn, temp);

                if (attackedSquares[kingPosition[0]][kingPosition[1]] == 0) {

                    // tutaj powinno byc sprawdzenie czy król dalej jest atakowany a nie czy pole jest 0 bo teraz jest tak że król może zasłonić to pole

                    // no i kolejna rzecz to totalnie przeprojektowac ruchy piona żeby możliwe było bicie tylko na skos i żeby nie mógł przeskakiwać przez inne figury

                    addToMap(allValidMoves, piece, ints);
                }
            }
        }
        return allValidMoves;
    }

    private Piece[][] copyChessBoard(Piece[][] chessBoard) {
        Piece[][] copy = new Piece[8][8];
        for (int i = 0; i < chessBoard.length; i++) {
            System.arraycopy(chessBoard[i], 0, copy[i], 0, chessBoard[i].length);
        }
        return copy;
    }


    private Map<Piece, List<int[]>> getAllDefensiveMoves(boolean isWhiteTurn){

        List<int[][]> allLegalMoves = new ArrayList<>();

        List<Piece> allPieces = new ArrayList<>();

        for (Piece[] row : chessGame.chessBoard) {
            for (Piece currentPiece : row) {
                if (currentPiece != null) {
                    boolean isPieceWhite = currentPiece.pieceType().name().startsWith("W");
                    if ((isWhiteTurn && isPieceWhite) || (!isWhiteTurn && !isPieceWhite)) {
                        allPieces.add(currentPiece);
                    }
                }
            }
        }

        for (Piece piece : allPieces) {
            int[][] validMoves = piece.displayValidMoves(piece, chessGame.chessBoard, showSelectedPieceSpot(piece, chessGame.chessBoard));
            allLegalMoves.add(validMoves);
        }
        Map<Piece, List<int[]>> allSingleMovesForSpecificPiece = new HashMap<>();
        for (int i = 0; i < allLegalMoves.size(); i++) {
            for (int j = 0; j < 8; j++) {
                for (int k = 0; k < 8; k++) {
                    if (allLegalMoves.get(i)[j][k] == 1) {
                        int[] tempTab = new int[]{j, k};
                        addToMap(allSingleMovesForSpecificPiece, allPieces.get(i), tempTab);
                    }
                }
            }
        }
        return allSingleMovesForSpecificPiece;
    }

    private static void addToMap(Map<Piece, List<int[]>> map, Piece piece, int[] value) {
        map.computeIfAbsent(piece, k -> new ArrayList<>()).add(value);
    }



}
