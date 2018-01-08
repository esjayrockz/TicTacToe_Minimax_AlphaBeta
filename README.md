# TicTacToe_Minimax_AlphaBeta

AI Project Tic Tac Toe Game using Minimax Algorithm

A game playing algorithm using minimax with alpha-beta pruning is implemented for a 3x3 Tic-Tac-Toe game. This algorithm will consider all possible scenarios and determine its next steps based on the most optimal move available to it. It will keep playing ahead until it reaches a terminal arrangement of the board resulting in a tie, a win, or a loss. Once in a terminal state, the AI will assign a positive score for a win, a negative score for a loss, or a neutral score (0) for a tie.
At the same time, the algorithm will evaluate the moves that lead to a terminal state based on the players’ turn. It will choose the move with the maximum score when it is the AI’s turn and choose the move with the minimum score when it is the human player’s turn. Using this strategy, the algorithm will choose the best move for it ahead.

Extended to the planar n x n Tic-Tac-Toe game: When using a board higher than a dimension of 4, I am using an evaluation function instead of a utility function just to evaluate the state of the current board. This is because if we use a utility function for a 5x5 tic-tac-toe board, the number of states created is enormous and not computable by our computers.

User Interface: The program is written in Java and the graphical user interface is created in Java Swing.
