
/**
 * Created by Suvajit on 02/12/17.
 */

import javax.swing.*;
import java.util.LinkedList;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import java.util.Map;
import java.util.HashMap;
import java.util.Comparator;
import java.util.Random;

public class Board {


    private static int d = Integer.parseInt(JOptionPane.showInputDialog("Enter dimension for tic tac toe board e.g. Enter 3 for a 3x3 board"));//Taking input of dimensions of board
    public static final int DIM = d;//dimension of board
    public static final int SIZE = DIM * DIM;//Size will be DIM x DIM
    public char[] ttt_board;//current set up of board
    public char turn;//current turn 'X' or 'O'
    private Map<Integer, Integer> cache = new HashMap<Integer, Integer>();//Hashmap used for caching

    public Board() {//Constructor with no parameters

        this.turn = 'X';
        ttt_board = new char[SIZE];
        for (int i = 0; i < SIZE; i++) {
            ttt_board[i] = ' ';
        }
    }

    public Board(char[] board, char turn) {//Constructor with current board and turn
        this.ttt_board = board;
        this.turn = turn;

    }

    public Board(String str) {//Constructor with current board in string
        this.ttt_board = str.toCharArray();
        this.turn = 'X';
    }

    public Board(String str, char turn) {//Constructor with current board in string and current turn
        this.ttt_board = str.toCharArray();
        this.turn = turn;
    }


    public boolean win(char turn) {//Checking whether it is a win for the current turn
        boolean isWin = false;
        for (int i = 0; i < SIZE; i += DIM) {//Checking win in rows
            isWin = isWin || win_line(turn, i, i + DIM, 1);
            if(isWin == true)
                return isWin;
        }
        for (int i = 0; i < DIM; i++) {//Checking win in columns
            isWin = isWin || win_line(turn, i, SIZE, DIM);
            if(isWin == true)
                return isWin;
        }
        isWin = isWin || win_line(turn, 0, SIZE, DIM + 1);//Checking win for major diagonal
        if(isWin == true)
            return isWin;
        isWin = isWin || win_line(turn, DIM - 1, SIZE - 1, DIM - 1);//Checking win for minor diagonal
        return isWin;
    }

    public boolean win_line(char turn, int start, int end, int step) {//Iterating throw a particular row/column/diagonal to see if it is win for turn
        for (int i = start; i < end; i += step) {
            if (ttt_board[i] != turn)
                return false;
        }

        return true;
    }

    public int blanks() {//Number of blanks left in the current board
        int total = 0;
        for (int i = 0; i < SIZE; i++) {
            if (ttt_board[i] == ' ')
                total++;
        }
        return total;
    }

    public int code() {//Returning a key for hashing
        int value = 0;
        for (int i = 0; i < SIZE; i++) {
            value = value * 3;
            if (ttt_board[i] == 'X')
                value += 1;
            else if (ttt_board[i] == 'O')
                value += 2;
        }
        return value;
    }



    public int minimax(int alpha,int beta) {//minimax method implemented using alpha beta pruning

        Integer key = code();
        Integer value = cache.get(key);
        if (value != null)//if cache(hash) found
            return value;
        if (win('X'))//if win for 'X'
            return blanks()+1;
        if (win('O'))//if win for '0'
            return -blanks()-1;
        if (blanks() == 0)//if Draw
            return 0;
        List<Integer> list = new ArrayList<>();
        if(turn == 'X') {//Code for Maximising element
            int v = -100, mm;
            for (Integer index : possibleMoves()) {
                mm = move(index).minimax(alpha,beta);
                unmove(index);//Unmoving because we just want to find out value after move and not make the move
                v = v > mm ? v : mm;
                alpha = alpha > v ? alpha : v;
                if (beta <= alpha)//alpha beta pruning
                    break;
                list.add(mm);

            }
        }
        else if(turn == 'O')//Code for Minimising element
        {
            int v = 100, mm;
            for (Integer index : possibleMoves()) {
                mm = move(index).minimax(alpha,beta);
                unmove(index);//Unmoving because we just want to find out value after move and not make the move
                v = v < mm ? v : mm;
                alpha = alpha < v ? alpha : v;
                if (beta <= alpha)//alpha beta pruning
                    break;
                list.add(mm);
            }
        }

        value = turn == 'X' ? Collections.max(list) : Collections.min(list);//Returning max if turn = 'X' else returning min of list
        cache.put(key, value);//storing the hash
        return value;
    }


