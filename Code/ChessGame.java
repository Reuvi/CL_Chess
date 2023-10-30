//Only External Java Libraries Used
import java.util.Scanner;
import java.lang.Math;

//Class Creations
public class ChessGame {
    private static final int BOARD_SIZE = 8;// We Use Final to indicate not to change
    private static final char EMPTY_SQUARE = '.';
    private static final char[] BLACK_PIECES = {'P', 'R', 'N', 'B', 'Q', 'K'};
    private static final char[] WHITE_PIECES = {'p', 'r', 'n', 'b', 'q', 'k'};
    private static boolean[] NONMOVEABLE = {false, false, false, false, false, false};
    //[0]-WhiteKING [1]-WLeftRook [2]-WRightRook [3]-BlackKING [4]-BLeftRook [5]-BRightRook
    private static int enPassantTargetSquare = -1;
    private static boolean enPassant = false;
    private char[][] board;
    private boolean whiteTurn;
    
    //Constructor to make the game
    public ChessGame() {
        board = new char[BOARD_SIZE][BOARD_SIZE];
        whiteTurn = true;
        initializeBoard();
    }
    
    //First Set the Entire Array to Empty Squares, Then Fill in the important Pieces.
    private void initializeBoard() {
        // Initialize empty squares
        for (int row = 0; row < BOARD_SIZE; row++) {
            for (int col = 0; col < BOARD_SIZE; col++) {
                board[row][col] = EMPTY_SQUARE;
            }
        }

        // Initialize black pieces
        board[0][0] = 'R';
        board[0][1] = 'N';
        board[0][2] = 'B';
        board[0][3] = 'Q';
        board[0][4] = 'K';
        board[0][5] = 'B';
        board[0][6] = 'N';
        board[0][7] = 'R';
        for (int col = 0; col < BOARD_SIZE; col++) {
            board[1][col] = 'P';
        }

        // Initialize white pieces
        board[7][0] = 'r';
        board[7][1] = 'n';
        board[7][2] = 'b';
        board[7][3] = 'q';
        board[7][4] = 'k';
        board[7][5] = 'b';
        board[7][6] = 'n';
        board[7][7] = 'r';
        for (int col = 0; col < BOARD_SIZE; col++) {
            board[6][col] = 'p';
        }
    }
    
    //Render the Board to the Screen
    private void printBoard() {
        for(int i = 0; i<= 10; i++){
            System.out.println();
        }
        System.out.println();
        System.out.println("     a  b  c  d  e  f  g  h");
        System.out.println("  |--------------------------|");
        System.out.println("  |           BLACK          |");
        for (int row = 0; row < BOARD_SIZE; row++) {
            System.out.print((8 - row) + " |  ");
            for (int col = 0; col < BOARD_SIZE; col++) {
                System.out.print(board[row][col] + "  ");
            }
            System.out.println("| " + (8 - row));
            if(row == 7){
                System.out.println("  |           WHITE          |");
            }
            else {
                System.out.println("  |                          |");
            }    
        }
        System.out.println("  |--------------------------|");
        System.out.println("     a  b  c  d  e  f  g  h");
        System.out.println();
        for(int i = 0; i<= 1; i++){
            System.out.println();
        }
    }
    
    private String formatMove(int srcCol, int srcRow, int destCol, int destRow) {
        char srcColChar = '.';
        char destColChar = '.';
        
        switch(srcCol){
            case 0:
                srcColChar = 'a';
                break;
            case 1:
                srcColChar = 'b';
                break;
            case 2:
                srcColChar = 'c';
                break;
            case 3:
                srcColChar = 'd';
                break;
            case 4:
                srcColChar = 'e';
                break;
            case 5:
                srcColChar = 'f';
                break;
            case 6:
                srcColChar = 'g';
                break;
            case 7:
                srcColChar = 'h';
                break;
        }
        switch(srcCol){
            case 0:
                destColChar = 'a';
                break;
            case 1:
                destColChar = 'b';
                break;
            case 2:
                destColChar = 'c';
                break;
            case 3:
                destColChar = 'd';
                break;
            case 4:
                destColChar = 'e';
                break;
            case 5:
                destColChar = 'f';
                break;
            case 6:
                destColChar = 'g';
                break;
            case 7:
                destColChar = 'h';
                break;
        }
        char srcRowChar = (char) ('8' - srcRow);
        char destRowChar = (char) ('8' - destRow);
        return String.valueOf(srcColChar) + String.valueOf(srcRowChar) + String.valueOf(destColChar) + String.valueOf(destRowChar);
    }
    
