package org.example.board;

import org.example.pieces.*;
import org.example.pieces.piece.Piece;
import org.example.pieces.piece.PieceType;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

public class Board {

    public Piece[][] setupBoard(){

        Piece[][] chessBoard = new Piece[8][8];

        for (int col = 0; col < 8; col++) {
            chessBoard[1][col] = new Pawn(false);
            chessBoard[6][col] = new Pawn(true);
        }
            chessBoard[0][0] = new Rook(false);
            chessBoard[0][1] = new Knight(false);
            chessBoard[0][2] = new Bishop(false);
            chessBoard[0][3] = new Queen(false);
            chessBoard[0][4] = new King(false);
            chessBoard[0][5] = new Bishop(false);
            chessBoard[0][6] = new Knight(false);
            chessBoard[0][7] = new Rook(false);

            chessBoard[7][0] = new Rook(true);
            chessBoard[7][1] = new Knight(true);
            chessBoard[7][2] = new Bishop(true);
            chessBoard[7][3] = new Queen(true);
            chessBoard[7][4] = new King(true);
            chessBoard[7][5] = new Bishop(true);
            chessBoard[7][6] = new Knight(true);
            chessBoard[7][7] = new Rook(true);

        return chessBoard;
    }

    public static ImageIcon printPiecesImages(PieceType type){

        String pieceTypeName = String.valueOf(type);

        if(pieceTypeName.isEmpty()){
            return null;
        }
        try {
            InputStream inputStream = Board.class.getResourceAsStream("/img/" + pieceTypeName + ".png");
            if (inputStream != null) {
                BufferedImage icon = ImageIO.read(inputStream);
                if (icon != null) {
                    return new ImageIcon(icon.getScaledInstance(80, 80, Image.SCALE_SMOOTH));
                }
            }
        }catch (IOException e) {
            System.out.println("The image could not be loaded: " + e);
        }
        return null;
    }
}
