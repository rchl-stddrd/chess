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
        int r,c;
        r = position.row;
        c = position.col;
        //{1,2}
        if(r+1<8 && c+2<8) {
            if (board.getSquare(new ChessPosition(r + 1, c + 2)) != null) {
                if (notSameTeam(r + 1, c + 2)) {
                    moves.add(new ChessMove(position, new ChessPosition(r + 1, c + 2), null));
                }
            } else {
                moves.add(new ChessMove(position, new ChessPosition(r + 1, c + 2), null));
            }
        }
        //{1,-2}
        //c-2>=1
        if(r+1<8 && c-2>1) {
            if (board.getSquare(new ChessPosition(r + 1, c - 2)) != null) {
                if (notSameTeam(r + 1, c - 2)) {
                    moves.add(new ChessMove(position, new ChessPosition(r + 1, c - 2), null));
                }
            } else {
                moves.add(new ChessMove(position, new ChessPosition(r + 1, c - 2), null));
            }
        }
        //{2,1}
        if(r+2<8 && c+1<8) {
            if (board.getSquare(new ChessPosition(r + 2, c + 1)) != null) {
                if (notSameTeam(r + 2, c + 1)) {
                    moves.add(new ChessMove(position, new ChessPosition(r + 2, c + 1), null));
                }
            } else {
                moves.add(new ChessMove(position, new ChessPosition(r + 2, c + 1), null));
            }
        }
        //{2,-1}
        if(r+2<8 && c-1>1) {
            if (board.getSquare(new ChessPosition(r + 2, c - 1)) != null) {
                if (notSameTeam(r + 2, c - 1)) {
                    moves.add(new ChessMove(position, new ChessPosition(r + 2, c - 1), null));
                }
            } else {
                moves.add(new ChessMove(position, new ChessPosition(r + 2, c - 1), null));
            }
        }
        //{-1,2}
        if(r-1>1 && c+2<8) {
            if (board.getSquare(new ChessPosition(r - 1, c + 2)) != null) {
                if (notSameTeam(r - 1, c + 2)) {
                    moves.add(new ChessMove(position, new ChessPosition(r - 1, c + 2), null));
                }
            } else {
                moves.add(new ChessMove(position, new ChessPosition(r - 1, c + 2), null));
            }
        }
        //{-1,-2}
        if(r-1>1 && c-2>1) {
            if (board.getSquare(new ChessPosition(r - 1, c - 2)) != null) {
                if (notSameTeam(r - 1, c - 2)) {
                    moves.add(new ChessMove(position, new ChessPosition(r - 1, c - 2), null));
                }
            } else {
                moves.add(new ChessMove(position, new ChessPosition(r - 1, c - 2), null));
            }
        }
        //{-2,1}
        if(r-2>1 && c+1<8) {
            if (board.getSquare(new ChessPosition(r - 2, c + 1)) != null) {
                if (notSameTeam(r - 2, c + 1)) {
                    moves.add(new ChessMove(position, new ChessPosition(r - 2, c + 1), null));
                }
            } else {
                moves.add(new ChessMove(position, new ChessPosition(r - 2, c + 1), null));
            }
        }
        //{-2,-1}
        if(r-2>1 && c-1>1) {
            if (board.getSquare(new ChessPosition(r - 2, c - 1)) != null) {
                if (notSameTeam(r - 2, c - 1)) {
                    moves.add(new ChessMove(position, new ChessPosition(r - 2, c - 1), null));
                }
            } else {
                moves.add(new ChessMove(position, new ChessPosition(r - 2, c - 1), null));
            }
        }
    }
    public void pawnPromotions(ChessPosition start, ChessPosition end) {
        if (board.getSquare(new ChessPosition(start.row, start.col)).getTeamColor().equals(ChessGame.TeamColor.BLACK)) {
            for(ChessPiece.PieceType piece : ChessPiece.PieceType.values())
            {
                if(piece != ChessPiece.PieceType.KING && piece != ChessPiece.PieceType.PAWN) {
                    moves.add(new ChessMove(start, end, piece));
                }
            }
        } else {
            for (ChessPiece.PieceType piece : ChessPiece.PieceType.values()) {
                if(piece != ChessPiece.PieceType.KING && piece != ChessPiece.PieceType.PAWN) {
                    moves.add(new ChessMove(start, end, piece));
                }
            }
        }
    }
    public void  whitePawn(){
        int r = position.row;;
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


    public void blackPawn(){
        int r = position.row;;
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
        if (r>1 && c>1 &&board.getSquare(new ChessPosition(r -1, c -1)) != null && notSameTeam(r - 1, c - 1)) {
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
    public void PawnMovesCalculator(ChessBoard board, ChessPosition position)
    {
        if(board.getSquare(new ChessPosition(position.row, position.col)).getTeamColor().equals(ChessGame.TeamColor.BLACK)){
            blackPawn();
        }
        else{
            whitePawn();
        }
    }

}
