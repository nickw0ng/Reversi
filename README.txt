Overview: The code in our project involves the development of a two-player Reversi game with a
          graphical interface. The game is played on a grid of hexagonal cells, and each player
          has a color (black or white) with game pieces represented as discs with a black and
          white side for the visual view. However, for the textual view the hexagonal cells are
          represented by either X for black cell, O for white cell, and _ for an empty cell.

Quick start: The user starts the code by calling BasicReversi which takes in a int that represents
             the board size. In terms of invariant, it ensure the board size is greater than 5 and
             odd so that is maintains its symmetry. If these invariant conditions are met the game
             is started, the board is created and the game components are initialized.

Key components: One component of our game is the discs which is the board part on the model. In
                addition, there is a DiscPosn which shows the coordinate points in which the disc is
                located. Finally, there is a DiscType which shows the state of the disk whether it
                is shows a X, O, or _. For the model, we incorporated a ReversiModel interface that
                is the primary model for playing and has the methods that can be called while
                playing the game. We also implemented a reversiUtils class that provides method
                body's that are accessible for use and drives the application. Along with the model,
                we implemented a player interface to represent the player for the game which
                currently only contains the class for human player but in later implementations will
                incorporate a computer, ai player.

Source organization: For the code of the game pieces can be found in the Disc class the Disc
                     components can be found in either the DiscPosn class (coordinate point of the
                     Disc) and DiscType enum (the state of the disc). 

Keyboard Inputs: 'm' to move and 'p' to pass.

Changes: moved some functions from ReversiUtils to the model, and fixed z coordinates.

Changes for part 3:
    -Created mock controller for testing
    -created a model status listener and player listener for synchronous dual views
    -updated model and view classes to notify the model status listeners and player listeners
    -Fixed ReversiPanel's duplication of hexagons
    -added a first opening strategy to use as a back for other strategies if they cannot find a move
    -added a play method in the player classes, the model, and the controller
    -Made ReversiMaker class that creates a game based on command line inputs, instructions:
            -"There are 6 strategies:
              1. controlled by human player, 2. goes for the first available space, 3. goes for the move that gets the most discs,
              4. goes for the upper leftmost valid corner, 5. avoids going next to corners, 6. goes for the move that makes it hardest for the other player,
              You may start the game by:
              -typing two corresponding numbers (with a space between them on the same line)
              -Just hit enter and the 2 players will automatically become human players
              -Only entering one number will make the other automatically become a human player
              Only valid numbers typed will be chosen (1 - 6), all others will be ignored"

Working Features:
    -All strategies other than CaptureMaxStrategy and MiniMax strategies.
         (CaptureMaxStrategy would make the model do the same move twice so the invalid move message would pop up,
         but it was still fully playable)
         (MiniMax strategy also for some reason made the game end automatically)
    -The view was slightly off centered.

