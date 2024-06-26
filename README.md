#  Reversi Game

## Overview
This project involves developing a two-player Reversi game with a graphical hexagonal grid interface.
Players use black and white discs on the visual board, represented textually as X for black cells, 
O for white cells, and _ for empty cells

## Quick Start
To start the game, call BasicReversi with an integer representing the board size. The board size must be greater than 5 and odd to maintain symmetry. If these conditions are met, the game initializes with the board and game components ready

## Key Components
The game revolves around discs as the main board component. Additionally, DiscPosn represents
the coordinate points of discs, and DiscType indicates whether a cell is represented by X, O, or _. The ReversiModel interface drives gameplay functionality, supported by ReversiUtils for application-wide methods. Player representation includes human players; future updates may include AI players.

## Source Organization
Game pieces are implemented in the Disc class, with DiscPosn managing disc coordinates and DiscType using enums to track disc states.

## Keyboard Inputs
- Press 'm' to move.
- Press 'p' to pass.

## Working Features
- All strategies operational except CaptureMaxStrategy and MiniMax.
- View alignment slightly adjusted for better display.

## Changes for Part 3:
- Introduced mock controller for testing.
- Added model status and player listeners for synchronous dual views.
- Updated model and view classes to notify listeners.
- Fixed ReversiPanel hexagon duplication.
- Implemented opening strategies for gameplay.
- Added play method in player, model, and controller classes.
- Introduced ReversiMaker for game creation from command line inputs.