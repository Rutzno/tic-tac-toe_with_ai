# Tic-Tac-Toe with Artificial Intelligence

**Tic-Tac-Toe** game (also known _Noughts_ and _Crosses_), is a 
paper-and-pencil game for two players, _X_ and _O_, who take turns 
marking the spaces in a 3×3 grid. The player who succeeds in placing 
three of their marks in a horizontal, vertical, or diagonal row is the winner.
  
This project is an upgraded version of the project _tic-tac-toe in java_ which 
allows a human being to play against a computer in implementing three level 
of difficulty by integrating _artificial intelligence_.  
- **_easy_** : Make a random move
- **_medium_** : It uses this technique
    1. If it can win in one move (if it has two in a row), it places a third to get three in a row and win.
    2. If the opponent can win in one move, it plays the third itself to block the opponent to win.
    3. Otherwise, it makes a random move.
- **_hard_** : We implement the _Minimax_ algorithm. It's a brute force algorithm 
that maximizes the value of the own position and minimizes the value of the opponent's position. 
It's not only an algorithm for _Tic-Tac-Toe_, but for every game with two players with 
alternate move order, for example, chess. Thus, this level is an undefeated 
champion (so, it can come out with a win, or a tie score).   

There is another feature in this app: if you're tired playing the game, 
you can even launch a match between two _ai_ and watch them playing.

Since it's not yet in GUI, you might use these coordinates to play the game.

-----------------
|1 3` `2 3` `3 3|  
|1 2` `2 2` `3 2|  
|1 1` `2 1` `3 1|  
-----------------
To start the game, after _input command_: type for instance _start hard user_
which mean match between _aiPlayer (computer)_ and _human_.
