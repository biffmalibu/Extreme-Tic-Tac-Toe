# Adversarial Search

## Directory contents

    java/ -- A Java implementation of Tic-Tac-Toe and Minimax.
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

### Java
After you've compiled, you can play a game of tic-tac-toe by doing the following
from the `java` subdirectory:

    java -cp bin TicTacToe

Use the `-h` flag to see the usage.

    java -cp bin TicTacToe -h

Follow the instructions.

### Python
Change into the `python` subdirectory, then play by doing:

    python3 tictactoe.py

Use the `-h` flag to see the usage statement.

    python3 tictactoe.py -h


# Logging
The Mimimax implementaion has a logging feature that allows you to see what's
happening down to a pre-specified depth. To use this, just specify the depth
you'd like to see logging turned on for when you run the program:

    java -cp bin TicTacToe 5

or

    python3 tictactoe.py 5

This will log down to ply 5 of the Minimax search tree.
