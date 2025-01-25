package chess;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

public class PieceMovesCalculator {
    Collection<ChessMove> moves;
    ChessBoard board;
    ChessPosition position;

    public PieceMovesCalculator( ChessBoard board, ChessPosition myPosition){
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

    public Collection<ChessMove> getMoves(){
        updateMoves();
        return moves;
    }

    public void updateMoves()
    {
        if(board.getPiece(position).getPieceType().equals(ChessPiece.PieceType.KING))
        {
            KingMovesCalculator(board, position);
        }
        else if(board.getPiece(position).getPieceType().equals(ChessPiece.PieceType.QUEEN))
        {
            QueenMovesCalculator(board, position);
        }
        else if(board.getSquare(position).getPieceType().equals(ChessPiece.PieceType.ROOK))
        {
            RookMovesCalculator(board, position);
        }
        else if(board.getSquare(position).getPieceType().equals(ChessPiece.PieceType.BISHOP))
        {
            BishopMovesCalculator(board, position);
        }
        else if(board.getSquare(position).getPieceType().equals(ChessPiece.PieceType.KNIGHT))
        {
            KnightMovesCalculator(board, position);
        }
        //pawn (board.getPiece(position).getPieceType().equals(PieceType.PAWN))
        else
        {
            PawnMovesCalculator(board, position);

        }
    }
     public boolean notSameTeam(int r, int c) {
         return !board.getSquare(new ChessPosition(r, c)).getTeamColor().equals(board.getSquare(new ChessPosition(position.row, position.col)).getTeamColor());
     }

    public void KingMovesCalculator(ChessBoard board, ChessPosition position) {
        int r = position.row;
        int c = position.col;
        //{+1,+1}
        if (r + 1 < 8 && c + 1 < 8) {
            if (board.getSquare(new ChessPosition(r + 1, c + 1)) != null) {
                if (notSameTeam(r + 1, c + 1)) {
                    moves.add(new ChessMove(position, new ChessPosition(r + 1, c + 1), null));
                }
            }
            else {
                moves.add(new ChessMove(position, new ChessPosition(r + 1, c + 1), null));
            }
        }
        //{+1, c}
        if (r + 1 < 8) {
            if (board.getSquare(new ChessPosition(r + 1, c)) != null) {
                if (notSameTeam(r + 1, c)) {
                    moves.add(new ChessMove(position, new ChessPosition(r + 1, c), null));
                }
            } else  {
                moves.add(new ChessMove(position, new ChessPosition(r + 1, c), null));
            }
        }
        //{+1,-1}
        if (r + 1 < 8 && c > 1) {
            if (board.getSquare(new ChessPosition(r + 1, c - 1)) != null) {
                if (notSameTeam(r + 1, c - 1)) {
                    moves.add(new ChessMove(position, new ChessPosition(r + 1, c - 1), null));
                }
            } else  {
                moves.add(new ChessMove(position, new ChessPosition(r + 1, c - 1), null));
            }
        }
        //{r,+1}
        if (c + 1 < 8) {
            if (board.getSquare(new ChessPosition(r, c + 1)) != null) {
                if (notSameTeam(r, c + 1)) {
                    moves.add(new ChessMove(position, new ChessPosition(r, c + 1), null));
                }
            } else {
                moves.add(new ChessMove(position, new ChessPosition(r, c + 1), null));
            }
        }
        //{-1, +1}
        if (r > 1 && c + 1 < 8) {
            if (board.getSquare(new ChessPosition(r - 1, c + 1)) != null) {
                if (notSameTeam(r - 1, c + 1)) {
                    moves.add(new ChessMove(position, new ChessPosition(r - 1, c + 1), null));
                }
            } else {
                moves.add(new ChessMove(position, new ChessPosition(r - 1, c + 1), null));
            }
        }
        //{r, -1}
        if (c > 1) {
            if (board.getSquare(new ChessPosition(r, c - 1)) != null) {
                if (notSameTeam(r, c - 1)) {
                    moves.add(new ChessMove(position, new ChessPosition(r, c - 1), null));
                }
            } else {
                moves.add(new ChessMove(position, new ChessPosition(r, c - 1), null));
            }
        }
        //{-1, -1}
        if (c > 1 && r > 1) {
            if (board.getSquare(new ChessPosition(r - 1, c - 1)) != null) {
                if (notSameTeam(r - 1, c - 1)) {
                    moves.add(new ChessMove(position, new ChessPosition(r - 1, c - 1), null));
                }
            } else {
                moves.add(new ChessMove(position, new ChessPosition(r - 1, c - 1), null));
            }
        }
        //{-1, c}
        if(r>1) {
            if (board.getSquare(new ChessPosition(r - 1, c)) != null) {
                if (notSameTeam(r - 1, c)) {
                    moves.add(new ChessMove(position, new ChessPosition(r - 1, c), null));
                }
            } else {
                moves.add(new ChessMove(position, new ChessPosition(r - 1, c), null));
            }
        }
    }

    public void QueenMovesCalculator(ChessBoard board, ChessPosition position)
    {
        RookMovesCalculator(board, position);
        BishopMovesCalculator(board, position);
    }

    public void RookMovesCalculator(ChessBoard board, ChessPosition position)
    {
        int r,c;
        //{+1,c}
        r = position.row;
        c= position.col;
        while(r<8) {
            if(r==8 || c==8){break;}
            r++;
            if (board.getSquare(new ChessPosition(r, c)) != null) {
                if (notSameTeam(r, c)) {
                    ChessMove m = new ChessMove(position, new ChessPosition(r, c), null);
                    moves.add(m);
                }
                break;
            }

            ChessMove m = new ChessMove(position, new ChessPosition(r, c), null);
            moves.add(m);
            //r++;
        }
        //{r,+1}
        r = position.row;
        c = position.col;
        while(c<8) {
            if (c==8 || r==8){break;}
            c++;
            if (board.getSquare(new ChessPosition(r, c)) != null) {
                if (notSameTeam(r, c)) {
                    ChessMove m = new ChessMove(position, new ChessPosition(r, c), null);
                    moves.add(m);
                }
                break;
            }
            // c++;

            ChessMove m = new ChessMove(position, new ChessPosition(r, c), null);
            moves.add(m);
        }
        //{-1. c}
        r = position.row;
        c= position.col;
        while(r>1) {
            r--;
            if (board.getSquare(new ChessPosition(r, c)) != null) {
                if (notSameTeam(r, c)) {
                    ChessMove m = new ChessMove(position, new ChessPosition(r, c), null);
                    moves.add(m);
                }
                break;
            }
            ChessMove m = new ChessMove(position, new ChessPosition(r, c), null);
            moves.add(m);
        }
        //r,-1}
        r = position.row;
        c = position.col;
        while(c>1) {
            c--;
            if (board.getSquare(new ChessPosition(r, c)) != null) {
                if (notSameTeam(r, c)) {
                    ChessMove m = new ChessMove(position, new ChessPosition(r, c), null);
                    moves.add(m);
                }
                break;
            }
            ChessMove m = new ChessMove(position, new ChessPosition(r, c), null);
            moves.add(m);
        }
    }

    public void BishopMovesCalculator(ChessBoard board, ChessPosition position)
    {
        int r,c;
        // {+1,+1}
        r = position.row;
        c = position.col;
        while(r<8 && c<8)
        {
            r++;
            c++;
            if(board.getSquare(new ChessPosition(r,c)) != null) {
                if (notSameTeam(r, c)){
                    ChessMove m = new ChessMove(position, new ChessPosition(r, c), null);
                    moves.add(m);
                }
                break;
            }
            ChessMove m = new ChessMove(position, new ChessPosition(r, c), null);
            moves.add(m);
        }
        //{-1,-1}
        r = position.row;
        c = position.col;
        while(r>1 && c>1)
        {
            r--;
            c--;
            if(board.getSquare(new ChessPosition(r,c)) != null) {
                if (notSameTeam(r, c)){
                    ChessMove m = new ChessMove(position, new ChessPosition(r, c), null);
                    moves.add(m);
                }
                break;
            }
            ChessMove m = new ChessMove(position, new ChessPosition(r, c), null);
            moves.add(m);
        }

        //{+1,-1}
        r = position.row;
        c = position.col;
        while(r<8 && c>1)
        {
            r++;
            c--;
            if(board.getSquare(new ChessPosition(r,c)) != null)
            {
                if(notSameTeam(r, c)){
                    ChessMove m = new ChessMove(position, new ChessPosition(r, c), null);
                    moves.add(m);
                }
                break;
            }
            ChessMove m = new ChessMove(position, new ChessPosition(r,c), null);
            moves.add(m);
        }
        //{-1,+1}
        r = position.row;
        c = position.col;
        while(r>1 && c<8 ) {
            r--;
            c++;
            if (board.getSquare(new ChessPosition(r, c)) != null) {
                if (notSameTeam(r, c)) {
                    ChessMove m = new ChessMove(position, new ChessPosition(r, c), null);
                    moves.add(m);
                }
                break;
            }
            ChessMove m = new ChessMove(position, new ChessPosition(r, c), null);
            moves.add(m);
        }
    }

    public void KnightMovesCalculator(ChessBoard board, ChessPosition position)
    {
        throw new RuntimeException("Not implemented");
    }

    public void PawnMovesCalculator(ChessBoard board, ChessPosition position)
    {
        throw new RuntimeException("Not implemented");
    }

}
