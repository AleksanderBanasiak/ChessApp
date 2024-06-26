package org.example.game;

import lombok.Getter;
import lombok.Setter;
import org.example.board.Board;
import org.example.pieces.piece.Piece;
import org.example.pieces.piece.PieceType;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.example.board.Board.printPiecesImages;

@Getter
@Setter
public class ChessGame {

    JFrame frame = new JFrame("Chess Game by Aleksander Banasiak");

    JPanel jPanel = new JPanel();

    JButton[][] board = new JButton[8][8];

    Board boardService = new Board();

    Piece[][] chessBoard = boardService.setupBoard();

    List<Integer> moves = new ArrayList<>();

    Color[][] originalColors = new Color[8][8];

    protected static int moveCount = 0;

    protected static Map<Piece, Boolean> isPieceMove = new HashMap<>();


    private int selectedRow = -1;
    private int selectedCol = -1;
    private boolean isWhiteTurn = true;

    public void game() {
        configureFrame();
        configureBoardPanel();
        createBoard();
        displayFrame();
        addPiecesToMap();
    }

    public void restartGame() {
        chessBoard = boardService.setupBoard();
        resetBoardColors();
        isWhiteTurn = true;
        updateBoardIcons();
        isPieceMove = new HashMap<>();
        addPiecesToMap();
    }


    private void displayFrame() {
        frame.setVisible(true);
        frame.pack();
    }

    private void configureBoardPanel() {
        jPanel.setLayout(new GridLayout(8, 8));
        jPanel.setPreferredSize(new Dimension(800, 800));
        frame.add(jPanel);
    }

    private void configureFrame() {
        frame.setSize(800 ,800);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
    }

    public void createBoard(){
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                JButton tile = new JButton();
                if(chessBoard[row][col] != null){
                    tile.setIcon(printPiecesImages(chessBoard[row][col].pieceType()));
                }
                board[row][col] = tile;
                jPanel.add(tile);
                Color originalColor = (col + row) % 2 == 0 ? new Color(235, 236, 208) : new Color(119, 149, 86);
                originalColors[row][col] = originalColor;
                tile.setBackground(originalColor);
                tile.setFocusable(false);
                tile.setPreferredSize(new Dimension(125, 125));
                TileMouseListener listener = new TileMouseListener(this, row, col);
                tile.addMouseListener(listener);
            }
        }
    }

    public void movePiece(int fromRow, int fromCol, int toRow, int toCol) {
        Piece piece = chessBoard[fromRow][fromCol];
        markPiece(piece);

        if(piece != null && isKing(piece) && kingMove(fromCol, toCol, -1)) {
            doCastling(fromRow, fromCol, toRow, toCol, piece, 7, -1);
        }
        else if(piece != null && isKing(piece) && kingMove(fromCol, toCol, 1)){
            doCastling(fromRow, fromCol, toRow, toCol, piece, 0, 1);
        }else {
            chosenPieceMove(fromRow, fromCol, toRow, toCol, chessBoard[fromRow][fromCol]);
        }

        resetBoardColors();
        rotateBoard();
    }

    private void doCastling(int fromRow, int fromCol, int toRow, int toCol, Piece piece, int where, int where2) {
        chosenPieceMove(fromRow, fromCol, toRow, toCol, piece);

        chosenPieceMove(7, where, toRow, toCol + where2, chessBoard[7][where]);
    }

    private void chosenPieceMove(int fromRow, int fromCol, int toRow, int toCol, Piece piece) {
        chessBoard[toRow][toCol] = piece;
        chessBoard[fromRow][fromCol] = null;

        board[toRow][toCol].setIcon(board[fromRow][fromCol].getIcon());
        board[fromRow][fromCol].setIcon(null);
    }

    private static boolean kingMove(int fromCol, int toCol, int when) {
        return (toCol == fromCol - (when *2)) ||  (toCol == fromCol - (when *3));
    }

    private static boolean isKing(Piece piece) {
        return piece.pieceType() == PieceType.BLACK_KING || piece.pieceType() == PieceType.WHITE_KING;
    }

    private static void markPiece(Piece piece) {
        if(isPieceMove.containsKey(piece)){
            isPieceMove.put(piece, true);
        }
    }

    public void resetBoardColors() {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                board[i][j].setBackground(originalColors[i][j]);
            }
        }
    }
    private void rotateBoard() {
        Piece[][] tempBoard = new Piece[8][8];
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                tempBoard[i][j] = chessBoard[7 - i][7 - j];
            }
        }
        chessBoard = tempBoard;
        updateBoardIcons();
    }
    private void updateBoardIcons() {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (chessBoard[i][j] != null) {
                    board[i][j].setIcon(printPiecesImages(chessBoard[i][j].pieceType()));
                } else {
                    board[i][j].setIcon(null);
                }
            }
        }
    }
    private void addPiecesToMap(){
            isPieceMove.put(chessBoard[0][0], false);
            isPieceMove.put(chessBoard[0][7], false);
            isPieceMove.put(chessBoard[0][4], false);
            isPieceMove.put(chessBoard[7][4], false);
            isPieceMove.put(chessBoard[7][0], false);
            isPieceMove.put(chessBoard[7][7], false);
    }


}




