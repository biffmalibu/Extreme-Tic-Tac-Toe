// File:   TicTacToe.java
// Author: Hank Feild, CSC460 class
// Author: Bradford Torpey, CSC460 class
// Date:   2022-03-10, updated 2024-04-01, 2024-04-02
// Purpose: Plays a game of TicTacToe between the user and the computer using Minimax.

// Added support for depth limiting and alpha-beta pruning.

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

/**
 * Represents a TicTacToe game.
 */
public class TicTacToe {

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
         * @param board Should be 9 characters long, each character 
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
         *             the range [1,9].
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
         *      - three Xs or Os in a row (vertically, horizontally, or diagonally)
         *      - all 9 spots are filled
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

            // True if the board is filled or there are three X's or O's in a row.
            return
                // If we don't have three in a row, but the whole board is full, then
                // there's a draw.
                isFilled || utility() != 0;
        }

            
        /**
         * Finds the utility of a terminal state. This 1 if X has three in a row,
         * -1 if O has three in a row, and 0 otherwise.
         * 
         * @return 1 if X wins, -1 if O wins, 0 if a draw.
         */
        public double utility() {
            // Three in a row by row.
            if(board[0] == board[1] && board[0] == board[2] && board[0] != ' ')
                return board[0] == 'X' ? 1 : -1;
            
            if(board[3] == board[4] && board[3] == board[5] && board[3] != ' ')
                return board[3] == 'X' ? 1 : -1;

            if(board[6] == board[7] && board[6] == board[8] && board[6] != ' ')
                return board[6] == 'X' ? 1 : -1;


            // Three in a row by column.
            if(board[0] == board[3] && board[0] == board[6] && board[0] != ' ')
                return board[0] == 'X' ? 1 : -1;

            if(board[1] == board[4] && board[1] == board[7] && board[1] != ' ')
                return board[1] == 'X' ? 1 : -1;

            if(board[2] == board[5] && board[2] == board[8] && board[2] != ' ')
                return board[2] == 'X' ? 1 : -1;


            // Three in a row by diagonal.
            if(board[0] == board[4] && board[0] == board[8] && board[0] != ' ')
                return board[0] == 'X' ? 1 : -1;

            if(board[2] == board[4] && board[2] == board[6] && board[2] != ' ')
                return board[2] == 'X' ? 1 : -1;

            // If nothing else, draw.
            return 0;
        }

        /**
         * Evaluates a non-terminal state. Each column that has two X's or two O's
         * and one open space yields a value of 1 (for X) or -1 (for O). This is
         * summed across the three columns for the final value.
         * 
         * @return A positive return value favors X's chances of winning, negative
         * favors O's, an 0 is an expected draw.
         */
        public double eval(){
            double total = 0;

            // Two in a row by column.
            if(board[0] == board[3] && board[6] == ' ' || board[0] == board[6] && 
                    board[3] == ' ' || board[3] == board[6] && board[0] == ' ')
                total += board[0] == 'X' || board[3] == 'X' ? 1 : -1;

            if(board[1] == board[4] && board[7] == ' ' || board[1] == board[7] && 
                    board[4] == ' ' || board[4] == board[7] && board[1] == ' ')
                total += board[1] == 'X' || board[4] == 'X' ? 1 : -1;

            if(board[2] == board[5] && board[8] == ' ' || board[2] == board[8] && 
                    board[5] == ' ' || board[5] == board[8] && board[2] == ' ')
                total += board[2] == 'X' || board[5] == 'X' ? 1 : -1;

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
    Scanner input;
    Minimax minimax;
    boolean useAlphaBeta;
    boolean useDepthLimit;
    int depth;

    /**
     * Initializes helpers for the game.
     */
    public TicTacToe(int loggingDepth, boolean useAlphaBeta, boolean useDepthLimit, int depth) {
        input = new Scanner(System.in);
        minimax = new Minimax();
        minimax.setAlphaBeta(useAlphaBeta);
        this.loggingDepth = loggingDepth;
        this.useAlphaBeta = useAlphaBeta;
        this.useDepthLimit = useDepthLimit;
        this.depth = depth;
        System.out.println("useAlphaBeta: "+ useAlphaBeta +", useDepthLimit: "+ useDepthLimit +", depth: "+ depth +", loggingDepth: "+ loggingDepth);
    }

    /**
     * Starts a tic-tac-toe game between the user and the computer.
     */
    public void run(){
        int userMove, aiMove;
        currentState = new TicTacToeState("         ".toCharArray(), '?', 0);
        System.out.println("Key: ");
        printBoard("123456789".toCharArray());
        System.out.println();
        printBoard(currentState.board);

        // Until the game is over.
        while(true){
            // Human's turn.
            System.out.print("Your turn; enter the space # where you'd like to put your X: ");
            userMove = input.nextInt();
            while(currentState.board[userMove-1] != ' '){
                System.out.print("That spot is taken; try again: ");
                userMove = input.nextInt();
            }
            currentState.makeMove('X', userMove);

            printBoard(currentState.board);

            // Check if the human won.
            if(currentState.isTerminal())
                break;

            // System.out.println(currentState);
            // break;

            // Computer's turn.
            System.out.println("\nComputer's turn:");
            aiMove = ((TicTacToeActionUtility) minimax.value(currentState, depth, loggingDepth, "", Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY)).move;
            System.out.println("States expanded: " + Minimax.getStateCount());
            Minimax.resetStateCount();
            currentState.makeMove('O', aiMove);

            printBoard(currentState.board);

            // Check if the AI won.
            if(currentState.isTerminal())
                break;
        }

        System.out.println("Game over. Utility: "+ currentState.utility());

    }

    /**
     * Prints the board as a 3x3 table.
     * 
     * @param board The tic-tac-toc board to print.
     */
    public void printBoard(char[] board){
        for(int i = 0; i < board.length; i++){
            if(i > 0 && i % 3 == 0){
                System.out.println("\n---+---+---");
            }
            System.out.print(" "+ board[i] + " ");
            if(i % 3 < 2){
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
            String USAGE = "Usage: java TicTacToe [-h] [-a] [-d <depth>] [-l <loggingDepth>]\n" +
                    "All parameters are optional.\n" +
                    "  -h: Display this help message.\n" +
                    "  -a: Enable alpha-beta pruning.\n" +
                    "  -d <depth>: Specify the depth for depth-limited minimax.\n" +
                    "  -l <loggingDepth>: Specify the logging depth.\n";

            int loggingDepth = 0;
            boolean useAlphaBeta = false;
            boolean useDepthLimit = false;
            int depth = -1;

            if (args.length > 0) {
                if (args[0].equals("-h")) {
                    System.out.println(USAGE);
                    System.exit(0);
                }

                for (int i = 0; i < args.length; i++) {
                    if (args[i].equals("-a")) {
                        useAlphaBeta = true;
                    }
                    if (args[i].equals("-d")) {
                        if (i + 1 < args.length) {
                            depth = Integer.parseInt(args[i + 1]);
                            useDepthLimit = true;
                        } else {
                            System.out.println("Invalid depth value.");
                            System.exit(0);
                        }
                    }
                    if (args[i].equals("-l")) {
                        if (i + 1 < args.length) {
                            loggingDepth = Integer.parseInt(args[i + 1]);
                        } else {
                            System.out.println("Invalid logging depth value.");
                            System.exit(0);
                        }
                    }
                }
            }

            TicTacToe tictactoe = new TicTacToe(loggingDepth, useAlphaBeta, useDepthLimit, depth);
            tictactoe.run();
        }
    }


