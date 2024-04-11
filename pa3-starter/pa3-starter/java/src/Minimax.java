// File:   Minimax.java
// Author: Hank Feild, CSC460 class
// Author: Bradford Torpey, CSC460 class
// Date:   2022-03-10, updated 2024-04-01, 2024-04-02
// Purpose: Carries out the actions of Minimax.

// Added support for depth limiting and alpha-beta pruning.


/**
 * Performs Minimax to find the action with the best outcome for a
 * given player.
 */
public class Minimax  {
    private static boolean useAlphaBeta = false;
    private static int stateCount = 0;

    public static void setAlphaBeta(boolean useAlphaBeta) {
        Minimax.useAlphaBeta = useAlphaBeta;
    }
    public static void resetStateCount() {
        stateCount = 0;
    }

    public static int getStateCount() {
        return stateCount;
    }
    /**
     * Determines the action/move that the next player should make given the
     * current state of the game by running Minimax with the state as the root.
     * 
     * @param state The state to find the next move for.
     * @param depth The depth at which to stop in depth-limited Minimax; use -1
     *              to conduct a full Minimax search.
     * @param loggingDepth How many levels down the Minimax tree to display info
     *                    for; use 0 to disable this feature. 
     * @param loggingPrefix Used in conjunction with logging; use this to provide 
     *               additional spacing for each subsequent level of the Minimax
     *               tree for easier reading.
     * @return The action/move the next player should make and the expected
     *         utility of that move.
     */
    public ActionUtility value(GameState state, 
            int depth, int loggingDepth, String loggingPrefix) {

        stateCount++; // Increment the state count

        if (stateCount % 10000000 == 0) {
            System.out.println("States expanded: " + stateCount);
        }

        if(state.isTerminal()) {
            return state.getActionUtility();

        } else if(depth == 0) {
            // return the result of evaluation function.
            return state.getActionEval();

        } else if(state.isMax()) {
            if (useAlphaBeta) {
                return maxValueAlphaBeta(state, depth, loggingDepth, loggingPrefix, Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY);
            } else {
                return maxValue(state, depth, loggingDepth, loggingPrefix);
            }

        } else {
            if (useAlphaBeta) {
                return minValueAlphaBeta(state, depth, loggingDepth, loggingPrefix, Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY);
            } else {
                return minValue(state, depth, loggingDepth, loggingPrefix);
            }
        }
    }