    private boolean isValidMove(String move) {
    
    // Right Castle Checks
        if(move.equals("Castle-Right")){
            if(whiteTurn){
                if(board[7][5] == EMPTY_SQUARE && board[7][6] == EMPTY_SQUARE && !NONMOVEABLE[0] && !NONMOVEABLE[2]){
                    board[7][4] = EMPTY_SQUARE;
                    board[7][5] = 'r';
                    board[7][6] = 'k';
                    board [7][7] = EMPTY_SQUARE;
                    boolean illegal = isCheck(true);
                    board[7][4] = 'k';
                    board[7][5] = EMPTY_SQUARE;
                    board[7][6] = EMPTY_SQUARE;
                    board [7][7] = 'r';
                    boolean billegal = isCheck(true);
                    if (!illegal && !billegal) {
                        return true;
                    }
                    else {
                        return false;
                    }
                }
            }
            else {
                if(board[0][5] == EMPTY_SQUARE && board[0][6] == EMPTY_SQUARE && !NONMOVEABLE[3] && !NONMOVEABLE[5]){
                    board[0][4] = EMPTY_SQUARE;
                    board[0][5] = 'R';
                    board[0][6] = 'K';
                    board [0][7] = EMPTY_SQUARE;
                    boolean illegal = isCheck(true);
                    board[0][4] = 'K';
                    board[0][5] = EMPTY_SQUARE;
                    board[0][6] = EMPTY_SQUARE;
                    board [0][7] = 'R';
                    boolean billegal = isCheck(true);
                    if (!illegal && !billegal) {
                        return true;
                    }
                    else {
                        return false;
                    }
                }
            }
        }
        
        //Left Castle Checks
        if(move.equals("Castle-Left")){
            if(whiteTurn){
                if(board[7][1] == EMPTY_SQUARE && board[7][2] == EMPTY_SQUARE && board [7][3] == EMPTY_SQUARE && !NONMOVEABLE[0] && !NONMOVEABLE[1]){
                    board[7][0] = EMPTY_SQUARE;
                    board[7][1] = EMPTY_SQUARE;
                    board[7][2] = 'k';
                    board [7][3] = 'r';
                    board [7][4] = EMPTY_SQUARE;
                    boolean illegal = isCheck(true);
                    board[7][0] = 'r';
                    board[7][1] = EMPTY_SQUARE;
                    board[7][2] = EMPTY_SQUARE;
                    board [7][3] = EMPTY_SQUARE;
                    board [7][4] = 'k';
                    boolean billegal = isCheck(true);
                    if (!illegal && !billegal) {
                        return true;
                    }
                    else {
                        return false;
                    }
                }
            }
            else {
               if(board[0][1] == EMPTY_SQUARE && board[0][2] == EMPTY_SQUARE && board [0][3] == EMPTY_SQUARE && !NONMOVEABLE[3] && !NONMOVEABLE[4]){
                    board[0][0] = EMPTY_SQUARE;
                    board[0][1] = EMPTY_SQUARE;
                    board[0][2] = 'k';
                    board [0][3] = 'r';
                    board [0][4] = EMPTY_SQUARE;
                    boolean illegal = isCheck(true);
                    board[0][0] = 'r';
                    board[0][1] = EMPTY_SQUARE;
                    board[0][2] = EMPTY_SQUARE;
                    board [0][3] = EMPTY_SQUARE;
                    board [0][4] = 'k';
                    boolean billegal = isCheck(true);
                    if (!illegal && billegal) {
                        return true;
                    }
                    else {
                        return false;
                    }
                }
            }
        }
            
    //Gotta make sure if not castle that its properly formated!
        if (move.length() != 4) {
            return false;
        }
    
    // We know a - a is 0, so this code just formats the INPUT to be array readable
    // Lets say you're moving from c(2) to d(3). We need to get these chars to numbers so if we use a as a base of 0, and subtract it from each input we get a readable number 0-7 that would work.
        int srcCol = move.charAt(0) - 'a';
        int srcRow = '8' - move.charAt(1);
        int destCol = move.charAt(2) - 'a';
        int destRow = '8' - move.charAt(3);
    
    //Check that we selected an actual piece
        char piece = board[srcRow][srcCol];
        if (piece == EMPTY_SQUARE || isPieceWhite(piece) != whiteTurn) {
            return false;  // No piece to move or wrong turn
        }

    // Check if move is within board boundaries
        if (srcCol < 0 || srcCol >= BOARD_SIZE || srcRow < 0 || srcRow >= BOARD_SIZE || destCol < 0 || destCol >= BOARD_SIZE || destRow < 0 || destRow >= BOARD_SIZE) {
            return false;
        }

    // Check if the destination square is occupied by a friendly piece
        char destPiece = board[destRow][destCol];
        if (isPieceWhite(piece) == isPieceWhite(destPiece) && destPiece != EMPTY_SQUARE) {
            return false;
        }
    
    // Make a temporary move for legal calculations first save original piece
        char capturedPiece = makeMove(move, false, null, true);
    
    // Check if the player's own king is in check after the move
        boolean CHlegal = isCheck(isPieceWhite(piece));
        
    // Reset the board after the check    
        undoMove(move, capturedPiece);
    
    // Check if the move is valid for the piece the is moving
        boolean MVlegal = isValidSpecificMove(piece, srcRow, srcCol, destRow, destCol);

    
        if (CHlegal) {
        // Move Creates Discovered Check Not Allowed
            return false;
        } 
        else {
        // The Move is legal for the piece or is not
            return MVlegal;
        }
    }

