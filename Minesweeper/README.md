I made this AI just for fun the summer before my senior year. I really planned to work on it much more but I started doing a lot mroe stuff for Roboboat instead. This will probably be my last project until roboboat is over because that is taking up all of my free time right now.
This is both the Minesweeper AI and the Minesweeper Player version

To run the AI, run initMinesweeperWithAI.java
To run the Player version, run initMinesweeper.java

I never finished AIv2, so only AIv1 is implemented

I have never to this day looked up how an actual minesweeeper AI works. This simple logic was derived from myself.

AIv1 works by checking every cell on the board and checking these two conditions:
1. If a cell is revealed and the number of unveiled neighbor cells is equal to the number on the cell, then all remaining neighbor cells must be bombs
2. If a cell is revealed and the number of neighbor cells that are bombs is equal to the number on the cell, then all remaining neighbor cells must be clear

If there is no guaranteed safe cell (based on these two rules), then the AI will pick a cell randomly

These two rules work fairly well, but it misses a lot of edge cases

What I learned: I didn't actually learn anything new. It uses the same structure as my chess AI and snake AI where the game is one file and the AI is another.

Problems: It doesn't always work.