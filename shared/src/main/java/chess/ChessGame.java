package chess;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

/**
 * For a class that can manage a chess game, making moves on a board
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessGame {
    ChessBoard board = new ChessBoard();
    TeamColor teamColor;
    public ChessGame() {
        setBoard(board);
        setTeamTurn(TeamColor.WHITE);
    }

    public ChessGame copyGame(TeamColor color){
        ChessGame game = new ChessGame();
        game.setBoard(copyOfBoard());
        game.setTeamTurn(color);
        return game;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof ChessGame chessGame)) {
            return false;
        }
        return Objects.equals(board, chessGame.board) && teamColor == chessGame.teamColor;
    }

    @Override
    public int hashCode() {
        return Objects.hash(board, teamColor);
    }

    @Override
    public String toString() {
        return "ChessGame{" +
                "board=" + board +
                ", teamColor=" + teamColor +
                '}';
    }

    public ChessBoard copyOfBoard(){
        ChessPosition pos;
        ChessBoard copy = new ChessBoard();
        for(int r = 1; r<9; r++){
            for(int c=1; r<9; r++){
                pos = new ChessPosition(r,c);
                copy.addPiece(pos,board.getPiece(pos));
            }
        }
        return copy;
    }
    /**
     * @return Which team's turn it is
     */
    public TeamColor getTeamTurn() {
        return teamColor;
    }

    /**
     * Set's which teams turn it is
     *
     * @param team the team whose turn it is
     */
    public void setTeamTurn(TeamColor team) {
        teamColor = team;
    }

    /**
     * Enum identifying the 2 possible teams in a chess game
     */
    public enum TeamColor {
        WHITE,
        BLACK
    }

    /**
     * Gets a valid moves for a piece at the given location
     *
     * @param startPosition the piece to get valid moves for
     * @return Set of valid moves for requested piece, or null if no piece at
     * startPosition
     */
    public Collection<ChessMove> validMoves(ChessPosition startPosition) {
//        ChessPiece piece = board.getPiece(startPosition);
//        Collection<ChessMove> allMoves = ChessPiece.pieceMoves(board, startPosition);
//        Collection<ChessMove> movesValid = new ArrayList<>();
//
//        if (board.getPiece(startPosition) == null)
//        {
//            return null;
//        }
//        else if(board.getPiece(startPosition).getPieceType() == ChessPiece.PieceType.KING)
//        {
//            for (ChessMove move: allMoves){
//                ChessGame game = copyGame(teamColor);
//                game.makeMove(move);
//                if (!isInCheck(teamColor)){
//                    movesValid.add(move);
//                }
//            }
//        }
//        else{
//            movesValid = allMoves;
//        }
//        return movesValid;
        throw new RuntimeException("Not implemented");
    }

    /**
     * Makes a move in a chess game
     *
     * @param move chess move to preform
     * @throws InvalidMoveException if move is invalid
     */
    public void makeMove(ChessMove move) throws InvalidMoveException {
        ChessPosition start =  move.getStartPosition();
        ChessPosition end = move.getEndPosition();
//        try
        board.addPiece(end,board.getPiece(start));
        board.addPiece(start, null);
        if(teamColor == TeamColor.BLACK)
            setTeamTurn(TeamColor.WHITE);
        else
            setTeamTurn(TeamColor.BLACK);
    }

    /**
     * Determines if the given team is in check
     *
     * @param teamColor which team to check for check
     * @return True if the specified team is in check
     */
    public boolean isInCheck(TeamColor teamColor) {
        for (int r = 1; r < 9; r++) {
            for (int c = 1; r < 9; r++) {
                for (ChessMove m : ChessPiece.pieceMoves(board, new ChessPosition(r,c)) ) {
                    if(board.getPiece(m.getEndPosition()).getPieceType() == ChessPiece.PieceType.KING){
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * Determines if the given team is in checkmate
     *
     * @param teamColor which team to check for checkmate
     * @return True if the specified team is in checkmate
     */
    public boolean isInCheckmate(TeamColor teamColor) {
       if(isInCheck(teamColor)) {
            for (int r = 1; r < 9; r++) {
                for (int c = 1; r < 9; r++) {
                    if (validMoves(new ChessPosition(r, c)) != null) {
                        return false;
                    }
                }
            }
            return true;
       }
       return false;
    }

    /**
     * Determines if the given team is in stalemate, which here is defined as having
     * no valid moves
     *
     * @param teamColor which team to check for stalemate
     * @return True if the specified team is in stalemate, otherwise false
     */
    public boolean isInStalemate(TeamColor teamColor) {
        if(!isInCheck(teamColor)) {
            for (int r = 1; r < 9; r++) {
                for (int c = 1; r < 9; r++) {
                    if (validMoves(new ChessPosition(r, c)) != null) {
                        return false;
                    }
                }
            }
            return true;
        }
        return false;
    }

    /**
     * Sets this game's chessboard with a given board
     *
     * @param board the new board to use
     */
    public void setBoard(ChessBoard board) {
        board.resetBoard();
    }

    /**
     * Gets the current chessboard
     *
     * @return the chessboard
     */
    public ChessBoard getBoard() {
        return board;
    }
}
