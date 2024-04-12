// File:   AIvsAI.java
// Author: Bradford Torpey, CSC460 class
// Date:   2024-04-12
// Purpose: Plays a game of ExtremeTicTacToe between two computers using Minimax.

// Github copilot was used to cleanup/revise the eval() method.

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

/**
 * Represents a TicTacToe game.
 */
public class AIvsAI {

    /**
     * Represents a specific state of a tic-tac-toe board (the positions of the
     * X's, O's, and open spots).
     */
    public class TicTacToeState implements GameState {
        public char[] board;
        public char lastPlayer;
        public int move;

        /**
         * Initializes a tic-tac-toe state.
         * 
         * @param board Should be 16 characters long, each character 
         *              representing one spot on the board. Valid 
         *              characters are: X, O, and ' ' (space).
         * @param lastPlayer The player who made the most recent move.
         * @param move The spot selected in the most recent move.
         */
        public TicTacToeState(char[] board, char lastPlayer, int move){
            this.board = board;
            this.lastPlayer = lastPlayer;
            this.move = move;
        }

        /**
         * Updates the given spot on the board with the player's mark.
         * 
         * @param player The player who made the most recent move (should be X 
         *               or O)
         * @param move The spot selected in the most recent move, should be in
         *             the range [1,16].
         */
        public void makeMove(char player, int move){
            board[move-1] = player;
            this.move = move;
            this.lastPlayer = player;
        }
        
        /**
         * @return A summary of this state as a string, including the board, 
         *         player, and action.
         */
        public String toString(){
            return "[\n\tboard: "+ Arrays.toString(board) +",\n\tplayer: "+
                lastPlayer +",\n\tmove: "+ move +"\n]";
        }

        /**
         * @return A deep copy of this state.
         */
        public TicTacToeState clone(){
            return new TicTacToeState(board.clone(), lastPlayer, move);
        }

