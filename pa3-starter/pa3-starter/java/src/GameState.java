// File:   GameState.java
// Author: Hank Feild, CSC460 class
// Date:   2024-04-01
// Purpose: Describes an interface for a state in an arbitrary game.

import java.util.ArrayList;

/**
 * Represents a state in a game.
 */
public interface GameState {
    public boolean isTerminal();
    public double utility();
    public double eval();
    public <GS extends GameState> ArrayList<GS> successors();
    public boolean isMax();
    public ActionUtility getActionUtility();
    public ActionUtility getActionEval();
    public ActionUtility getActionUtility(double utility);
    public String toString();
}
