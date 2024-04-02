// File:   Minimax.java
// Author: Hank Feild, CSC460 class
// Date:   2022-03-10, updated 2024-04-01
// Purpose: Carries out the actions of Minimax.

/**
 * Performs Minimax to find the action with the best outcome for a
 * given player.
 */
public class Minimax  {

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

        if(state.isTerminal()) {
            return state.getActionUtility();

        } else if(depth == 0) {
            // return the result of evaluation function.
            return state.getActionEval();


        } else if(state.isMax()) {
            return maxValue(state, depth, loggingDepth, loggingPrefix);

        } else {
            return minValue(state, depth, loggingDepth, loggingPrefix);
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
}