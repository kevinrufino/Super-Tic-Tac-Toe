package package1;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

public class SuperTicTacToeGame {
    /** The array that keeps track of what goes in there*/
    public Cell[][] board;
    /** The enumerator keeps who wins and loses*/
    private GameStatus status;
    /** This keeps track of whose turn it is*/
    private Cell turn;
    /** This is the board size and win length*/
    private int bSize;
    /** This is the list that keeps track of prior moves*/
    private ArrayList<Point> backup = new ArrayList<Point>();

    /*****************************************************************
     Constructor changes the game status, and sets board size and makes board
     @param val the length of the board size
     *****************************************************************/
    public SuperTicTacToeGame(int val) {
        bSize = val;
        status = GameStatus.IN_PROGRESS;
        board = new Cell[getSize()][getSize()];
        reset();
    }

    /*****************************************************************
     Accessor method that returns new board
     @return new board
     *****************************************************************/
    public Cell[][] getBoard() {
        return board;
    }

    /*****************************************************************
     Mutator method taking button clicked and setting cell, adding
     moves to list, and checks if it's AI's turn
     *****************************************************************/
    public void select(int row, int col) {
        //Checks if selected isn't empty
        if (board[row][col] != Cell.EMPTY) {
            return;
        }

        //undo list
        Point p = new Point(row,col);
        backup.add(p);

        board[row][col] = turn;
        turn = (turn == Cell.O) ? Cell.X : Cell.O;

        //AI
        if (turn == Cell.O) {
            AI();
        }

        status = isWinner();
    }

    /*****************************************************************
     Method controlling AI
     *****************************************************************/
    public void AI (){
        //turn = Cell.O;

        Random rand = new Random();
        int randR = rand.nextInt(getSize());
        int randC = rand.nextInt(getSize());

        //reruns method until cell is selected
        if (board[randR][randC] != Cell.EMPTY) {
            AI();

        } else {
            board[randR][randC] = turn;;
            Point v = new Point(randR,randC);
            backup.add(v);
        }

        turn = (turn == Cell.O) ? Cell.X : Cell.O;
        status = isWinner();
    }

    /*****************************************************************
     Method setting first turn to player
     *****************************************************************/
    public void xStarts (){
        turn = Cell.X;
    }

    /*****************************************************************
     Method setting first turn to AI
     *****************************************************************/
    public void oStarts(){
        turn = Cell.O;
        AI();
    }

    /*****************************************************************
     Mutator method setting board set to parameter
     *****************************************************************/
    public void setSize(int size){
        bSize = size;
    }

    /*****************************************************************
     Accesor method returning current board size
     @return board size
     *****************************************************************/
    public int getSize(){
        return bSize;
    }

    /*****************************************************************
     Method undoing last move and updating backup list
     *****************************************************************/
    public void undo(){
        try {
                int curPos = backup.size() - 1;
                Point prevPos = backup.get(curPos);
                board[prevPos.x][prevPos.y] = Cell.EMPTY;
                backup.remove(curPos);
                turn = (turn == Cell.O) ? Cell.X : Cell.O;
                backup.trimToSize();
        }
        catch (ArrayIndexOutOfBoundsException e) {
            e.getMessage();
        }

    }

    /*****************************************************************
     Method checking for all conditions of who wins the game or if
     any of them can win at all
     @return GameStatus of game
     *****************************************************************/
    private GameStatus isWinner() {
        int counter = 0;

        /* X WINS */
        //Horizontal X
        for (int r = 0; r < getSize(); r++) {
            counter = 0;
            for (int c = 0; c < getSize(); c++) {
                if (board[r][c] == Cell.X) {
                    counter++;
                }
            }
            if (counter == getSize()){
                return GameStatus.X_WON;
            }
        }

        //Vertical X
        for (int c = 0; c < getSize(); c++) {
            counter = 0;
            for (int r = 0; r < getSize(); r++) {
                if (board[r][c] == Cell.X) {
                    counter++;
                }
            }
            if (counter == getSize()){
                return GameStatus.X_WON;
            }
        }

        //Diagonal X
        for (int i = 0; i < getSize(); i++){
            if (board[i][i] == Cell.X){
                counter ++;
            }

            if (counter == getSize()){
                counter = 0;
                return GameStatus.X_WON;
            }
        }

//        Inverse Diagonal X
//        for (int r = getSize()-1, c = 0; c < getSize() && r > 0; r--, c++){
//            if (board[r][c] == Cell.X){
//                counter ++;
//            }
//
//            if (counter == getSize()){
//                counter = 0;
//                return GameStatus.X_WON;
//            }
//        }

        /* O WINS*/
        //Horizontal O
        for (int r = 0; r < getSize(); r++) {
            counter = 0;
            for (int c = 0; c < getSize(); c++) {
                if (board[r][c] == Cell.O) {
                    counter++;
                }
            }
            if (counter == getSize()){
                return GameStatus.O_WON;
            }
        }

        //Vertical 0
        for (int c = 0; c < getSize(); c++) {
            counter = 0;
            for (int r = 0; r < getSize(); r++) {
                if (board[r][c] == Cell.O) {
                    counter++;
                }
            }
            if (counter == getSize()){
                return GameStatus.O_WON;
            }
        }

        //Diagonal O
        for (int i = 0; i < getSize(); i++){
            if (board[i][i] == Cell.O){
                counter ++;
            }

            if (counter == getSize()){
                counter = 0;
                return GameStatus.O_WON;
            }
        }

        //Inverse Diagonal O
//        for (int r = getSize()-1, c = 0; c < getSize() && r > 0; r--, c++){
//            if (board[r][c] == Cell.O){
//                counter ++;
//            }
//
//            if (counter == getSize()){
//                counter = 0;
//                return GameStatus.O_WON;
//            }
//        }

        /* NO ONE WINS */
        for (int r = 0; r < getSize(); r++) {
            for (int c = 0; c < getSize(); c++) {
                if (board[r][c] != Cell.EMPTY) {
                    counter++;
                }
                if (counter == getSize() * getSize()) {
                    counter = 0;
                    return GameStatus.CATS;
                }
            }
        }

        return GameStatus.IN_PROGRESS;
    }

    /*****************************************************************
     Acessor method returning game status
     @return status
     *****************************************************************/
    public GameStatus getGameStatus() {
        return status;
    }

    /*****************************************************************
     Method resseting all board cells to empty and setting turn to
     player
     *****************************************************************/
    public void reset() {
        //sets all cells to empty
        for (int r = 0; r < getSize(); r++)
            for (int c = 0; c < getSize(); c++)
                board[r][c] = Cell.EMPTY;

        turn = Cell.X;
    }
}