package package1;

import javax.swing.*;

import static java.lang.Integer.parseInt;

/*****************************************************************
 Author: Kevin Rufino
 Version: i dont even know anymore xD
 *****************************************************************/
public class SuperTicTacToe {

    private static int size;
    private static int timeLimit;
    private static SuperTicTacToeGame game;

    public static void main (String[] args)
    {
        JFrame frame = new JFrame ("Super Tic Tac Toe!");
        frame.setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);

        SuperTicTacToePanel panel = new SuperTicTacToePanel();
        frame.getContentPane().add(panel);

        frame.setSize(700,700);
        frame.setVisible(true);
    }


}