    private boolean isValidSpecificMove(char piece, int srcRow, int srcCol, int destRow, int destCol) {
    // Validate specific piece moves first generalize the piece as we already checked color checks
        switch (Character.toLowerCase(piece)) {
            case 'r':
                return isRookMoveValid(srcRow, srcCol, destRow, destCol);
            case 'n':
                return isKnightMoveValid(srcRow, srcCol, destRow, destCol);
            case 'b':
                return isBishopMoveValid(srcRow, srcCol, destRow, destCol);
            case 'q':
                return isQueenMoveValid(srcRow, srcCol, destRow, destCol);
            case 'k':
                return isKingMoveValid(srcRow, srcCol, destRow, destCol);
            case 'p':
                return isPawnMoveValid(srcRow, srcCol, destRow, destCol);
            default:
                return false;
        }
    }


    // Are their any pieces in the way of the moving piece?
    private boolean isPathObstructed(int srcRow, int srcCol, int destRow, int destCol) {
        int rowDiff = destRow - srcRow;
        int colDiff = destCol - srcCol;
    
    //If they are not moving on the dimensions step should be 0, else it should be the sign of the difference
        int rowStep = (rowDiff == 0) ? 0 : rowDiff / Math.abs(rowDiff);
        int colStep = (colDiff == 0) ? 0 : colDiff / Math.abs(colDiff);
    
    //Initialize beggining states for our intermediary values
        int row = srcRow + rowStep;
        int col = srcCol + colStep;
    
    //Check all of the intermediary values for pieces
        while (row != destRow || col != destCol) {
            if (board[row][col] != EMPTY_SQUARE) {
                return true;  // Path is obstructed
            }
            row += rowStep;
            col += colStep;
        }

        return false;  // Path is clear
    }

    //CheckRook
    private boolean isRookMoveValid(int srcRow, int srcCol, int destRow, int destCol) {
        
        // Check for obstruction
        if (srcRow == destRow && isPathObstructed(srcRow, srcCol, destRow, destCol)) {
            return false;  // Path is obstructed along the row it travels
        }
        if (srcCol == destCol && isPathObstructed(srcRow, srcCol, destRow, destCol)) {
            return false;  // Path is obstructed along the column it travels
        }
        return (srcRow == destRow && srcCol != destCol) || (srcRow != destRow && srcCol == destCol);
    }

    //CheckKnight
    private boolean isKnightMoveValid(int srcRow, int srcCol, int destRow, int destCol) {
        int rowDiff = Math.abs(destRow - srcRow);
        int colDiff = Math.abs(destCol - srcCol);
        return (rowDiff == 2 && colDiff == 1) || (rowDiff == 1 && colDiff == 2);
    }
    
