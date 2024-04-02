'''
File:   tictactoe.py
Author: Hank Feild, CSC460 class
Date:   2024-04-01
Purpose: Plays a game of TicTacToe between the user and the computer using Minimax.
'''

from minimax import GameState, ActionUtility, Minimax
import sys

class TicTacToeState(GameState):
    '''Represents a specific state of a tic-tac-toe board (the positions of the
    X's, O's, and open spots).
    '''

    def __init__(self, board, lastPlayer, move):
        '''Initializes a tic-tac-toe state.
         
        Parameters:
            board (list): Should be 9 characters long, each character 
                          representing one spot on the board. Valid 
                          characters are: X, O, and ' ' (space).
            lastPlayer (str): The player who made the most recent move ('X' or 'O')
            move (int): The spot selected in the most recent move.
        '''
        self.board = board
        self.lastPlayer = lastPlayer
        self.move = move

    def makeMove(self, lastPlayer, move):
        '''Updates the given spot on the board with the player's mark.
          
        Parameters:
            lastPlayer (str): The player who made the most recent move (should be 'X' 
                          or 'O')
            move (int): The spot selected in the most recent move, should be in
                        the range [1,9].
        '''
        self.board[move-1] = player
        self.move = move 
        self.lastPlayer = lastPlayer

    def __str__(self):
        '''Returns (str): A summary of this state as a string, including the board,
        player, and action.'''
        return f"[\n\tboard: {str(self.board)}\n\tplayer: {self.lastPlayer}\n\tmove: {self.move}]"
    

    def isTerminal(self):
        '''Returns (boolean): True if the given tic-tac-toe state is terminal:
              - three Xs or Os in a row (vertically, horizontally, or diagonally)
              - all 9 spots are filled
        '''
        return ' ' not in self.board or self.utility() != 0


    def utility(self):
        '''Finds the utility of a terminal state. This 1 if X has three in a row,
        -1 if O has three in a row, and 0 otherwise.
         
        Returns (float): 1 if X wins, -1 if O wins, 0 if a draw.
        '''

        # Check all ways to get three in a row.
        for a,b,c in ((0,1,2), (3,4,5), (6,7,8), # Rows
                      (0,3,6), (1,4,7), (2,5,8), # Columns
                      (0,4,8), (2,4,6)): # Diagonals
            if self.board[a] == self.board[b] == self.board[c] != ' ':
                return 1 if self.board[a] == 'X' else -1
        
        # If nothing else, it's a draw.
        return 0
     

    def eval(self):
        '''Evaluates a non-terminal state. Each column that has two X's or two O's
        and one open space yields a value of 1 (for X) or -1 (for O). This is
        summed across the three columns for the final value.
         
        Returns (float): A positive return value favors X's chances of winning, negative
            favors O's, an 0 is an expected draw.
        '''
        total = 0

        # Check all ways to get three in a row.
        for a,b,c in ((0,1,2), (3,4,5), (6,7,8), # Rows
                      (0,3,6), (1,4,7), (2,5,8), # Columns
                      (0,4,8), (2,4,6)): # Diagonals
            if self.board[a] == self.board[b]  != ' ':
                total += 1 if self.board[a] == 'X' else -1
            if self.board[b] == self.board[c] != ' ':
                total += 1 if self.board[b] == 'X' else -1
            
        return total


    def successors(self):
        '''Generates a list of successors for the current state. These are all
        possible moves of the next player into blank spaces, where the "next
        player" is whichever player isn't state.player.
         
        Returns (list(GameState)): A list of all possible successor of the given 
            state for the next player.
        '''
        successorStates = []
        succesorState = None
        nextPlayer = 'O' if self.lastPlayer == 'X' else 'X'

        for i in range(len(self.board)):
            if self.board[i] == ' ':
                successorState = self.clone()
                successorState.makeMove(nextPlayer, i+1)
                successorStates.append(successorState)

        return successorStates
    

    def isMax(self):
        '''Determines if this is a "max" state, meaning a state for which Minimax
        should be maximizing over values of successors, meaning it's X's turn
        *next* (and therefore, 'O' was the last player).
         
        Returns (boolean): True if this is a max state.
        '''
        return self.lastPlayer == 'O'
    
    def getActionUtility(self, utility=None):
        '''A helper function that simply wraps a move (extracted from the given 
        state) and the utility value of this terminal state.
        
        Parameters:
            utility (float): The utility value to pair with the current state; if absent, the utility of the current state is used.

        Returns (ActionUtility): An action-utility.
        '''
        return TicTacToeActionUtility(self.move, utility if utility else self.utility())
    
    def getActionEval(self):
        '''A helper function that simply wraps a move (extracted from the given 
        state) and the evaluation value of this non-terminal state.
        
        Returns (ActionUtility): An action-utility.
        '''
        return TicTacToeActionUtility(self.move, self.eval())


    def clone(self):
        '''Returns (GameState): A deep copy of this state.'''
        return TicTacToeState(self.board.copy(), self.lastPlayer, self.move)
    



