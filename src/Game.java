import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

public class Game extends JFrame implements ActionListener {
    private JPanel panel;
    private JButton pos0, pos1, pos2, pos3, pos4, pos5, pos6, pos7, pos8;
    private int counter;
    private String turn;
    private String[] board;
    private boolean won = false;
    private JDialog newGame;
    private ArrayList<JButton> buttons = new ArrayList<>();


    public Game(){
        super("Tic Tac Toe");
        setSize(400,400);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setup();
    }

    private void setup(){
        panel = new JPanel(new GridLayout(3,3));
        pos0 = new JButton("1");
        buttons.add(pos0);
        pos1 = new JButton("2");
        buttons.add(pos1);
        pos2 = new JButton("3");
        buttons.add(pos2);
        pos3 = new JButton("4");
        buttons.add(pos3);
        pos4 = new JButton("5");
        buttons.add(pos4);
        pos5 = new JButton("6");
        buttons.add(pos5);
        pos6 = new JButton("7");
        buttons.add(pos6);
        pos7 = new JButton("8");
        buttons.add(pos7);
        pos8 = new JButton("9");
        buttons.add(pos8);

        setContentPane(panel);

        for(JButton btn : buttons){
            panel.add(btn);
            btn.addActionListener(this);
        }

        turn = "X";
        board = new String[9];
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JButton temp;

        if(e.getSource() instanceof JButton){
            temp = (JButton) e.getSource();
        }
        else{
            return;
        }

        board[Integer.parseInt(temp.getText()) - 1] = turn;
        temp.setText(turn);
        temp.setEnabled(false);
        check();
        if(counter % 2 == 0){
            turn = "O";
            temp.setBackground(Color.decode("#0057B7"));
            temp.setForeground(Color.decode("#FFFFFF"));
        }
        else{
            turn = "X";
            temp.setBackground(Color.decode("#ffd700"));
            temp.setForeground(Color.decode("#FFFFFF"));
        }
        counter++;

    }

    private void check() {
        for (int i = 0; i < 8; i++) {
            String match = switch (i) {
                case 0 -> board[0] + board[1] + board[2];
                case 1 -> board[3] + board[4] + board[5];
                case 2 -> board[6] + board[7] + board[8];
                case 3 -> board[0] + board[3] + board[6];
                case 4 -> board[1] + board[4] + board[7];
                case 5 -> board[2] + board[5] + board[8];
                case 6 -> board[0] + board[4] + board[8];
                case 7 -> board[2] + board[4] + board[6];
                default -> null;
            };

            if (match.equals("XXX")) {
                won = true;
                wonOrDraw();
                break;
            }

            else if (match.equals("OOO")) {
                won = true;
                wonOrDraw();
                break;
            }

        }
        if(counter == 8 && !won){
            wonOrDraw();
        }

    }

    private void wonOrDraw() {
        if(won){
            newGame = new JDialog(this, "Game has been won by " + turn);
        }
        else{
            newGame = new JDialog(this, "DRAW");
        }

        newGame.setLayout(new FlowLayout());

        JLabel res = new JLabel("RESET THE GAME?");
        JButton yes = new JButton("YES");
        JButton no = new JButton("NO");

        newGame.add(res);
        newGame.add(yes);
        newGame.add(no);
        newGame.setSize(300, 300);
        newGame.setVisible(true);

        newGame.addWindowListener(new WindowAdapter() {
            @Override public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });

        yes.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                reset();
            }
        });

        no.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
    }

    private void reset(){

        newGame.dispose();
        counter = 0;

        for(JButton btn : buttons){
            btn.setText(String.valueOf(++counter));
            btn.setEnabled(true);
            btn.setBackground(null);
            btn.setForeground(null);
        }

        board = new String[9];
        won = false;
        counter = 0;
        turn = "X";
    }

    public static void main(String[] args) {
        new Game();
    }

}