    //CheckBishop
    private boolean isBishopMoveValid(int srcRow, int srcCol, int destRow, int destCol) {
        int rowDiff = Math.abs(destRow - srcRow);
        int colDiff = Math.abs(destCol - srcCol);
        
        // Check for obstruction
        if (Math.abs(rowDiff) == Math.abs(colDiff) && isPathObstructed(srcRow, srcCol, destRow, destCol)) {
            return false;
        }  // Path is obstructed along the vertical axis
        
        return rowDiff == colDiff;
    }

    //CheckQueen
    private boolean isQueenMoveValid(int srcRow, int srcCol, int destRow, int destCol) {
        int rowDiff = Math.abs(srcRow - destRow);
        int colDiff = Math.abs(srcCol - destCol);
    
    // Check for obstruction
        if ((srcRow == destRow || srcCol == destCol) && isPathObstructed(srcRow, srcCol, destRow, destCol)) {
            return false;  // Path is obstructed
        }
        if (rowDiff == colDiff && isPathObstructed(srcRow, srcCol, destRow, destCol)) {
            return false;  // Path is obstructed
        }
    // Since queen = Bishop + Rook both options are valid
        return isRookMoveValid(srcRow, srcCol, destRow, destCol) || isBishopMoveValid(srcRow, srcCol, destRow, destCol);
    }
    
    //CheckKing
    private boolean isKingMoveValid(int srcRow, int srcCol, int destRow, int destCol) {
        int rowDiff = Math.abs(destRow - srcRow);
        int colDiff = Math.abs(destCol - srcCol);
        return rowDiff <= 1 && colDiff <= 1;
    }
    
    //CheckPawn
    private boolean isPawnMoveValid(int srcRow, int srcCol, int destRow, int destCol) {
        char piece = board[srcRow][srcCol];
        char destPiece = board[destRow][destCol];
    
        // Check if moving forward
        int direction = (isPieceWhite(piece)) ? -1 : 1;
        if (destCol == srcCol && destRow == srcRow + direction && destPiece == EMPTY_SQUARE) {
            return true;  // Move forward one square
        }
    
        // Check if first move of pawn and moving forward two squares
        if (srcRow == (isPieceWhite(piece) ? BOARD_SIZE - 2 : 1) && destCol == srcCol && destRow == srcRow + 2 * direction && destPiece == EMPTY_SQUARE) {
            // Check if the path is clear
            int intermediateRow = srcRow + direction;
            if (board[intermediateRow][srcCol] == EMPTY_SQUARE && board[destRow][destCol] == EMPTY_SQUARE) {
                return true;  // Move forward two squares
            }
        }
    
        // Check if capturing diagonally
        if (Math.abs(destCol - srcCol) == 1 && destRow == srcRow + direction && ((destPiece != EMPTY_SQUARE && isPieceWhite(piece) != isPieceWhite(destPiece)))) {
            return true;  // Capture diagonally
        }
        
        //Check for Enpassant
        if (Math.abs(destCol - srcCol) == 1 && destRow == srcRow + direction) {
            if((destPiece == EMPTY_SQUARE &&  ((destRow * 10) + destCol) == enPassantTargetSquare)) {
                enPassant = true;
                return true; // Capture Passant
            }
        }
        return false;
    }

//CheckPieceColor
    private boolean isPieceWhite(char piece) {
        for (char whitePiece : WHITE_PIECES) {
            if (piece == whitePiece) {
                return true;
            }
        }
        return false;
    }
    
//Check-The-Checkmate
    private boolean isCheckmate(boolean isWhite) {
        if (!isCheck(isWhite)) {
            return false;
        }
        // Check if any move can take the player out of check
        for (int srcRow = 0; srcRow < BOARD_SIZE; srcRow++) {
            for (int srcCol = 0; srcCol < BOARD_SIZE; srcCol++) {
                char piece = board[srcRow][srcCol];
                if (isPieceWhite(piece) == isWhite) {
                    for (int destRow = 0; destRow < BOARD_SIZE; destRow++) {
                        for (int destCol = 0; destCol < BOARD_SIZE; destCol++) {
                            String move = formatMove(srcCol, srcRow, destCol, destRow);
                            //if (piece == 'p'){
                            //System.out.println(move);}
                            if (!isMoveLeadsToCheck(move)) {
                                return false; // At least one move can save the player from checkmate
                            }
                        }
                    }
                }
            }
        }
        return true; // No move can save the player from checkmate
    }
    
