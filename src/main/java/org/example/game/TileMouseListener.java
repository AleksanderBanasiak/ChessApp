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

public class TileMouseListener extends MouseAdapter {
    private final ChessGame chessGame;
    private final int row;
    private final int col;
    boolean isWhiteTurn = true;
    private final BoardColorHandler boardColorHandler;
    private final CheckHandler checkHandler;

    public TileMouseListener(ChessGame chessGame, int row, int col) {
        this.chessGame = chessGame;
        this.row = row;
        this.col = col;
        this.boardColorHandler = new BoardColorHandler(chessGame);
        this.checkHandler = new CheckHandler(chessGame);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        JButton clickedButton = (JButton) e.getSource();
        isWhiteTurn =  ChessGame.moveCount % 2 == 0;
        handleMove(clickedButton);

    }

    private void handleMove(JButton clickedButton) {
        Map<Piece, List<int[]>> allValidDefensiveMoves = getAllValidDefensiveMoves(getAllDefensiveMoves());

        if (allValidDefensiveMoves.isEmpty()) {
            checkHandler.handleCheckmate(isWhiteTurn);
        } else {
            if (chessGame.getSelectedRow() == -1) {
                selectDefensiveMoveAndShowValidMoves(allValidDefensiveMoves);
            } else {
                if (clickedButton.getBackground().equals(new Color(255, 136, 108))) {
                    movePieceAndEndTurn();
                } else {
                    resetBoardColorsAndShowValidMoves(allValidDefensiveMoves);
                    checkHandler.isCheck(isWhiteTurn);
                }
            }
        }
    }

    private void movePieceAndEndTurn() {
        chessGame.movePiece(chessGame.getSelectedRow(), chessGame.getSelectedCol(), row, col);
        ChessGame.moveCount++;
        chessGame.setSelectedRow(-1);
        chessGame.setSelectedCol(-1);
    }

    private void selectDefensiveMoveAndShowValidMoves(Map<Piece, List<int[]>> allValidDefensiveMoves) {
        if (allValidDefensiveMoves.containsKey(chessGame.chessBoard[row][col])) {
            boardColorHandler.showValidMovesRemovingCheck(allValidDefensiveMoves.get(chessGame.chessBoard[row][col]));
        }
        chessGame.setSelectedRow(row);
        chessGame.setSelectedCol(col);
    }

    private void resetBoardColorsAndShowValidMoves(Map<Piece, List<int[]>> allValidDefensiveMoves) {
        boardColorHandler.resetBoardColors();
        selectDefensiveMoveAndShowValidMoves(allValidDefensiveMoves);
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
        Map<Piece, List<int[]>> pieceListMap = mapAllSingleMovesForSpecificPiece(allLegalMoves, allPieces);


        Map<Piece, Boolean> isPieceMove = ChessGame.isPieceMove;


        prepareCastling(isPieceMove, pieceListMap);


        return pieceListMap;
    }

    private void prepareCastling(Map<Piece, Boolean> isPieceMove, Map<Piece, List<int[]>> pieceListMap) {

        int[] kingPosition = checkHandler.getKingPosition(isWhiteTurn, chessGame.chessBoard);

        Piece leftRook = null;
        Piece rightRook = null;
        Piece king = chessGame.chessBoard[kingPosition[0]][kingPosition[1]];

        if(chessGame.chessBoard[7][0] !=null) {
            if (chessGame.chessBoard[7][0].pieceType().equals(PieceType.BLACK_ROOK) ||
                    chessGame.chessBoard[7][0].pieceType().equals(PieceType.WHITE_ROOK)) {
                leftRook = chessGame.chessBoard[7][0];
            }
        }

        if(chessGame.chessBoard[7][7] !=null){
            if(chessGame.chessBoard[7][7].pieceType().equals(PieceType.BLACK_ROOK) ||
                    chessGame.chessBoard[7][7].pieceType().equals(PieceType.WHITE_ROOK)){
                rightRook = chessGame.chessBoard[7][7];
            }
        }

        castling(isPieceMove, king, leftRook, pieceListMap, rightRook);
    }

    private void castling(Map<Piece, Boolean> isPieceMove, Piece king, Piece leftRook, Map<Piece, List<int[]>> pieceListMap, Piece rightRook) {

        if(king != null && isPieceMove!=null && !isPieceMove.get(king)){
            if(rightRook != null && !isPieceMove.get(rightRook)){
                if((!isWhiteTurn && chessGame.chessBoard[7][6] == null && chessGame.chessBoard[7][5] == null && chessGame.chessBoard[7][4] == null) ||
                        (isWhiteTurn && chessGame.chessBoard[7][5] == null && chessGame.chessBoard[7][6] == null)) {
                    addToMap(pieceListMap, king, new int[]{7, 6});
                }
            }
            if(leftRook != null  && !isPieceMove.get(leftRook)){
                if((!isWhiteTurn && chessGame.chessBoard[7][1] == null && chessGame.chessBoard[7][2] == null) ||
                        (isWhiteTurn && chessGame.chessBoard[7][1] == null && chessGame.chessBoard[7][2] == null && chessGame.chessBoard[7][3] == null)){
                    addToMap(pieceListMap, king, new int[]{7, 1});
                }
            }
        }
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

                int[] kingPosition = checkHandler.getKingPosition(isWhiteTurn, temp);
                int[][] attackedSquares = checkHandler.getAttackedSquares(isWhiteTurn, temp);

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

    private void addToMap(Map<Piece, List<int[]>> map, Piece piece, int[] value) {
        map.computeIfAbsent(piece, k -> new ArrayList<>()).add(value);
    }
}
