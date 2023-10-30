import java.util.Scanner;
import java.util.Arrays;


//Driver File
public class Main {
    public static void main(String[] args) { 
        Scanner prompt = new Scanner(System.in);
        boolean state = true;
        System.out.println("Welcome to Command-Line Chess\nPress(1) for guide.\nPress(2) to start a game.\nPress(3) to exit.\nPress(4) to learn how to play chess.\nPress(5) to reveal 3 openings to know!\n");
        while(state){
            System.out.println();
            System.out.print(": ");
            String answer = prompt.nextLine();
            switch (answer) {
                case "1":
                    System.out.println("\nWhite starts first black always goes second.\n-All moves will be checked for legalization.\n--To castle left or right if you legally can, type Castle-Left or Castle-Right.\n---To move a piece type the original position and then the final destination.\n----Example e2e4 moves whites center pawn up two squares!\n-----Type exit to giveup/leave the game.\n");
                    break;
                case "2":
                    System.out.println();
                    System.out.println();
                    System.out.println();
                    System.out.println();
                    ChessGame game = new ChessGame();
                    game.play();
                    break;
                case "3":
                    state = false;
                    break;
                case "4":
                    System.out.println("Welcome to the game of chess! In chess, the objective is to checkmate your opponent's king, which means putting the king in a position where it is under attack and cannot escape capture. The game is played on an 8x8 board with alternating dark and light squares. Each player starts with 16 pieces: one king, one queen, two rooks, two knights, two bishops, and eight pawns.");
                    System.out.println("To begin, white always moves first, and players take turns moving their pieces. Each piece has its own unique way of moving. The king can move one square in any direction, while the queen can move any number of squares in any direction: horizontally, vertically, or diagonally. Rooks move horizontally or vertically, bishops move diagonally, and knights move in an L-shape, two squares in one direction and then one square in a perpendicular direction.");
                    System.out.println("Pawns are a bit different. They move forward one square, but on their first move, they have the option to move forward two squares. Pawns capture by moving diagonally one square forward. Additionally, pawns have a special move called en passant, which can be used in specific situations.");
                    System.out.println("If a piece moves to a square occupied by an opponents piece, the opponents piece is captured and removed from the board. The captured piece is out of play for the remainder of the game.");
                    System.out.println("Its important to note that there are some special moves in chess as well. Castling is a move that involves the king and rook and is used to improve the kings safety. En passant, as mentioned earlier, allows a pawn to capture an opponent's pawn under specific conditions.");
                    System.out.println("The game continues until one player achieves checkmate or a draw is reached. A draw can occur due to various reasons, such as stalemate, threefold repetition, or insufficient material to checkmate.");
                    System.out.println("Now that you have a basic understanding of the rules, it's time to start playing and enjoy the strategic challenges of chess! Good luck!");
                    break;
                case "5":
                    //Add 
                    System.out.println("#1 – The French Defense: \n Defense for Black against 1.e4 \n Opening moves: 1.e4 e6");
                    System.out.println("#2 – The Sicilian Defense: \n Defense for Black against 1.e4 \n Opening moves: 1.e4 c5");
                    System.out.println("#3 – The Queen's Gambit Declined: \n Defense against 1.d4 as Black, common starting point for \n a 1.d4 repertoire as White \n Opening moves: 1.d4 d5 2.c4 e6");
                    break;
        prompt.close();
    }
}
//Created by Reuvi Israeli.