        /**
         * @return Returns true if the given tic-tac-toe state is terminal:
         *      - four Xs or Os in a row (vertically, horizontally, or diagonally)
         *      - four Xs or Os in a square
         *      - four Xs or Os in the four corners
         *      - all 16 spots are filled
         */
        public boolean isTerminal() {
            boolean isFilled = true;
            // Check if the board if filled.
            for(int i = 0; i < board.length; i++){
                if(board[i] == ' '){
                    isFilled = false;
                    break;
                }
            }
            // True if the board is filled or there are four in a row, column, or diagonal, square, or corners.
            return
                // If we don't have four a win, but the whole board is full, then
                // there's a draw.
                isFilled || utility() != 0;
        }

            
        /**
         * Finds the utility of a terminal state. This 1 if X has four in a row, column, diagonal, corner, or square,
         * -1 if O has four, and 0 otherwise.
         * 
         * @return 1 if X wins, -1 if O wins, 0 if a draw.
         */
        public double utility() {
            // Four in a row by row.
            if(board[0] == board[1] && board[0] == board[2] && board[0] == board[3] && board[0] != ' ')
            return board[0] == 'X' ? 1 : -1;
            
            if(board[4] == board[5] && board[4] == board[6] && board[4] == board[7] && board[4] != ' ')
            return board[4] == 'X' ? 1 : -1;

            if(board[8] == board[9] && board[8] == board[10] && board[8] == board[11] && board[8] != ' ')
            return board[8] == 'X' ? 1 : -1;

            if(board[12] == board[13] && board[12] == board[14] && board[12] == board[15] && board[12] != ' ')
            return board[12] == 'X' ? 1 : -1;


            // Four in a row by column.
            if(board[0] == board[4] && board[0] == board[8] && board[0] == board[12] && board[0] != ' ')
            return board[0] == 'X' ? 1 : -1;

            if(board[1] == board[5] && board[1] == board[9] && board[1] == board[13] && board[1] != ' ')
            return board[1] == 'X' ? 1 : -1;

            if(board[2] == board[6] && board[2] == board[10] && board[2] == board[14] && board[2] != ' ')
            return board[2] == 'X' ? 1 : -1;

            if(board[3] == board[7] && board[3] == board[11] && board[3] == board[15] && board[3] != ' ')
            return board[3] == 'X' ? 1 : -1;


            // Four in a row by diagonal.
            if(board[0] == board[5] && board[0] == board[10] && board[0] == board[15] && board[0] != ' ')
            return board[0] == 'X' ? 1 : -1;

            if(board[3] == board[6] && board[3] == board[9] && board[3] == board[12] && board[3] != ' ')
            return board[3] == 'X' ? 1 : -1;

            // Four in a square.
            if(board[0] == board[1] && board[0] == board[4] && board[0] == board[5] && board[0] != ' ')
            return board[0] == 'X' ? 1 : -1;

            if(board[1] == board[2] && board[1] == board[5] && board[1] == board[6] && board[1] != ' ')
            return board[1] == 'X' ? 1 : -1;

            if(board[2] == board[3] && board[2] == board[6] && board[2] == board[7] && board[2] != ' ')
            return board[2] == 'X' ? 1 : -1;

            if(board[4] == board[5] && board[4] == board[8] && board[4] == board[9] && board[4] != ' ')
            return board[4] == 'X' ? 1 : -1;

            if(board[5] == board[6] && board[5] == board[9] && board[5] == board[10] && board[5] != ' ')
            return board[5] == 'X' ? 1 : -1;

            if(board[6] == board[7] && board[6] == board[10] && board[6] == board[11] && board[6] != ' ')
            return board[6] == 'X' ? 1 : -1;

            if(board[8] == board[9] && board[8] == board[12] && board[8] == board[13] && board[8] != ' ')
            return board[8] == 'X' ? 1 : -1;

            if(board[9] == board[10] && board[9] == board[13] && board[9] == board[14] && board[9] != ' ')
            return board[9] == 'X' ? 1 : -1;

            if(board[10] == board[11] && board[10] == board[14] && board[10] == board[15] && board[10] != ' ')
            return board[10] == 'X' ? 1 : -1;


            // Four corners.
            if(board[0] == board[3] && board[0] == board[12] && board[0] == board[15] && board[0] != ' ')
            return board[0] == 'X' ? 1 : -1;

            // If nothing else, draw.
            return 0;
        }
        /**
         * Evaluates a non-terminal state. Each column that has three X's or three O's
         * and one open space yields a value of 1 (for X) or -1 (for O). This is
         * summed across the four columns for the final value.
         * 
         * @return A positive return value favors X's chances of winning, negative
         * favors O's, an 0 is an expected draw.
         */
        public double eval() { // This method was produced using github copilot
            double total = 0;
            // Two in a row by column.
            for (int i = 0; i < 4; i++) {
                if (board[i] == board[i + 4] && board[i + 8] == ' ' || board[i] == board[i + 8] && board[i + 4] == ' '
                        || board[i + 4] == board[i + 8] && board[i] == ' ') {
                    total += board[i] == 'X' || board[i + 4] == 'X' ? 1 : -1;
                }
            }

            // Two in a row by row.
            for (int i = 0; i < 12; i += 4) {
                if (board[i] == board[i + 1] && board[i + 2] == ' ' || board[i] == board[i + 2] && board[i + 1] == ' '
                        || board[i + 1] == board[i + 2] && board[i] == ' ') {
                    total += board[i] == 'X' || board[i + 1] == 'X' ? 1 : -1;
                }
            }

            // Two in a row by diagonal.
            if (board[0] == board[5] && board[10] == ' ' || board[0] == board[10] && board[5] == ' '
                    || board[5] == board[10] && board[0] == ' ') {
                total += board[0] == 'X' || board[5] == 'X' ? 1 : -1;
            }

            if (board[3] == board[6] && board[9] == ' ' || board[3] == board[9] && board[6] == ' '
                    || board[6] == board[9] && board[3] == ' ') {
                total += board[3] == 'X' || board[6] == 'X' ? 1 : -1;
            }

            // Two in a square.
            for (int i = 0; i < 12; i += 4) {
                if (board[i] == board[i + 1] && board[i + 4] == ' ' || board[i] == board[i + 4] && board[i + 1] == ' '
                        || board[i + 1] == board[i + 4] && board[i] == ' ') {
                    total += board[i] == 'X' || board[i + 1] == 'X' ? 1 : -1;
                }
            }

            // Four corners.
            if (board[0] == board[3] && board[12] == ' ' || board[0] == board[12] && board[3] == ' '
                    || board[3] == board[12] && board[0] == ' ') {
                total += board[0] == 'X' || board[3] == 'X' ? 1 : -1;
            }

            return total;
        }
        /**
         * Generates a list of successors for the current state. These are all
         * possible moves of the next player into blank spaces, where the "next
         * player" is whichever player isn't state.player.
         * 
         * @return A list of all possible successor of the given state for the next
         *         player.
         */
        public ArrayList<TicTacToeState> successors() {
            ArrayList<TicTacToeState> successorStates = new ArrayList<TicTacToeState>();
            TicTacToeState successorState;
            char nextPlayer = lastPlayer == 'X' ? 'O' : 'X';

            for(int i = 0; i < board.length; i++){
                if(board[i] == ' '){
                    successorState = clone();
                    successorState.makeMove(nextPlayer, i+1);
                    successorStates.add(successorState);
                }
            }
            
            return successorStates;
        }