    //Helper method for checkmate bigboi
    private boolean isMoveLeadsToCheck(String move) {
        if (isValidMove(move)) {
            return false;
        }
        else {
            return true;
        }
    }
    
    //Has two use cases, one use case is proxy moves to check if a move is legal, and the other use case is a real move.
    private char makeMove(String move, boolean posPromo, Scanner scanner, boolean proxy) {
        
        //Making A Right Castle Move
        if(move.equals("Castle-Right")){
            if(whiteTurn) {
                board[7][4] = EMPTY_SQUARE;
                board[7][5] = 'r';
                board[7][6] = 'k';
                board [7][7] = EMPTY_SQUARE;
                whiteTurn = !whiteTurn;
                return '.';
            }
            else{
                board[0][4] = EMPTY_SQUARE;
                board[0][5] = 'R';
                board[0][6] = 'K';
                board [0][7] = EMPTY_SQUARE;
                whiteTurn = !whiteTurn;
                return '.';
            }
        }
        
        //Making A Left Castle Move
        if(move.equals("Castle-Left")){
            if(whiteTurn) {
                board[7][0] = EMPTY_SQUARE;
                board[7][1] = EMPTY_SQUARE;
                board[7][2] = 'k';
                board[7][3] = 'r';
                board[7][4] = EMPTY_SQUARE;
                whiteTurn = !whiteTurn;
                return '.';
            }
            else{
                board[0][0] = EMPTY_SQUARE;
                board[0][1] = EMPTY_SQUARE;
                board[0][2] = 'K';
                board[0][3] = 'R';
                board[0][4] = EMPTY_SQUARE;
                whiteTurn = !whiteTurn;
                return '.';
            }
        }
        
        //Same Logic as stated in IsValid
        int srcCol = move.charAt(0) - 'a';
        int srcRow = '8' - move.charAt(1);
        int destCol = move.charAt(2) - 'a';
        int destRow = '8' - move.charAt(3);
    
        char piece = board[srcRow][srcCol];
        char capturedPiece = board[destRow][destCol];
        
        //Helps with Castles and Enpassants
        if(!proxy){
            switch (piece) {
                case 'k':
                    NONMOVEABLE[0] = true;
                    break;
                case 'K':
                    NONMOVEABLE[3] = true;
                    break;
                case 'r':
                    if (srcCol == 7){
                        NONMOVEABLE[2] = true;
                        break;
                    }
                    if (srcCol == 0){
                        NONMOVEABLE[1] = true;
                        break;
                    }
                    break;
                case 'R':
                    if (srcCol == 7){
                        NONMOVEABLE[5] = true;
                        break;
                    }
                    if (srcCol == 0){
                        NONMOVEABLE[4] = true;
                        break;
                    }
                    break;
                case 'p':
                    if (destRow == srcRow - 2) {
                        enPassantTargetSquare = 50 + destCol;
                        break;
                    }
                    break;
                case 'P':
                    if (destRow == srcRow + 2) {
                        enPassantTargetSquare = 20 + destCol;
                        break;
                    }
                    break;
            }
            
            if (enPassant) {
                board[destRow][destCol] = piece;
                board[srcRow][srcCol] = EMPTY_SQUARE;
                if(whiteTurn){
                    board[destRow + 1][destCol] = EMPTY_SQUARE;
                    whiteTurn = !whiteTurn;
                    return 'P';
                }
                else {
                    board[destRow - 1][destCol] = EMPTY_SQUARE;
                    whiteTurn = !whiteTurn;
                    return 'p';
                }
            }
        }
        
        //Check for promotion(White)
        if(piece == 'p' && (destRow == 0 || destRow == 7) && posPromo){
            boolean state = true;
            boolean first = true;
            while(state){
                if (!first){
                    System.out.println("Not an option Try Again");
                }
                first = false;
                System.out.println("White Promote your Pawn to: Rook Bishop Queen Knight");
                System.out.print(": ");
                String selected = scanner.nextLine();
                switch (selected) {
                    case "Rook":
                        piece = 'r';
                        state = false;
                        break;
                    case "Bishop":
                        piece = 'b';
                        state = false;
                        break;
                    case "Knight":
                        piece = 'n';
                        state = false;
                        break;
                    case "Queen":
                        piece = 'q';
                        state = false;
                        break;
                }
            }
        }
    
        //Check for promotion(Black)
        if(piece == 'P' && (destRow == 0 || destRow == 7) && posPromo){
            boolean state = true;
            boolean first = true;
            while(state){
                if (!first){
                    System.out.println("Not an option Try Again");
                }
                first = false;
                System.out.println("Black Promote your Pawn to: Rook Bishop Queen Knight");
                System.out.print(": ");
                String selected = scanner.nextLine();
                switch (selected) {
                    case "Rook":
                        piece = 'R';
                        state = false;
                        break;
                    case "Bishop":
                        piece = 'B';
                        state = false;
                        break;
                    case "Knight":
                        piece = 'N';
                        state = false;
                        break;
                    case "Queen":
                        piece = 'Q';
                        state = false;
                        break;
                }
            }
        }
        
        // Make the move
        board[destRow][destCol] = piece;
        board[srcRow][srcCol] = EMPTY_SQUARE;
        whiteTurn = !whiteTurn;
        return capturedPiece;
    }

//Most-Used-Method checks given a current board condition if the king would be in check or not.
    private boolean isCheck(boolean isWhite) {
        // Find the position of the king
        int kingRow = -1;
        int kingCol = -1;
        char king = isWhite ? 'k' : 'K';
    
        // Search for the king's position on the board
        for (int row = 0; row < BOARD_SIZE; row++) {
            for (int col = 0; col < BOARD_SIZE; col++) {
                if (board[row][col] == king) {
                    kingRow = row;
                    kingCol = col;
                    break;
                }
            }
        }
        
        //Error fix for when checkmate checks calls this with bad movements
        if (kingRow == -1 || kingCol == -1){
            return true;
        }
        
        // Check if any opponent's piece can attack the king
        for (int row = 0; row < BOARD_SIZE; row++) {
            for (int col = 0; col < BOARD_SIZE; col++) {
                char piece = board[row][col];
                if (isPieceWhite(piece) != isWhite && isValidSpecificMove(piece, row, col, kingRow, kingCol)) {
                    return true;
                }
            }
        }
    
        return false;
    }
    

//Helper method to undo a proxy move made for checks
    private void undoMove(String move, char capturedPiece) {
        int srcCol = move.charAt(0) - 'a';
        int srcRow = '8' - move.charAt(1);
        int destCol = move.charAt(2) - 'a';
        int destRow = '8' - move.charAt(3);
    
        char piece = board[destRow][destCol];
    
        // Undo the move
        board[srcRow][srcCol] = piece;
        board[destRow][destCol] = capturedPiece;
        whiteTurn = !whiteTurn;
    }

//Checks if the game is in stalemate
    private boolean isStalemate(boolean isWhite) {
        if (isCheck(isWhite)) {
            return false; // Not stalemate if the player is in check
        }
        // Check if any move is legal
        for (int srcRow = 0; srcRow < BOARD_SIZE; srcRow++) {
            for (int srcCol = 0; srcCol < BOARD_SIZE; srcCol++) {
                char piece = board[srcRow][srcCol];
                if (isPieceWhite(piece) == isWhite) {
                    for (int destRow = 0; destRow < BOARD_SIZE; destRow++) {
                        for (int destCol = 0; destCol < BOARD_SIZE; destCol++) {
                            String move = formatMove(srcCol, srcRow, destCol, destRow);
                            if (isValidMove(move)) {
                                return false; // At least one move is legal
                            }
                        }
                    }
                }
            }
        }
    
        return true; // No legal move is available
    }

//Gameloop
    public void play() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            printBoard();
            if (isCheckmate(whiteTurn)){
                System.out.println("Game Over Player Checkmated");
                break;}
            if (isStalemate(whiteTurn)){
                System.out.println("Game Over Stalemate-Tie");
                break;
            }
            if (whiteTurn) {
                System.out.print("White's move: ");
            } 
            else {
            System.out.print("Black's move: ");
            }

            String move = scanner.nextLine();

            if (move.equalsIgnoreCase("exit")){
                System.out.println("Game terminated.");
                break;
                }
            if (isValidMove(move)) {
                enPassantTargetSquare = -1;
                makeMove(move, true, scanner, false);
                enPassant = false;
                } 
            else {
            System.out.println("Invalid move. Please try again.");
            }
        }
    }
}
