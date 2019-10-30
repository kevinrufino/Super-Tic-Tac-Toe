package package1;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import static java.lang.Integer.parseInt;

public class SuperTicTacToePanel extends JPanel {

    /** board with buttons*/
    private JButton[][] board;
    /** board with cell information*/
    private Cell[][] iBoard;

    /** X picture*/
    private ImageIcon xIcon;
    /** O picture*/
    private ImageIcon oIcon;
    /** empty picture*/
    private ImageIcon emptyIcon;

    /** instantiates game object*/
    private SuperTicTacToeGame game;

    /** reset button*/
    private JButton reset =  new JButton("reset");
    /** undo button*/
    private JButton undo =  new JButton("UNDO");

    /*****************************************************************
     Default constructor asking user to enter board size and enter
     who they wish to be the first player
     *****************************************************************/
    public SuperTicTacToePanel() {
        //Board size
        String input = JOptionPane.showInputDialog("Enter Board Size: ");
        if (parseInt(input) < 3 || parseInt(input) > 14){
            input = JOptionPane.showInputDialog("Board size must be\n> 2 & < 15");
        }
        if (parseInt(input) < 3 || parseInt(input) > 14){
            input = JOptionPane.showInputDialog("Board size must be\n> 2 & < 15");
        }

        game = new SuperTicTacToeGame(parseInt(input));

        xIcon = new ImageIcon ("./src/package1/x.jpg");
        oIcon = new ImageIcon ("./src/package1/o.jpg");
        emptyIcon = new ImageIcon ("./src/package1/empty.jpg");

        JPanel bottom = new JPanel();
        JPanel center = new JPanel();
        JPanel btn =  new JPanel();

        // create game, listeners
        ButtonListener listener = new ButtonListener();
        reset.addActionListener(listener);
        undo.addActionListener(listener);

        center.setLayout(new GridLayout(game.getSize(),game.getSize(),2,2));
        Dimension temp = new Dimension(60,60);
        board = new JButton[game.getSize()][game.getSize()];

        for (int row = 0; row < game.getSize(); row++)
            for (int col = 0; col < game.getSize(); col++) {

                Border thickBorder = new LineBorder(Color.black, 2);

                board[row][col] = new JButton ("", emptyIcon);
                board[row][col].setPreferredSize(temp);
                board[row][col].setBorder(thickBorder);

                board[row][col].addActionListener(listener);
                center.add(board[row][col]);
            }

        //First turn
        String[] buttons = { "YOU", "AI"};

        int rc = JOptionPane.showOptionDialog(null, "Who gets the first Turn?", "First Turn",
                JOptionPane.INFORMATION_MESSAGE, 0, null, buttons, buttons[1]);
        if(rc == 0){
            game.xStarts();
            displayBoard();
        }
        else {
            game.oStarts();
            displayBoard();
        }


        displayBoard();


        // add all to contentPane
        add (reset, BorderLayout.NORTH);
        add (undo, BorderLayout.NORTH);
        add (center, BorderLayout.CENTER);
        add (bottom, BorderLayout.SOUTH);

    }

    /*****************************************************************
     Method checking for all values of every cell and updating board
     and icons to current state
     *****************************************************************/
    private void displayBoard() {
        iBoard = game.getBoard ();

        //checks all cells and sets them to proper icons
        for (int r = 0; r < game.getSize(); r++){
            for (int c = 0; c < game.getSize(); c++) {

                board[r][c].setIcon(emptyIcon);
                if (iBoard[r][c] == Cell.O)
                    board[r][c].setIcon(oIcon);

                if (iBoard[r][c] == Cell.X)
                    board[r][c].setIcon(xIcon);
            }
        }
    }

    private class ButtonListener implements ActionListener {

        /*****************************************************************
         Method checking what buttons were clicked and where
         *****************************************************************/
        public void actionPerformed(ActionEvent e) {
            for (int r = 0; r < game.getSize(); r++){
                for (int c = 0; c < game.getSize(); c++) {
                    if (board[r][c] == e.getSource()) {
                        game.select(r, c);
                        displayBoard();
                    }
                }
            }

            displayBoard();

            //reset button
            if(e.getSource() == reset){
                game.reset();
                displayBoard();
            }

            //undo button
            if (e.getSource() == undo){
                game.undo();
                displayBoard();
            }

            //GameStatus messages
            if (game.getGameStatus() == GameStatus.X_WON) {
                JOptionPane.showMessageDialog(null, "X won and O lost!\n The game will reset");
                game.reset();
                displayBoard();
            }
            if (game.getGameStatus() == GameStatus.O_WON) {
                JOptionPane.showMessageDialog(null, "O won and X lost!\n The game will reset");
                game.reset();
                displayBoard();
            }
            if (game.getGameStatus() == GameStatus.CATS) {
                JOptionPane.showMessageDialog(null, "No one won!\n The game will reset");
                game.reset();
                displayBoard();
            }
        }
    }
}