package org.example.game;

import org.example.pieces.piece.Piece;
import org.example.pieces.piece.PieceType;

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
    private static int moveCount = 0;

    public TileMouseListener(ChessGame chessGame, int row, int col) {
        this.chessGame = chessGame;
        this.row = row;
        this.col = col;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        JButton clickedButton = (JButton) e.getSource();
        isWhiteTurn = moveCount % 2 == 0;

        boolean isCheck = isCheck(isWhiteTurn);

        if (!isCheck) {
            handleNonCheckState(clickedButton);
        } else {
            handleCheckState(clickedButton);
        }
    }

    private void handleNonCheckState(JButton clickedButton) {
        if (chessGame.getSelectedRow() == -1) {
            selectPieceAndShowValidMoves();
        } else {
            if (clickedButton.getBackground().equals(new Color(255, 136, 108))) {
                movePieceAndEndTurn();
            } else {
                resetBoardColorsAndShowValidMoves();
            }
        }
    }


    private void handleCheckState(JButton clickedButton) {
        Map<Piece, List<int[]>> allValidDefensiveMoves = getAllValidDefensiveMoves(getAllDefensiveMoves());

        if (allValidDefensiveMoves.isEmpty()) {
            handleCheckmate();
        } else {
            if (chessGame.getSelectedRow() == -1) {
                selectDefensiveMoveAndShowValidMoves(allValidDefensiveMoves);
            } else {
                if (clickedButton.getBackground().equals(new Color(255, 136, 108))) {
                    movePieceAndEndTurn();
                } else {
                    resetBoardColorsAndShowValidMoves(allValidDefensiveMoves);
                }
            }
        }
    }

    private void selectPieceAndShowValidMoves() {
        showValidMovesForPiece(row, col, isWhiteTurn);
        chessGame.setSelectedRow(row);
        chessGame.setSelectedCol(col);
    }

    private void movePieceAndEndTurn() {
        chessGame.movePiece(chessGame.getSelectedRow(), chessGame.getSelectedCol(), row, col);
        moveCount++;
        chessGame.setSelectedRow(-1);
        chessGame.setSelectedCol(-1);
    }

    private void resetBoardColorsAndShowValidMoves() {
        resetBoardColors();
        selectPieceAndShowValidMoves();
    }

    private void selectDefensiveMoveAndShowValidMoves(Map<Piece, List<int[]>> allValidDefensiveMoves) {
        if (allValidDefensiveMoves.containsKey(chessGame.chessBoard[row][col])) {
            showValidMovesRemovingCheck(allValidDefensiveMoves.get(chessGame.chessBoard[row][col]));
        }
        chessGame.setSelectedRow(row);
        chessGame.setSelectedCol(col);
    }

    private void resetBoardColorsAndShowValidMoves(Map<Piece, List<int[]>> allValidDefensiveMoves) {
        resetBoardColors();
        selectDefensiveMoveAndShowValidMoves(allValidDefensiveMoves);
    }

    private void showValidMovesForPiece(int row, int col, boolean isWhiteTurn) {
        int[][] pieceType = showValidMoves(row, col, chessGame.getChessBoard(), isWhiteTurn);
        loop(pieceType);
    }



    private void resetBoardColors() {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                chessGame.board[i][j].setBackground(chessGame.originalColors[i][j]);
            }
        }
    }

    private boolean isCheck(boolean isWhiteTurn) {
        int[] kingPosition = getKingPosition(isWhiteTurn, chessGame.chessBoard);
        int[][] attackedSquares = getAttackedSquares(isWhiteTurn, chessGame.chessBoard);

        if (attackedSquares[kingPosition[0]][kingPosition[1]] == 1) {
            chessGame.board[kingPosition[0]][kingPosition[1]].setBackground(Color.red);
            return true;
        }
        return false;
    }

    private int[] getKingPosition(boolean isWhiteTurn, Piece[][] chessBoard) {
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

    private Map<Piece, List<int[]>> getAllDefensiveMoves() {
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

        return mapAllSingleMovesForSpecificPiece(allLegalMoves, allPieces);
    }

    private Map<Piece, List<int[]>> mapAllSingleMovesForSpecificPiece(List<int[][]> allLegalMoves, List<Piece> allPieces) {
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

                int[] kingPosition = getKingPosition(isWhiteTurn, temp);
                int[][] attackedSquares = getAttackedSquares(isWhiteTurn, temp);

                if (attackedSquares[kingPosition[0]][kingPosition[1]] == 0) {
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

    private void showValidMovesRemovingCheck(List<int[]> validMoves) {
        int[][] resultTab = new int[8][8];

        for (int[] move : validMoves) {
            int moveRow = move[0];
            int moveCol = move[1];
            if (moveRow >= 0 && moveRow < resultTab.length && moveCol >= 0 && moveCol < resultTab[0].length) {
                resultTab[moveRow][moveCol] = 1;
            }
        }

        loop(resultTab);
    }

    private void loop(int[][] resultTab) {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (resultTab[i][j] == 1) {
                    chessGame.getBoard()[i][j].setBackground(new Color(255, 136, 108));
                }
            }
        }
    }

    private void addToMap(Map<Piece, List<int[]>> map, Piece piece, int[] value) {
        map.computeIfAbsent(piece, k -> new ArrayList<>()).add(value);
    }
    private int[][] getAttackedSquares(boolean isWhiteTurn, Piece[][] chessBoard) {
        int[][] attackedSquares = new int[8][8];

        for (Piece[] row : chessBoard) {
            for (Piece piece : row) {
                if (piece != null && isPieceOfCorrectColor(piece, isWhiteTurn)) {
                    int[][] validMoves = piece.displayValidMoves(piece, chessBoard, showSelectedPieceSpot(piece, chessBoard));
                    mergeAttackedSquares(attackedSquares, validMoves);
                }
            }
        }

        return attackedSquares;
    }

    private boolean isPieceOfCorrectColor(Piece piece, boolean isWhiteTurn) {
        return (piece.pieceType().name().startsWith("B") && isWhiteTurn) ||
                (piece.pieceType().name().startsWith("W") && !isWhiteTurn);
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

    private void handleCheckmate() {
        // TODO: Implement checkmate logic
        System.out.println("Checkmate!");

        JOptionPane.showMessageDialog(chessGame.frame, "xd");
    }
}
