/**
 * Created by Suvajit on 03/12/17.
 */
import java.awt.Color;
import java.awt.Font;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.*;



public class GameUI extends JFrame {//Class for Graphical Interface

    Board board = new Board(); //Create new Game by creating an object of the Board class
    //JFrame frame = new JFrame("TicTacToe");
    //frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    final JButton[] buttons = new JButton[Board.SIZE];//Creating an array for buttons of tic tac toe board

    public GameUI(){//Constructor for GameUI

        //frame.setLayout(new GridLayout(Board.DIM,Board.DIM));

        setLayout(new GridLayout(Board.DIM,Board.DIM));
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        //Setting Layout
        for(int i=0;i<Board.SIZE;i++){//Iterating through all buttons
            final JButton button = createButton();
            buttons[i] = button;//Assigning the button to the array
            //System.out.println(i);

            final int index = i;
            //System.out.println(index);

            button.addMouseListener(new MouseAdapter() {//Adding MouseListener to wait for the event of pressing mouse


                @Override
                public void mousePressed(MouseEvent e) {
                    if(board.ttt_board[index]==' ')
                    {
                        button.setText("" + board.turn);//Setting Turn text on button
                        //System.out.println(index);
                        board.move(index);//Making the move
                        if(!board.isGameEnd())//if the game is not over, let AI proceed
                        {
                            int best = board.bestMove(); //finding best move for AI
                            buttons[best].setText("" + board.turn);//Setting Turn text on button where best move has been made
                            board.move(best); //making the move
                        }
                        if(board.isGameEnd())//If the game is over, display respective message

                        {
                            String message = "";
                            if(board.win('X'))
                                message = "You won!" ;
                            else
                            if(board.win('O'))
                                message = "Computer won!" ;
                            else
                                message = "Draw";
                            JOptionPane.showMessageDialog(null, message);
                            new GameUI();

                        }}
                }
            });
        }
        pack();//packing all attributes for UI
        setVisible(true);//Displaying the UI
    }

    private JButton createButton(){//Method for Creating Complete UI
        JButton button = new JButton();//Creating Complete UI
        button.setPreferredSize(new Dimension(100,100));//Setting Size of UI
        button.setBackground(Color.BLACK);//Setting Color of background for UI
        button.setOpaque(true);//Setting Opacity as true
        button.setFont(new Font(null, Font.PLAIN, 80 ));//Setting font size of buttons
        add(button);
        return button;
    }

    public static void main(String[] args) {//main function for the program
        //System.out.println("Enter dimension for tic tac toe board e.g. 3 for a 3x3 board");
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new GameUI();

            }
        });
    }
}
