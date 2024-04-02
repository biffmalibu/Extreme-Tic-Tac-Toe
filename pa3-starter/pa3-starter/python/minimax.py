'''
File:   minimax.py
Author: Hank Feild
Date:   2024-04-01

Contains several abstract and concrete classes for performing 
and assisting with Minimax.
'''


from abc import ABC, abstractmethod

####################################################################################################

class GameState(ABC):
    '''An abstract representation of a game state. Subclass this class
    and implement each of the methods to use it with Minimax.
    '''

    @abstractmethod
    def clone(self):
        '''Returns (GameState): A deep copy of this state.'''
        pass

    @abstractmethod
    def isTerminal(self):
        '''Returns (boolean): True if this state is terminal.'''
        pass

    @abstractmethod 
    def utility(self):
        '''Returns (float): The utility of this state (assuming it is terminal).'''
        pass

    @abstractmethod
    def eval(self):
        '''Returns (float): An estimate of the utility of this state (assuming it is non-terminal).'''
        pass

    @abstractmethod
    def successors(self):
        '''Returns (list(GameState)): A list of successors of this state.'''
        pass

    @abstractmethod
    def isMax(self):
        '''Returns (boolean): True if this is a max state.'''
        pass

    @abstractmethod
    def getActionUtility(self, utility=None):
        '''
        Parameters:
            utility (float): The utility value to pair with the current state; if absent, the utility of the current state is used.

        Returns (ActionUtility): An action-utility pair using the given utility and the current state's action or move.
        '''
        pass


    @abstractmethod
    def getActionEval(self):
        '''Returns (ActionUtility): An action-utility pair using the return value from eval() and the current state's action or move.
        '''
        pass

    @abstractmethod
    def __str__(self):
        '''Returns (str): A string representation of the state; this should be displayable.'''
        pass


####################################################################################################


class ActionUtility(ABC):
    '''Represents an action (e.g., a move)-utility pair. Each game may have a unique
    way of representing a move (e.g., as a string, int, object, etc.), and so
    this class does not impose a specific type on that. The utility must be a numeric
    type, however. Subclass this class for each game to provide a specific implementation.
    '''

    @abstractmethod
    def __str__(self):
        '''Returns (str): A string representation of this action utility pair.'''
        pass

    def getUtility(self):
        '''Returns (float): The utility of this action-utility pair.'''
        return self.utility
    

####################################################################################################
class Minimax:
    '''A implementation of Minimax for two-player, zero-sum games.'''

    def value(self, state, depth, loggingDepth, loggingPrefix):
        '''Determines the action/move that the next player should make given the
        current state of the game by running Minimax with the state as the root.
     
        Parameters:
            state (GameState): The state to find the next move for.
            depth (int): The depth at which to stop in depth-limited Minimax; use -1
                         to conduct a full Minimax search.
            loggingDepth (int): How many levels down the Minimax tree to display info
                               for; use 0 to disable this feature. 
            loggingPrefix (str): Used in conjunction with logging; use this to provide 
                                 additional spacing for each subsequent level of the 
                                 Minimax tree for easier reading.
     
        Returns (ActionUtility): The action/move the next player should make and
            the expected utility of that move.
        '''
        if state.isTerminal():
            return state.getActionUtility()
        
        elif depth == 0:
            return state.getActionEval()
        
        elif state.isMax():
            return self.maxValue(state, depth, loggingDepth, loggingPrefix)
        
        else:
            return self.minValue(state, depth, loggingDepth, loggingPrefix)


    def maxValue(self, state, depth, loggingDepth, loggingPrefix):
        '''Selects the move that maximizes utility over the successors of the
        given state.

        Parameters:
            state (GameState): The state to find the next move for.
            depth (int): The depth at which to stop in depth-limited Minimax; use -1
                         to conduct a full Minimax search.
            loggingDepth (int): How many levels down the Minimax tree to display info
                               for; use 0 to disable this feature. 
            loggingPrefix (str): Used in conjunction with logging; use this to provide 
                                 additional spacing for each subsequent level of the 
                                 Minimax tree for easier reading.
     
        Returns (ActionUtility): The action/move the next player should make and
            the expected utility of that move.
        '''
        actionUtility = None

        for successor in state.successors():
            successorActionUtility = self.value(successor, depth-1, loggingDepth-1, loggingPrefix+' ')

            # Logging if enabled.
            if loggingDepth > 0:
                print(f"{loggingPrefix}maxValue: "+
                      str(successor).replace('\n', '\n'+loggingPrefix) +
                      f"\n{loggingPrefix}"+
                      str(successorActionUtility).replace('\n', '\n'+loggingPrefix))
                
            if actionUtility is None or successorActionUtility.getUtility() > actionUtility.getUtility():
                actionUtility = successor.getActionUtility(successorActionUtility.getUtility())


        # More logging.
        if loggingDepth > 0:
            print(f"{loggingPrefix}maxValue: returning "+
                  str(actionUtility).replace('\n', '\n'+loggingPrefix))
            
        return actionUtility
    


    def minValue(self, state, depth, loggingDepth, loggingPrefix):
        '''Selects the move that minimizes utility over the successors of the
        given state.

        Parameters:
            state (GameState): The state to find the next move for.
            depth (int): The depth at which to stop in depth-limited Minimax; use -1
                         to conduct a full Minimax search.
            loggingDepth (int): How many levels down the Minimax tree to display info
                               for; use 0 to disable this feature. 
            loggingPrefix (str): Used in conjunction with logging; use this to provide 
                                 additional spacing for each subsequent level of the 
                                 Minimax tree for easier reading.
     
        Returns (ActionUtility): The action/move the next player should make and
            the expected utility of that move.
        '''
        actionUtility = None

        for successor in state.successors():
            successorActionUtility = self.value(successor, depth-1, loggingDepth-1, loggingPrefix+' ')

            # Logging if enabled.
            if loggingDepth > 0:
                print(f"{loggingPrefix}minValue: "+
                      str(successor).replace('\n', '\n'+loggingPrefix)+
                      f"\n{loggingPrefix}"+
                      str(successorActionUtility).replace('\n', '\n'+loggingPrefix))
                
            if actionUtility is None or successorActionUtility.getUtility() < actionUtility.getUtility():
                actionUtility = successor.getActionUtility(successorActionUtility.getUtility())


        # More logging.
        if loggingDepth > 0:
            print(f"{loggingPrefix}minValue: returning "+
                  str(actionUtility).replace('\n', '\n'+loggingPrefix))
            
        return actionUtility