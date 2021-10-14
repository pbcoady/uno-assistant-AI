# UNO Assistant AI Tool
# IMGD4100 - TermProject
## Esteban Aranda, Coady Parker, Treksh Marwaha

For convenience, the source code is attached. Do try running the project to see it in action for yourself!

### Running the program
Open the project on IntelliJ, locate 'StartGame.java' and run its main function. 
This will pop up the IDE's terminal which you can use to type in the inputs of the game.
The terminal will display the program's instructions and ask the user to input
the starting player, the user's starting hand, and the starting card on the discard pile.
After that, the match begins and one can input the action of each player at every turn.
Every turn, the number of cards each player has is printed along the card at the top of
the discard pile.
When it is the user's turn, the program suggests the most optimal play given the user's hand.
There are 4 commands that represent in-game actions. They are 'value-color' or 'color-value'
which represents a card has been played, 'pass' when a player does nothing, 'draw' followed
by number to represent the number of cards drawn by an opponent, 'draw' followed by card(s)
to represent the cards you drew.
In addition, the 'help' command prints the instructions again and the 'exit' command
terminates the program.
When a player wins the game the program recognizes it immediately and prints out the 
winning player.

### External sources
(The UNO strategy we ended up implementing for our suggestions)
https://boardgames.stackexchange.com/questions/13162/what-specific-strategies-have-the-highest-winloss-ratio-in-uno
(The rules of UNO we used as reference for how the game is supposed to be played)
https://service.mattel.com/instruction_sheets/42001pr.pdf
(A similar project that created a simulation environment and a more robust AI using deep learning that we used as inspiration)
http://robotics.cs.tamu.edu/dshell/cs420/uno/introduction.htm

### Video
https://drive.google.com/open?id=13c8DlmesADxNE7DgTuzUffeXjBeBfRS_
