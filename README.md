# TicTacToe

This Kata has been developed using TDD, so minimum code has been implemented 
before including the tests that will allow to verify the business logic is properly developed.

## Rules:

The following use cases has been implemented and tested:

- X always goes first.
- Players cannot play on a played position.
- Players alternate placing X’s and O’s on the board until either: 
   - One player has three in a row, horizontally, vertically or diagonally
   - All nine squares are filled.
- If a player is able to draw three X’s or three O’s in a row, that player wins.
- If all nine squares are filled and neither player has three in a row, the game is a draw.

## Architecture 

This code uses MVVM architecture. The `Activity` needs to observe below `LiveData` in order to be aware of the changes:

- **GameState**: It can be one of the following values:
    - Initial: It shows an empty board to init the game.
    - Playing: To update board with every turn
    - Finished: All the cells are blocked to avoid new clicks.
- **Result**: It's triggered when the game is over to inform about the result of the game, and the winner in case it applies.
It's been implemented following the `event wrapper pattern` as applied in Android Architecture Samples and described [here](https://medium.com/androiddevelopers/livedata-with-snackbar-navigation-and-other-events-the-singleliveevent-case-ac2622673150) 


## UI
For sake of simplicity the User Interface consists on a `GridLayout` filled with fixed height/width cells. Since those values are saved in `dimens` resources,
they could be easily adapted for different device sizes. Anyway, the best solution would be to calculate the specific size at running time.

The solution includes a custom view (`CellView`) that extends from ImageView and adds a index value allowing to easily identify the position of
each cell into the parent grid (Index 0 to 8: From left to right - From top to bottom)

## Demo
![phone1](demo/tictactoe_bnp.gif)
