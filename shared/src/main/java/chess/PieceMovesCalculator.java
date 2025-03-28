package chess;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

public class PieceMovesCalculator {
    Collection<ChessMove> moves;
    ChessBoard board;
    ChessPosition position;

    public PieceMovesCalculator(ChessBoard board, ChessPosition myPosition) {
        moves = new ArrayList<>();
        this.board = board;
        this.position = myPosition;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof PieceMovesCalculator that)) {
            return false;
        }
        return Objects.equals(moves, that.moves) && Objects.equals(board, that.board) && Objects.equals(position, that.position);
    }

    @Override
    public int hashCode() {
        return Objects.hash(moves, board, position);
    }

    @Override
    public String toString() {
        return "PieceMovesCalculator{" +
                "moves=" + moves +
                ", board=" + board +
                ", position=" + position +
                '}';
    }

    public Collection<ChessMove> getMoves() {
        updateMoves();
        return moves;
    }

    public void updateMoves() {
        if (board.getPiece(position).getPieceType().equals(ChessPiece.PieceType.KING)) {
            kingMovesCalculator(board, position);
        } else if (board.getPiece(position).getPieceType().equals(ChessPiece.PieceType.QUEEN)) {
            queenMovesCalculator(board, position);
        } else if (board.getSquare(position).getPieceType().equals(ChessPiece.PieceType.ROOK)) {
            rookMovesCalculator(board, position);
        } else if (board.getSquare(position).getPieceType().equals(ChessPiece.PieceType.BISHOP)) {
            bishopMovesCalculator(board, position);
        } else if (board.getSquare(position).getPieceType().equals(ChessPiece.PieceType.KNIGHT)) {
            knightMovesCalculator(board, position);
        }
        //pawn (board.getPiece(position).getPieceType().equals(PieceType.PAWN))
        else {
            pawnMovesCalculator(board, position);

        }
    }

    public boolean inBounds(int r, int c) {
        return (r >= 1 && r <= 8 && c >= 1 && c <= 8);
    }

    public boolean notSameTeam(int r, int c) {
        ChessPosition currentPosition = new ChessPosition(position.row, position.col);
        ChessPosition newPosition = new ChessPosition(r, c);
        return !board.getSquare(newPosition).getTeamColor().equals(board.getSquare(currentPosition).getTeamColor());
    }

    public void oneDirection(ChessBoard board, ChessPosition position, int rowInc, int colInc) {
        int r = position.row + rowInc;
        int c = position.col + colInc;
        while (inBounds(r, c)) {
            //if(r==8 || c==8){break;}
//             r++;
            if (board.getSquare(new ChessPosition(r, c)) != null) {
                if (notSameTeam(r, c)) {
                    ChessMove m = new ChessMove(position, new ChessPosition(r, c), null);
                    moves.add(m);
                }
                break;
            }
            ChessMove m = new ChessMove(position, new ChessPosition(r, c), null);
            moves.add(m);
            r = r + rowInc;
            c = c + colInc;
        }
    }

    public void multipleDirections(int[][] increments, int r, int c){
        for (int[] i : increments) {
            int newR = r + i[0];
            int newC = c + i[1];
            if (inBounds(newR, newC)) {
                if (board.getSquare(new ChessPosition(newR, newC)) != null) {
                    if (notSameTeam(newR, newC)) {
                        moves.add(new ChessMove(position, new ChessPosition(newR, newC), null));
                    }
                } else {
                    moves.add(new ChessMove(position, new ChessPosition(newR, newC), null));
                }
            }

        }
    }
    public void kingMovesCalculator(ChessBoard board, ChessPosition position) {
        int[][] increments = {{1, 1}, {1, 0}, {1, -1}, {0, 1}, {-1, 1}, {-1, 0}, {-1, -1}, {0, -1}};
        int r, c;
        r = position.row;
        c = position.col;
        multipleDirections(increments, r, c);
    }

    public void queenMovesCalculator(ChessBoard board, ChessPosition position) {
        rookMovesCalculator(board, position);
        bishopMovesCalculator(board, position);
    }

    public void rookMovesCalculator(ChessBoard board, ChessPosition position) {
        //{+1,c}
        oneDirection(board, position, 1, 0);
        //{r,+1}
        oneDirection(board, position, 0, 1);
        //{-1. c}
        oneDirection(board, position, -1, 0);
        //r,-1}
        oneDirection(board, position, 0, -1);
    }

    public void bishopMovesCalculator(ChessBoard board, ChessPosition position) {
        // {+1,+1}
        oneDirection(board, position, 1, 1);
        //{-1,-1}
        oneDirection(board, position, -1, -1);
        //{+1,-1}
        oneDirection(board, position, 1, -1);
        //{-1,+1}
        oneDirection(board, position, -1, 1);
    }

    public void knightMovesCalculator(ChessBoard board, ChessPosition position) {
        int[][] increments = {{1, 2}, {1, -2}, {2, 1}, {2, -1}, {-1, 2}, {-1, -2}, {-2, 1}, {-2, -1}};
        int r, c;
        r = position.row;
        c = position.col;

        multipleDirections(increments, r, c);
    }

    public void pawnPromotions(ChessPosition start, ChessPosition end) {
            for (ChessPiece.PieceType piece : ChessPiece.PieceType.values()) {
                if (piece != ChessPiece.PieceType.KING && piece != ChessPiece.PieceType.PAWN) {
                    moves.add(new ChessMove(start, end, piece));
                }
            }
    }

    public void whitePawn() {
        int r = position.row;
        int c = position.col;
        //first move
        if(r == 2 && board.getSquare(new ChessPosition(r + 2, c)) == null && board.getSquare(new ChessPosition(r + 1, c)) == null)
        {
            moves.add(new ChessMove(position, new ChessPosition(r + 2, c), null));
        }
        //diagonal capture
        if (r<8 && c<8 && board.getSquare(new ChessPosition(r +1, c +1)) != null && notSameTeam(r + 1, c + 1)) {
            if (r+1 == 8) {
                pawnPromotions(position, new ChessPosition(r + 1, c + 1));
            }
            else {
                moves.add(new ChessMove(position, new ChessPosition(r + 1, c + 1), null));
            }
        }

        if (r<8 && c>1 &&board.getSquare(new ChessPosition(r +1, c -1)) != null && notSameTeam(r + 1, c - 1)) {
            if (r+1 == 8) {
                pawnPromotions(position, new ChessPosition(r + 1, c - 1));
            }
            else {
                moves.add(new ChessMove(position, new ChessPosition(r + 1, c - 1), null));
            }
        }

        //normal move
        if (board.getSquare(new ChessPosition(r + 1, c)) == null ) {
            if(r+1 == 8) {
                pawnPromotions(position, new ChessPosition(r + 1, c));
            }
            else if(r<7) {
                moves.add(new ChessMove(position, new ChessPosition(r + 1, c), null));

            }
        }

    }


    public void blackPawn() {
        int r = position.row;
        int c = position.col;
        //first move
        if(r == 7 && board.getSquare(new ChessPosition(r - 2, c)) == null && board.getSquare(new ChessPosition(r - 1, c)) == null)
        {
            moves.add(new ChessMove(position, new ChessPosition(r - 2, c), null));
        }
        //diagonal capture
        if (r>1 && c<8 && board.getSquare(new ChessPosition(r -1, c +1)) != null && notSameTeam(r - 1, c + 1)) {
            if (r-1 == 1) {
                pawnPromotions(position, new ChessPosition(r-1, c+1));
            }
            else {
                moves.add(new ChessMove(position, new ChessPosition(r - 1, c + 1), null));
            }
        }
        if (r>1 && c>1 && board.getSquare(new ChessPosition(r -1, c -1)) != null && notSameTeam(r - 1, c - 1)) {
            if (r-1 == 1) {
                pawnPromotions(position, new ChessPosition(r - 1, c-1));
            }
            else {
                moves.add(new ChessMove(position, new ChessPosition(r - 1, c - 1), null));
            }
        }
        //normal move
        if (board.getSquare(new ChessPosition(r - 1, c)) == null) {
            if(r-1 == 1) {
                pawnPromotions(position, new ChessPosition(r - 1, c));
            }
            else if(r>1) {
                moves.add(new ChessMove(position, new ChessPosition(r - 1, c), null));
            }
        }
    }
    public void pawnMovesCalculator(ChessBoard board, ChessPosition position)
    {
        if(board.getSquare(new ChessPosition(position.row, position.col)).getTeamColor().equals(ChessGame.TeamColor.BLACK)){
            blackPawn();
        }
        else{
            whitePawn();
        }
    }
}
