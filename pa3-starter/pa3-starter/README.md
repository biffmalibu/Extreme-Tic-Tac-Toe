# Adversarial Search

## Directory contents

    java/ -- A Java implementation of Tic-Tac-Toe, ExtremeTicTacToe and Minimax.
        bin/ -- initially empty; after compilation, this contains all of the .class
                files.
        src/ -- contains the Java source files.
    python/ -- A python implementation of Tic-Tac-Toe and Minimax; contains several Python files.

## Building Java Codebase

First, change into the `java` subdirectory.

### Bash-like shells
If using a bash-like shell (GitBash, zsh, etc.), do:

    javac -d bin src/*.java

The above command will work even if you add new .java files, as long as they
aren't in subdirectories.

### Windows w/ PowerShell or Command Prompt
If using PowerShell or Command Prompt, do:

    javac -d bin src/TicTacToe.java src/Minimax.java

Note that for the latter, you'll need to augment the list of .java files if you
add new ones.

## Running Tic-Tac-Toe

### Python
Change into the `python` subdirectory, then play by doing:

    python3 tictactoe.py

Use the `-h` flag to see the usage statement.

    python3 tictactoe.py -h


### Java
After you've compiled, you can play a game of tic-tac-toe by doing the following
from the `java` subdirectory:

    java -cp bin TicTacToe

You can play a game of Extreme tic-tac-toe by doing the following
from the `java` subdirectory:

    java -cp bin ExtremeTicTacToe

You can pit two AI's together to play Extreme TicTacToe with AIvsAI
from the `java` subdirectory:

    java -cp bin AIvsAI -1 <difficulty> -2 <difficulty>

Use the `-h` flag to see the usage.

    java -cp bin TicTacToe -h
    java -cp bin ExtremeTicTacToe -h
    java -cp bin AIvsAI -h

Follow the instructions.


# Logging
The Mimimax implementaion has a logging feature that allows you to see what's
happening down to a pre-specified depth. To use this, just specify the depth
you'd like to see logging turned on for when you run the program by -l (depth):

    java -cp bin TicTacToe -l 5
    java -cp bin ExtremeTicTacToe -l 5

or

    python3 tictactoe.py 5

This will log down to ply 5 of the Minimax search tree.

# Alpha Beta Pruning

The Minimax implementation has an alpha beta pruing feature that can be enabled 
or disabled. to use alphabeta pruning, simply add -a to the command line arguments:

    java -cp bin TicTacToe -a
    java -cp bin ExtremeTicTacToe -a

# Depth Limited Minimax
The Minimax implementation can search down to a specified depth. this can be
enabled or disabled. To enable depth limited minimax add -d (depth)

    java -cp bin TicTacToe -d 5
    java -cp bin ExtremeTicTacToe -d 5

This will limit minimax search to a depth of 5.

# Profiles
The ExtremeTicTacToe implementation allows for the use of pre-specified difficulty profiles
to use instead of enabling depth limiting or ab pruning manually. Choose between 
beginner, intermediate, and advanced. -a and -d parameters are ignored. Use -p (profile)

    java -cp bin ExtremeTicTacToe -p advanced

This will set the computers difficulty to advanced.

# Evaluation function

The evaluation function is used to analyze non-terminal states. Using the 4x4
ExtremeTicTacToe grid, we search for 2 spots taken of the same letter, and use 
that to increment or decrement the total by 1. While we are not checking if 3/4 
spots needed to win are taken, checking for 2 should be effective still as it 
implies we are closer to a win. (+1 for X, -1 for O).