    /**
     * Selects the move that maximizes utility over the successors of the
     * given state.
     * 
     * @param state The state to find the next move for.
     * @param depth The depth at which to stop in depth-limited Minimax; use -1
     *              to conduct a full Minimax search.
     * @param loggingDepth How many levels down the Minimax tree to display info
     *                    for; use 0 to disable this feature. 
     * @param loggingPrefix Used in conjunction with logging; use this to provide 
     *               additional spacing for each subsequent level of the Minimax
     *               tree for easier reading.
     * @return The action/move the next player should make and the expected
     *         utility of that move.
     */
    public ActionUtility maxValue(GameState state, 
            int depth, int loggingDepth, String loggingPrefix){

        ActionUtility actionUtility = null;
        
        for(GameState successor : state.successors()){
            ActionUtility successorActionUtility = 
                value(successor, depth-1, loggingDepth-1, loggingPrefix+" ");

            // Logging.
            if(loggingDepth > 0) {
                System.out.println(loggingPrefix +"maxValue: "+ 
                    successor.toString().replaceAll("\n", "\n"+loggingPrefix) +"\n"+ 
                    loggingPrefix +successorActionUtility.toString().
                    replaceAll("\n", "\n"+loggingPrefix));
            }   

            if(actionUtility == null || 
                    successorActionUtility.getUtility() > actionUtility.getUtility()) {
                actionUtility = successor.getActionUtility(successorActionUtility.getUtility());
            }
        }
        
        // Logging.
        if(loggingDepth > 0) {
            System.out.println(loggingPrefix+ "maxValue: returning "+ 
                actionUtility.toString().replaceAll("\n", "\n"+loggingPrefix));
        }

        return actionUtility;
    }
    /**
     * Selects the move that maximizes utility over the successors of the
     * given state using alpha-beta pruning.
     * 
     * @param state The state to find the next move for.
     * @param depth The depth at which to stop in depth-limited Minimax; use -1
     *              to conduct a full Minimax search.
     * @param loggingDepth How many levels down the Minimax tree to display info
     *                    for; use 0 to disable this feature. 
     * @param loggingPrefix Used in conjunction with logging; use this to provide 
     *               additional spacing for each subsequent level of the Minimax
     *               tree for easier reading.
     * @param alpha The current best value for the maximizing player.
     * @param beta The current best value for the minimizing player.
     * @return The action/move the next player should make and the expected
     *         utility of that move.
     */
    public ActionUtility maxValueAlphaBeta(GameState state, 
        int depth, int loggingDepth, String loggingPrefix, double alpha, double beta) {

        ActionUtility actionUtility = null;
        
        for(GameState successor : state.successors()){
            ActionUtility successorActionUtility = 
                value(successor, depth-1, loggingDepth-1, loggingPrefix+" ");

            // Logging.
            if(loggingDepth > 0) {
                System.out.println(loggingPrefix +"maxValueAlphaBeta: "+ 
                    successor.toString().replaceAll("\n", "\n"+loggingPrefix) +"\n"+ 
                    loggingPrefix +successorActionUtility.toString().
                    replaceAll("\n", "\n"+loggingPrefix));
            }   

            if(actionUtility == null || 
                successorActionUtility.getUtility() > actionUtility.getUtility()) {
                actionUtility = successor.getActionUtility(successorActionUtility.getUtility());
            }

            // Alpha-beta pruning.
            if (actionUtility.getUtility() >= beta) {
                return actionUtility;
            }
            alpha = Math.max(alpha, actionUtility.getUtility());
        }
        
        // Logging.
        if(loggingDepth > 0) {
            System.out.println(loggingPrefix+ "maxValueAlphaBeta: returning "+ 
                actionUtility.toString().replaceAll("\n", "\n"+loggingPrefix));
        }

        return actionUtility;
    }
    /**
     * Selects the move that minimizes utility over the successors of the
     * given state.
     * 
     * @param state The state to find the next move for.
     * @param depth The depth at which to stop in depth-limited Minimax; use -1
     *              to conduct a full Minimax search.
     * @param loggingDepth How many levels down the Minimax tree to display info
     *                    for; use 0 to disable this feature. 
     * @param loggingPrefix Used in conjunction with logging; use this to provide 
     *               additional spacing for each subsequent level of the Minimax
     *               tree for easier reading.
     * @return The action/move the next player should make and the expected
     *         utility of that move.
     */
    public ActionUtility minValue(GameState state, 
        int depth, int loggingDepth, String loggingPrefix){

        ActionUtility actionUtility = null;
        
        for(GameState successor : state.successors()){
            ActionUtility successorActionUtility = 
                value(successor, depth-1, loggingDepth-1, loggingPrefix+" ");

            // Logging.
            if(loggingDepth > 0) {
                System.out.println(loggingPrefix +"minValue: "+ 
                    successor.toString().replaceAll("\n", "\n"+loggingPrefix) +"\n"+ 
                    loggingPrefix + successorActionUtility.toString().
                    replaceAll("\n", "\n"+loggingPrefix));
            }

            if(actionUtility == null || 
                    successorActionUtility.getUtility() < actionUtility.getUtility()){
                actionUtility = successor.getActionUtility(successorActionUtility.getUtility());
            }
        }
        
        // Logging.
        if(loggingDepth > 0)
            System.out.println(loggingPrefix +"minValue: returning "+ 
                actionUtility.toString().replaceAll("\n", "\n"+loggingPrefix));
  
        return actionUtility;
    }
    /**
     * Selects the move that minimizes utility over the successors of the
     * given state using alpha-beta pruning.
     * 
     * @param state The state to find the next move for.
     * @param depth The depth at which to stop in depth-limited Minimax; use -1
     *              to conduct a full Minimax search.
     * @param loggingDepth How many levels down the Minimax tree to display info
     *                    for; use 0 to disable this feature. 
     * @param loggingPrefix Used in conjunction with logging; use this to provide 
     *               additional spacing for each subsequent level of the Minimax
     *               tree for easier reading.
     * @param alpha The current best value for the maximizing player.
     * @param beta The current best value for the minimizing player.
     * @return The action/move the next player should make and the expected
     *         utility of that move.
     */
    public ActionUtility minValueAlphaBeta(GameState state, 
        int depth, int loggingDepth, String loggingPrefix, double alpha, double beta) {

        ActionUtility actionUtility = null;
        
        for(GameState successor : state.successors()){
            ActionUtility successorActionUtility = 
                value(successor, depth-1, loggingDepth-1, loggingPrefix+" ");

            // Logging.
            if(loggingDepth > 0) {
                System.out.println(loggingPrefix +"minValueAlphaBeta: "+ 
                    successor.toString().replaceAll("\n", "\n"+loggingPrefix) +"\n"+ 
                    loggingPrefix +successorActionUtility.toString().
                    replaceAll("\n", "\n"+loggingPrefix));
            }   

            if(actionUtility == null || 
                    successorActionUtility.getUtility() < actionUtility.getUtility()) {
                actionUtility = successor.getActionUtility(successorActionUtility.getUtility());
            }

            // Alpha-beta pruning.
            if (actionUtility.getUtility() <= alpha) {
                return actionUtility;
            }
            beta = Math.min(beta, actionUtility.getUtility());
        }
        
        // Logging.
        if(loggingDepth > 0) {
            System.out.println(loggingPrefix+ "minValueAlphaBeta: returning "+ 
                actionUtility.toString().replaceAll("\n", "\n"+loggingPrefix));
        }

        return actionUtility;
    }


    
}