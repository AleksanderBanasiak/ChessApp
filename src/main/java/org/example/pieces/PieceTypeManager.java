package org.example.pieces;

public class PieceTypeManager {

    public static PieceType getPieceType(int value) {
        for (PieceType pieceType : PieceType.values()) {
            if (pieceType.value == value) {
                return pieceType;
            }
        }
        return PieceType.EMPTY;
    }

    }