    public int evaluation() { //calculates value for evaluation function(only when dimension>4)

        int  sum_x =0, sum_o =0; int num_x,num_o;
        for (int i = 0; i < SIZE; i += DIM) {
            num_x = 0; num_o = 0;
            for (int j = i; j < i + DIM; j++) {
                if (ttt_board[j] == 'X')
                    num_x++;
                else if (ttt_board[j] == 'O')
                    num_o++;

            }
            if(num_x>0 && num_o>0)
            {
                sum_x += 0;
                sum_o += 0;
            }
            else
            if(num_x>0 && num_o==0){
                sum_x += (num_x * 2)-1;
            }
            else
            if(num_x==0 && num_o>0){
                sum_o += (num_o * 2)-1;
            }
        }
        for (int i = 0; i < DIM; i++) {
            num_x = 0; num_o = 0;
            for (int j = i; j < SIZE; j=j+DIM) {
                if (ttt_board[j] == 'X')
                    num_x++;
                else if (ttt_board[j] == 'O')
                    num_o++;

            }
            if(num_x>0 && num_o>0)
            {
                sum_x += 0;
                sum_o += 0;
            }
            else
            if(num_x>0 && num_o==0){
                sum_x += (num_x * 2)-1;
            }
            else
            if(num_x==0 && num_o>0){
                sum_o += (num_o * 2)-1;
            }
        }

        num_x = 0; num_o = 0;
        for (int j = 0; j < SIZE; j+=DIM+1) {
            if (ttt_board[j] == 'X')
                num_x++;
            else if (ttt_board[j] == 'O')
                num_o++;
        }
        if(num_x>0 && num_o>0)
        {
            sum_x += 0;
            sum_o += 0;
        }
        else
        if(num_x>0 && num_o==0){
            sum_x += (num_x * 2)-1;
        }
        else
        if(num_x==0 && num_o>0){
            sum_o += (num_o * 2)-1;
        }
        num_x = 0; num_o = 0;
        for (int j = DIM-1; j < SIZE-1; j+=DIM-1) {
            if (ttt_board[j] == 'X')
                num_x++;
            else if (ttt_board[j] == 'O')
                num_o++;
        }
        if(num_x>0 && num_o>0)
        {
            sum_x += 0;
            sum_o += 0;
        }
        else
        if(num_x>0 && num_o==0){
            sum_x += (num_x * 2)-1;
        }
        else
        if(num_x==0 && num_o>0){
            sum_o += (num_o * 2)-1;
        }
        return sum_x-sum_o; // returning value of evaluation function
    }

    public int eval_function(int depth) {//if dimension is greater than 4, use evaluation function instead of utility function

        if (win('X'))
            return blanks()+1;
        if (win('O'))
            return -blanks()-1;
        if (blanks() == 0)
            return 0;
        if (depth == 0 )
            return evaluation();
        int max_index=possibleMoves().get(0);
        int min_index=possibleMoves().get(0);
        if(turn == 'X') {
            int max=0,mm;
            for (Integer index : possibleMoves()) {
                mm = move(index).eval_function(depth-1);
                unmove(index);
                if(mm>max)//storing max value of evaluation function
                {
                    max = mm;
                    max_index = index;
                }

            }
        }
        else if(turn == 'O')
        {
            int min=0,mm;
            for (Integer index : possibleMoves()) {
                mm = move(index).eval_function(depth-1);
                unmove(index);
                if(mm<min)//storing minimum value of evaluation function
                {
                    min = mm;
                    min_index = index;
                }

            }
        }


        return turn == 'X' ? max_index : min_index;//returning corresponding index
    }


    public int bestMove() { //Finding best move for AI

        if (DIM > 4) {

                return eval_function(1);//if dimension is greater than 4, use evaluation function instead of utility function

        }
        Comparator<Integer> cmp = new Comparator<Integer>() { // Using Comparator for finding best move for AI according to minimax value returned
            public int compare(Integer first, Integer second) {
                int a = move(first).minimax(-100,100);
                unmove(first);
                int b = move(second).minimax(-100,100);
                unmove(second);
                return a - b;
            }

        };
        List<Integer> list = possibleMoves();
        return turn == 'X' ? Collections.max(list, cmp): Collections.min(list, cmp);//returning best index
    }


    @Override
    public String toString() {
        return new String(ttt_board);
    }//Overriding toString method

    public Board move(int index){//making a move on the board

        ttt_board[index] = turn;
        turn = turn == 'X' ? 'O':'X';
        return this;

    }

    public Board unmove(int index){//making a unmove on the board because we just want to find out value after move and not make the move
        ttt_board[index] = ' ';
        turn = turn == 'X' ? 'O':'X';
        return this;

    }


    public List<Integer> possibleMoves(){ // Checking for list of possible moves available to the UI
        List<Integer> list = new ArrayList<>();
        for(int i=0;i<ttt_board.length;i++)
        {
            if(ttt_board[i]==' ')
                list.add(i);
        }

        return list;

    }

    public boolean isGameEnd(){//Checking if game is over
        return win('X') || win('O') || blanks() == 0;
    }


}
