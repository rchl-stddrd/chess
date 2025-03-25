package chess;


import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
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
        board.resetBoard();
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
            for(int c=1; c<9; c++){
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
        ChessPiece piece = board.getPiece(startPosition);
        if (piece == null)
        {
            return Collections.emptyList();
        }
        TeamColor color = piece.getTeamColor();
        Collection<ChessMove> allMoves = piece.pieceMoves(board, startPosition);
        Collection<ChessMove> movesValid = new ArrayList<>();
//        System.out.println(allMoves);
        if (allMoves == null){
            return Collections.emptyList();
        }
        for (ChessMove move: allMoves){
            ChessGame game = copyGame(color);
            game.makeValidMove(move);
            if (!game.isInCheck(color)){
                movesValid.add(move);
            }
        }

        return movesValid;
//        throw new RuntimeException("Not implemented");
    }

    public void makeValidMove(ChessMove move){
        ChessPosition start =  move.getStartPosition();
        ChessPosition end = move.getEndPosition();

            if(move.getPromotionPiece() != null) {
                board.addPiece(end, new ChessPiece(teamColor,move.getPromotionPiece()));
                board.addPiece(start, null);
            }

        else {
            board.addPiece(end, board.getPiece(start));
            board.addPiece(start, null);
        }
        if (teamColor == TeamColor.BLACK) {
            setTeamTurn(TeamColor.WHITE);
        }
        else {
            setTeamTurn(TeamColor.BLACK);
        }
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
        if(board.getPiece(start) == null){
            throw new InvalidMoveException("Invalid Move: No Piece Here");
        }
        else if(!validMoves(start).contains(move)){
            throw new InvalidMoveException("Invalid Move: Not in Valid Moves List");
        }
        else if(board.getPiece(start).getTeamColor() != getTeamTurn()){
            throw new InvalidMoveException("Invalid Move: Wrong Team");
        }
        else {
            makeValidMove(move);
        }
    }

    /**
     * Determines if the given team is in check
     *
     * @param teamColor which team to check for check
     * @return True if the specified team is in check
     */
    public boolean isInCheck(TeamColor teamColor) {
        for (int r = 1; r < 9; r++) {
            for (int c = 1; c < 9; c++) {
                ChessPiece piece = board.getPiece(new ChessPosition(r, c));
                if(piece != null && piece.getTeamColor() != teamColor) {
                    Collection<ChessMove> moves = piece.pieceMoves(board, new ChessPosition(r, c));
                    if (moves != null) {
                        for (ChessMove m : moves) {
                            ChessPiece endPiece = board.getPiece(m.getEndPosition());
                            if ( endPiece != null && endPiece.getTeamColor() == teamColor && endPiece.getPieceType() == ChessPiece.PieceType.KING) {
                                return true;
                            }
                        }
                    }
                }
            }
        }
        return false;
    }

    public boolean checkSurroundings(TeamColor teamColor){
        for (int r = 1; r < 9; r++) {
            for (int c = 1; c < 9; c++) {
                ChessPosition pos = new ChessPosition(r,c);
                ChessPiece piece = board.getPiece(pos);
                if(piece != null && piece.getTeamColor() == teamColor) {
                    Collection<ChessMove> moves = validMoves(pos);
                    for(ChessMove move : moves) {
                        ChessGame game = copyGame(teamColor);
                        game.makeValidMove(move);
                        if (validMoves(pos) != null) {
                            return false;
                        }
                    }
                }
            }
        }
        return true;
    }
    /**
     * Determines if the given team is in checkmate
     *
     * @param teamColor which team to check for checkmate
     * @return True if the specified team is in checkmate
     */
    public boolean isInCheckmate(TeamColor teamColor) {
       if(isInCheck(teamColor)) {
            return checkSurroundings(teamColor);
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
            return checkSurroundings(teamColor);
        }
        return false;
    }

    /**
     * Sets this game's chessboard with a given board
     *
     * @param board the new board to use
     */
    public void setBoard(ChessBoard board) {
        this.board = board;
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