class TicTacToeActionUtility(ActionUtility):
    '''Serves as a wrapper for a tic-tac-toe move and the utility associated
    with it.
    '''

    def __init__(self, move, utility):
        '''Initializes a TicTacToeActionUtility.
        
        Parameters:
            move (int): The move (board number) associated with this utility.
            utility (float): The utility of this move.
        '''
        self.move = move 
        self.utility = utility

    def __str__(self):
        '''Returns (str): A string representation of this action-utility pair.'''
        return f"[\n\tmove: {self.move}\n\tutility: {self.utility}\n]"


class TicTacToe:
    '''Contains methods for playing a game of TicTacToe with Minimax for
    the AI player.'''

    def __init__(self, loggingDepth=0):
        '''Initializes a new game of TicTacToe.
        
        Parameters:
            loggingDepth (int): The depth to which Minimax should log its search.
        '''
        self.currentState = None
        self.minimax = Minimax()
        self.loggingDepth = loggingDepth

    def run(self):
        '''Starts a new game of TicTacToe.'''
        userMove = None
        aiMove = None
        self.currentState = TicTacToeState([' ']*9, '?', 0)
        print('Key: ')
        self.printBoard(list(range(1,10)))
        print('Game board:')
        self.printBoard(self.currentState.board)

        # Go until the game is over.
        while not self.currentState.isTerminal():
            # Human's turn.
            userMove = int(input('Your turn; enter the space # where you\'d like to put your X: '))
            while userMove < 1 or userMove > 9 or self.currentState.board[userMove-1] != ' ':
                userMove = int(input('That spot is invalid; try again: '))

            self.currentState.makeMove('X', userMove)

            print("Game board:")
            self.printBoard(self.currentState.board)

            # Check if the human won.
            if self.currentState.isTerminal():
                break

            # AI's turn.
            print('\nComputer\'s turn:')
            aiMove = self.minimax.value(self.currentState, -1, self.loggingDepth, '').move
            self.currentState.makeMove('O', aiMove)

            self.printBoard(self.currentState.board)

        print(f"Game over. Utility: {self.currentState.utility()}")


    def printBoard(self, board):
        '''Prints the given board in a 3x3 grid.
        
        Parameters:
            board (list): Should be 9 characters long, each character 
                          representing one spot on the board. Valid 
                          characters are: X, O, and ' ' (space).
        '''
        for i in range(len(board)):
            if i > 0 and i % 3 == 0:
                print('\n---+---+---')
            print(f' {board[i]} ', end='')
            if i % 3 < 2:
                print('|', end='')
        print()


def main():
    '''Runs a game of TicTacToe.'''

    USAGE = ("Usage: python tictactoe.py [-h] [loggingDepth]\n"+
            "All parameters are optional.\n"+
            "    -h: Display this help message.\n"+
            "    loggingDepth: How many levels down the Minimax tree to display info for; use 0 to turn off (default).")

    loggingDepth = 0
    if len(sys.argv) > 1:
        if sys.argv[1] == '-h':
            sys.exit(USAGE)
            
        loggingDepth = int(sys.argv[1])

    game = TicTacToe(loggingDepth)
    game.run()


if __name__ == '__main__':
    main()