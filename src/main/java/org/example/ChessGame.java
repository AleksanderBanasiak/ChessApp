package org.example;

import lombok.Getter;
import lombok.Setter;
import org.example.board.Board;
import org.example.pieces.Piece;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

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

    private int selectedRow = -1;
    private int selectedCol = -1;
    private boolean isWhiteTurn = true;

    public void game(){
        configureFrame();
        configureBoardPanel();
        createBoard();
        displayFrame();
    }

    private void displayFrame() {
        frame.setVisible(true);
        frame.pack();
    }

    private void configureBoardPanel() {
        jPanel.setLayout(new GridLayout(8, 8));
        jPanel.setPreferredSize(new Dimension(1000, 1000));
        frame.add(jPanel);
    }

    private void configureFrame() {
        frame.setSize(1000 ,1000);
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



        chessBoard[toRow][toCol] = chessBoard[fromRow][fromCol];
        chessBoard[fromRow][fromCol] = null;
        board[toRow][toCol].setIcon(board[fromRow][fromCol].getIcon());
        board[fromRow][fromCol].setIcon(null);

//        selectedRow = toRow;
//        selectedCol = toCol;


        resetBoardColors();
        rotateBoard();




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
}




