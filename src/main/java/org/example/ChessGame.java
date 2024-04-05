package org.example;

import org.example.board.Board;
import org.example.pieces.PieceType;

import javax.swing.*;
import java.awt.*;

import static org.example.pieces.PieceTypeManager.getPieceType;

public class ChessGame {


    JFrame frame = new JFrame("Chess Game by Aleksander Banasiak");

    JPanel jPanel = new JPanel();

    JButton[][] board = new JButton[8][8];

    Board boardService = new Board();

    int[][] chessBoard = boardService.setupBoard();





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

    private void configureTile(){

    }

    public void createBoard(){

        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                JButton tile = new JButton();

                board[row][col] = tile;
                jPanel.add(tile);

                Color color = (col+row) % 2 ==0 ? new Color(235, 236, 208) : new Color(119, 149, 86);

                tile.setBackground(color);
                tile.setFocusable(false);
                tile.setPreferredSize(new Dimension(125, 125));

                int currentRow = row;
                int currentCol = col;


                tile.addActionListener(e -> {
                    int pieceVal =  chessBoard[currentRow][currentCol];

                    PieceType pieceType = getPieceType(pieceVal);

                    // i tutaj klasa show possible moves

                    // i chyba najlepiej ze pobiera daną klase figury przez enuma (ale jako interfejs piece)

                    // i w interfejsie są metody z pokazywaniem tych możliwych ruchów 

                    System.out.println(pieceType);

                    System.out.println("Wybrany przycisk znajduje się na pozycji: [" + ( currentRow +1) + "," + (currentCol+1) + "]");
                });
            }
        }
    }






}
