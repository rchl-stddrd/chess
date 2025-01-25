package chess;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

/**
 * Represents a single chess piece
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessPiece {

    private ChessGame.TeamColor color;
    private ChessPiece.PieceType type;

    public ChessPiece(ChessGame.TeamColor pieceColor, ChessPiece.PieceType type) {
        color = pieceColor;
        this.type = type;

    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof ChessPiece that)) {
            return false;
        }
        return color == that.color && type == that.type;
    }

    @Override
    public int hashCode() {
        return Objects.hash(color, type);
    }

    @Override
    public String toString() {
        return "ChessPiece{" +
                "color=" + color +
                ", type=" + type +
                '}';
    }

    /**
     * The various different chess piece options
     */
    public enum PieceType {
        KING,
        QUEEN,
        BISHOP,
        KNIGHT,
        ROOK,
        PAWN
    }

    /**
     * @return Which team this chess piece belongs to
     */
    public ChessGame.TeamColor getTeamColor() {

       return color;
    }

    /**
     * @return which type of chess piece this piece is
     */
    public PieceType getPieceType() {
        return type;
    }

    public Collection<ChessMove> PieceMovesCalculator(ChessBoard board, ChessPosition position)
    {
        if(board.getPiece(position).getPieceType().equals(ChessPiece.PieceType.KING))
        {
            return KingMovesCalculator(board, position);
        }
        else if(board.getPiece(position).getPieceType().equals(PieceType.QUEEN))
        {
            return QueenMovesCalculator(board, position);
        }
        else if(board.getSquare(position).getPieceType().equals(PieceType.ROOK))
        {
            return RookMovesCalculator(board, position);
        }
        else if(board.getSquare(position).getPieceType().equals(PieceType.BISHOP))
        {
            return BishopMovesCalculator(board, position);
        }
        else if(board.getSquare(position).getPieceType().equals(PieceType.KNIGHT))
        {
            return KnightMovesCalculator(board, position);
        }
        //pawn (board.getPiece(position).getPieceType().equals(PieceType.PAWN))
        else
        {
            return PawnMovesCalculator(board, position);

        }
    }

    public Collection<ChessMove> KingMovesCalculator(ChessBoard board, ChessPosition position)
    {
        throw new RuntimeException("Not implemented");
    }

    public Collection<ChessMove> QueenMovesCalculator(ChessBoard board, ChessPosition position)
    {
        throw new RuntimeException("Not implemented");
    }

    public Collection<ChessMove> RookMovesCalculator(ChessBoard board, ChessPosition position)
    {
        Collection<ChessMove> moves = new ArrayList<>();
        int r,c;
        //{+1,c}
        r = position.row;
        c= position.col;
        while(r<8) {
            if(r==8 || c==8){break;}
            r++;
            if (board.getSquare(new ChessPosition(r, c)) != null) {
                if (!board.getSquare(new ChessPosition(r, c)).getTeamColor().equals(board.getSquare(new ChessPosition(position.row, position.col)).getTeamColor())) {
                    ChessMove m = new ChessMove(position, new ChessPosition(r, c), null);
                    moves.add(m);
                }
                break;
            }

            ChessMove m = new ChessMove(position, new ChessPosition(r, c), null);
            moves.add(m);
            //r++;
        }
//        if (!board.getSquare(new ChessPosition(8, c)).getTeamColor().equals(board.getSquare(new ChessPosition(position.row, position.col)).getTeamColor())) {
//            moves.add(new ChessMove(position, new ChessPosition(8, c), null));
//        }
        //{r,+1}
        r = position.row;
        c = position.col;
        while(c<8) {
            if (c==8 || r==8){break;}
            c++;
            if (board.getSquare(new ChessPosition(r, c)) != null) {
                if (!board.getSquare(new ChessPosition(r, c)).getTeamColor().equals(board.getSquare(new ChessPosition(position.row, position.col)).getTeamColor())) {
                    ChessMove m = new ChessMove(position, new ChessPosition(r, c), null);
                    moves.add(m);
                }
                break;
            }
           // c++;

            ChessMove m = new ChessMove(position, new ChessPosition(r, c), null);
            moves.add(m);
        }
//        if (!board.getSquare(new ChessPosition(r, 8)).getTeamColor().equals(board.getSquare(new ChessPosition(position.row, position.col)).getTeamColor())) {
//            moves.add(new ChessMove(position, new ChessPosition(r, 8), null));
//        }
        //{-1. c}
        r = position.row;
        c= position.col;
        while(r>1) {
            r--;
            if (board.getSquare(new ChessPosition(r, c)) != null) {
                if (!board.getSquare(new ChessPosition(r, c)).getTeamColor().equals(board.getSquare(new ChessPosition(position.row, position.col)).getTeamColor())) {
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
                if (!board.getSquare(new ChessPosition(r, c)).getTeamColor().equals(board.getSquare(new ChessPosition(position.row, position.col)).getTeamColor())) {
                    ChessMove m = new ChessMove(position, new ChessPosition(r, c), null);
                    moves.add(m);
                }
                break;
            }
            ChessMove m = new ChessMove(position, new ChessPosition(r, c), null);
            moves.add(m);
        }
        return moves;
    }

    public Collection<ChessMove> BishopMovesCalculator(ChessBoard board, ChessPosition position)
    {
        Collection<ChessMove> moves = new ArrayList<>();
        int r,c;
        // {+1,+1}
        r = position.row;
        c = position.col;
        while(r<8 && c<8)
        {
            r++;
            c++;
            if(board.getSquare(new ChessPosition(r,c)) != null) {
                if (!board.getSquare(new ChessPosition(r, c)).getTeamColor().equals(board.getSquare(position).getTeamColor())){
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
                if (!board.getSquare(new ChessPosition(r, c)).getTeamColor().equals(board.getSquare(position).getTeamColor())){
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
                if(!board.getSquare(new ChessPosition(r,c)).getTeamColor().equals(board.getSquare(position).getTeamColor())){
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
        while(r>1 && c<8 )
        {
            r--;
            c++;
            if(board.getSquare(new ChessPosition(r,c)) != null ) {
                if(!board.getSquare(new ChessPosition(r,c)).getTeamColor().equals(board.getSquare(position).getTeamColor())) {
                    ChessMove m = new ChessMove(position, new ChessPosition(r, c), null);
                    moves.add(m);
                }
                break;
            }
            ChessMove m = new ChessMove(position, new ChessPosition(r, c), null);
            moves.add(m);
        }
        return moves;
    }

    public Collection<ChessMove> KnightMovesCalculator(ChessBoard board, ChessPosition position)
    {
        throw new RuntimeException("Not implemented");
    }

    public Collection<ChessMove> PawnMovesCalculator(ChessBoard board, ChessPosition position)
    {
        throw new RuntimeException("Not implemented");
    }
    /**
     * Calculates all the positions a chess piece can move to
     * Does not take into account moves that are illegal due to leaving the king in
     * danger
     *
     * @return Collection of valid moves
     */
    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {

        return PieceMovesCalculator(board, myPosition);
    }

}