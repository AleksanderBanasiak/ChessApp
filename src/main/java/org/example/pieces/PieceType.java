package org.example.pieces;

public enum PieceType {

    WHITE_PAWN(1),
    WHITE_KNIGHT(2),
    WHITE_BISHOP(3),
    WHITE_ROOK(4),
    WHITE_QUEEN(5),
    WHITE_KING(6),

    BLACK_PAWN(11),
    BLACK_KNIGHT(12),
    BLACK_BISHOP(13),
    BLACK_ROOK(14),
    BLACK_QUEEN(15),
    BLACK_KING(16),

    EMPTY(0);

    public final int value;

    PieceType(int value) {
        this.value = value;
    }
}