        /**
         * Determines if this is a "max" state, meaning a state for which Minimax
         * should be maximizing over values of successors, meaning it's X's turn
         * next.
         * 
         * @return True if the next player is X.
         */
        public boolean isMax() {
            // If the last player is O, then X is now going and therefore we're in
            // a max node.
            return lastPlayer == 'O'; 
        }

        /**
         * A helper function that simply wraps a move (extracted from the given 
         * state) and the utility value of this terminal state.
         * 
         * @return state.move and utility wrapped up in an easy to use object.
         */
        public TicTacToeActionUtility getActionUtility() {
            return new TicTacToeActionUtility(move, utility());
        }

        /**
         * A helper function that simply wraps a move (extracted from the given 
         * state) and the evaluation value for this state.
         * 
         * @return state.move and utility wrapped up in an easy to use object.
         */
        public TicTacToeActionUtility getActionEval() {
            return new TicTacToeActionUtility(move, eval());
        }

        
        /**
         * A helper function that simply wraps a move (extracted from the given 
         * state) and the evaluation value for this state.
         * 
         * @return state.move and utility wrapped up in an easy to use object.
         */
        public TicTacToeActionUtility getActionUtility(double utility) {
            return new TicTacToeActionUtility(move, utility);
        }

    }

    /**
     * Serves as a wrapper for a tic-tac-toe move and the utility associated
     * with it.
     */
    public class TicTacToeActionUtility implements ActionUtility  {
        public double utility;
        public int move;

        /**
         * Initializes the action and utility.
         * @param move The spot selected in the most recent move.
         * @param utility The utility value associated with the move.
         */
        public TicTacToeActionUtility(int move, double utility){
            this.move = move;
            this.utility = utility;
        }

        /**
         * @return The utility of this move.
         */
        public double getUtility() {
            return utility;
        }

        /**
         * @return A description of this move and utility.
         */
        public String toString(){
            return "[\n\tmove: "+ move +",\n\tutility: "+ utility +"\n]";
        }
    }


    // TicTacToe data members.
    TicTacToeState currentState;
    int loggingDepth;
    boolean playerOneAlphaBeta, playerTwoAlphaBeta;
    int playerOneDepthLimit, playerTwoDepthLimit;
    Scanner input;
    Minimax minimax;


    /**
     * Initializes helpers for the game.
     */
    public AIvsAI(int loggingDepth, boolean playerOneAlphaBeta, boolean playerTwoAlphaBeta, int playerOneDepthLimit, int playerTwoDepthLimit) {
        input = new Scanner(System.in);
        minimax = new Minimax();
        this.loggingDepth = loggingDepth;
        this.playerOneAlphaBeta = playerOneAlphaBeta;
        this.playerTwoAlphaBeta = playerTwoAlphaBeta;
        this.playerOneDepthLimit = playerOneDepthLimit;
        this.playerTwoDepthLimit = playerTwoDepthLimit;
        System.out.println("Player one: Depth limit: " + playerOneDepthLimit + " Alpha-beta: " + playerOneAlphaBeta);
        System.out.println("Player two: Depth limit: " + playerTwoDepthLimit + " Alpha-beta: " + playerTwoAlphaBeta);
    }

    /**
     * Starts a tic-tac-toe game between the user and the computer.
     */
    public void run(){
        int playerOneMove, playerTwoMove;
        currentState = new TicTacToeState("                ".toCharArray(), '?', 0);
        System.out.println("Key: ");
        printBoard(new char[] {'1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F', 'G'});
        System.out.println();
        printBoard(currentState.board);

        // Until the game is over.
        while(true){
            // Player one turn.
            System.out.println("\nPlayer one's turn:");
            Minimax.setAlphaBeta(playerOneAlphaBeta);
            playerOneMove = ((TicTacToeActionUtility) minimax.value(currentState, playerOneDepthLimit, loggingDepth, "", Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY)).move;
            System.out.println("States expanded: " + Minimax.getStateCount());
            Minimax.resetStateCount();
            currentState.makeMove('X', playerOneMove);

            printBoard(currentState.board);

            // Check if player one won.
            if(currentState.isTerminal()) {
                break;
            }


            // Player two turn.
            System.out.println("\nPlayer Two's turn:");
            Minimax.setAlphaBeta(playerTwoAlphaBeta);
            playerTwoMove = ((TicTacToeActionUtility) minimax.value(currentState, playerTwoDepthLimit, loggingDepth, "", Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY)).move;
            System.out.println("States expanded: " + Minimax.getStateCount());
            Minimax.resetStateCount();
            currentState.makeMove('O', playerTwoMove);

            printBoard(currentState.board);

            // Check if player two won.
            if(currentState.isTerminal()) {
                break;
            }
        }

        System.out.println("Game over. Utility: "+ currentState.utility());
        if(currentState.utility() == 0){
            System.out.println("Draw.");
        }
        else if(currentState.utility() == 1){
            System.out.println("Player one wins.");
        }
        else{
            System.out.println("Player two wins.");
        }

    }

    /**
     * Prints the board as a 4x4 table.
     * 
     * @param board The tic-tac-toc board to print.
     */
    public void printBoard(char[] board){
        for(int i = 0; i < board.length; i++){
            if(i > 0 && i % 4 == 0){
                System.out.println("\n---+---+---+---");
            }
            System.out.print(" "+ board[i] + " ");
            if(i % 4 < 3){
                System.out.print("|");
            }
        }
        System.out.println();
    }
    /**
     * Starts a game of tic-tac-toe between the user and the computer.
     * @param args Ignored.
     */
        public static void main(String[] args) {
            String USAGE = "Usage: java AIvsAI [-h] [-a] [-d <depth>] [-l <loggingDepth>]\n" +
                    "  -h: Display this help message.\n" +
                    "  -1: <difficulty>: Player one difficulty. (beginner, intermediate, advanced)\n" +
                    "  -2: <difficulty>: Player two difficulty. (beginner, intermediate, advanced)\n" +
                    "  -l <loggingDepth>: Specify the logging depth. (optional)\n";

            int loggingDepth = 0;
            boolean playerOneAlphaBeta = false;
            boolean playerTwoAlphaBeta = false;
            int playerOneDepthLimit = -1;
            int playerTwoDepthLimit = -1;
            String playerOneDifficulty;
            String playerTwoDifficulty;

            if (args.length > 0) {
                if (args[0].equals("-h")) {
                    System.out.println(USAGE);
                    System.exit(0);
                }

                boolean foundPlayerOneDifficulty = false;
                boolean foundPlayerTwoDifficulty = false;

                for (int i = 0; i < args.length; i++) {
                    if (args[i].equals("-1")) {
                        if (i + 1 < args.length) {
                            playerOneDifficulty = args[i + 1];
                            if (playerOneDifficulty.equals("beginner")) {
                                playerOneDepthLimit = 5;
                                playerOneAlphaBeta = true;
                            } else if (playerOneDifficulty.equals("intermediate")) {
                                playerOneDepthLimit = 8;
                                playerOneAlphaBeta = true;
                            } else if (playerOneDifficulty.equals("advanced")) {
                                playerOneDepthLimit = 13;
                                playerOneAlphaBeta = true;
                            } else {
                                System.out.println("Invalid player one difficulty value. Please choose either beginner, intermediate, or advanced.");
                                System.out.println(USAGE);
                                System.exit(0);
                            }
                            foundPlayerOneDifficulty = true;
                        } else {
                            System.out.println("Invalid player one difficulty value. Please provide a difficulty level.");
                            System.out.println(USAGE);
                            System.exit(0);
                        }
                    }
                    if (args[i].equals("-2")) {
                        if (i + 1 < args.length) {
                            playerTwoDifficulty = args[i + 1];
                            if (playerTwoDifficulty.equals("beginner")) {
                                playerTwoDepthLimit = 5;
                                playerTwoAlphaBeta = true;
                            } else if (playerTwoDifficulty.equals("intermediate")) {
                                playerTwoDepthLimit = 8;
                                playerTwoAlphaBeta = true;
                            } else if (playerTwoDifficulty.equals("advanced")) {
                                playerTwoDepthLimit = 13;
                                playerTwoAlphaBeta = true;
                            } else {
                                System.out.println("Invalid player two difficulty value. Please choose either beginner, intermediate, or advanced.");
                                System.out.println(USAGE);
                                System.exit(0);
                            }
                            foundPlayerTwoDifficulty = true;
                        } else {
                            System.out.println("Invalid player two difficulty value. Please provide a difficulty level.");
                            System.out.println(USAGE);
                            System.exit(0);
                        }
                    }
                    if (args[i].equals("-l")) {
                        if (i + 1 < args.length) {
                            loggingDepth = Integer.parseInt(args[i + 1]);
                        } else {
                            System.out.println("Invalid logging depth value.");
                            System.out.println(USAGE);
                            System.exit(0);
                        }
                    }
                }

                if (!foundPlayerOneDifficulty || !foundPlayerTwoDifficulty) {
                    System.out.println("Please provide both player one and player two difficulty values.");
                    System.out.println(USAGE);
                    System.exit(0);
                }
            } else {
                System.out.println("Please provide command line arguments.");
                System.out.println(USAGE);
                System.exit(0);
            }

            AIvsAI aivsai = new AIvsAI(loggingDepth, playerOneAlphaBeta, playerTwoAlphaBeta, playerOneDepthLimit, playerTwoDepthLimit);
            aivsai.run();
        }
    }


