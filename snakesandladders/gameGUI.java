package snakesandladders;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.Border;

/**
 * This class include all the graphics that appeared to the game
 * @author Michalis Chaviaras
 */
public class gameGUI extends JFrame implements ActionListener {

    private JFrame about_jframe;
    private JPanel east, east1, east2, south, center;
    private JMenu menu, info;
    private JButton rolldice;
    private JMenuItem newgame, undo, redo, quit;
    private JTextArea textarea, about_textarea;
    private JMenuBar bar;
    private String names[];//the names of the players that will be read 
    private Board board;
    private GameMaster master; //It is required for the names to be read 
    private JScrollPane pane;
    private JLabel zariapouirthe;

    /**
     * Precondition:"board"!=null kai "master"!=null
     * @param "board" 
     * @param "master"
     */
    public gameGUI(Board board, GameMaster master) {
        super("Snakes And Ladders");
        this.master = master;
        setLayout(new BorderLayout());
        this.board = board;


        //we make the menu of the game 
        menu = new JMenu("Game");
        info = new JMenu("Info");

        JMenuItem about = new JMenuItem("About");
        about.addActionListener(this);

        newgame = new JMenu("New Game");

        JMenuItem player2 = new JMenuItem("2 Players");
        newgame.add(player2);
        player2.addActionListener(this);
        JMenuItem player3 = new JMenuItem("3 Players");
        player3.addActionListener(this);
        newgame.add(player3);
        JMenuItem player4 = new JMenuItem("4 Players");
        player4.addActionListener(this);
        newgame.add(player4);
        JMenuItem player5 = new JMenuItem("5 Players");
        player5.addActionListener(this);
        newgame.add(player5);


        undo = new JMenuItem("Undo");
        undo.addActionListener(this);

        redo = new JMenuItem("Redo");
        redo.addActionListener(this);

        quit = new JMenuItem("Quit");
        quit.addActionListener(this);

        menu.add(newgame);
        menu.addSeparator();
        menu.add(undo);
        menu.add(redo);
        menu.addSeparator();
        menu.add(quit);
        info.add(about);
        bar = new JMenuBar();
        bar.add(menu);
        bar.add(info);
        setJMenuBar(bar);
        bar.setVisible(true);




        //we make the east JPanel 
        east = new JPanel();
        east.setSize(300, 100);
        east.setLayout(new GridLayout(2, 1));
        east1 = new JPanel();
        east1.setLayout(new GridLayout(7, 1));
        east1.add(new JLabel("  Players Info"));

        east2 = new JPanel();
        east2.setLayout(new GridLayout(7, 1));
        rolldice = new JButton("Roll Dice");
        east2.add(rolldice);

        JLabel result = new JLabel("RESULT");
        zariapouirthe = new JLabel("");
        east2.add(result);
        east.add(east1);
        east.add(east2);
        Border blackline;
        blackline = BorderFactory.createLineBorder(Color.black);
        east1.setBorder(blackline);  //the border with black color 
        east2.setBorder(blackline);  




        //we make the south JPanel 
        south = new JPanel();
        textarea = new JTextArea("");
        textarea.setEditable(false);
        textarea.setSize(300, 300);
        Dimension tx = new Dimension();
        tx.setSize(580, 100);
//        textarea.setPreferredSize(tx);I must removed it in order to perform DEBUGGING 
        textarea.setBorder(blackline);
//        south.add(textarea);
//DEBUGGING
        JScrollPane pane = new JScrollPane();
        pane.setPreferredSize(tx);
        pane.getViewport().add(textarea);
        pane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

//DEBUGGING




        //we make the Center JPanel
        center = new JPanel();
        center.setLayout(new GridLayout(board.getrows(), board.getcolumns()));


        for (int i = board.getrows(); i >= 1; i--) {

            if (i % 2 == 0) {
                for (int j = 0; j <= board.getcolumns() - 1; j++) {
                    center.add(board.getsquare((board.getcolumns() * i) - j));
                }
            } else {
                for (int j = 1; j <= board.getcolumns(); j++) {
                    center.add(board.getsquare((i - 1) * board.getcolumns() + j));
                }
            }
        }


        for (int i = 1; i <= board.getsumofsquares(); i++) {
            board.getsquare(i).draw();
        }


        Container content = getContentPane();
        this.add(pane, BorderLayout.SOUTH);
        content.add("East", east);
        content.add("Center", center);



        setSize(750, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    /**
     * Postcondition: Displays on the textarea what the dice brought
     * @param "zaria" What the dice brought 
     */
    public void diksetinzaria(int zaria) {
        east2.remove(zariapouirthe);
        zariapouirthe = new JLabel(" " + zaria);
        east2.add(zariapouirthe);
    }

    /**
     * Precondition: "player"!=null
     * Postcondition:grafei to onoma tou paikti kai zografizei to xroma tou
     * sto ActionPanel
     * @param player o paixtis pou tha zografistei sto ActionPanel
     * @param pointer o akeraios pou antistoixei stin thesi tou pinaka tou paikti pou
     *
     */
    public void setnamesonactionpanel(Player[] players, int pointer) {
        east1.removeAll();
        east1.setLayout(new GridLayout(7, 1));
        east1.add(new JLabel("  Players Info"));
        for (int i = 0; i < players.length; i++) {
            PlayersInfoPanel pl = new PlayersInfoPanel(players[i], i, pointer);
            pl.setVisible(true);
            east1.add(pl);
//            east1.repaint();
//            east1.setVisible(true);


        }
        east1.repaint();
        east1.setVisible(true);
        validate();
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("2 Players")) {
            StartingWindow(2);
            rolldice.addActionListener(this);
            master.continuethegame(names);
        } else if (e.getActionCommand().equals("3 Players")) {
            StartingWindow(3);
            rolldice.addActionListener(this);
            master.continuethegame(names);
        } else if (e.getActionCommand().equals("4 Players")) {
            StartingWindow(4);
            rolldice.addActionListener(this);
            master.continuethegame(names);
        } else if (e.getActionCommand().equals("5 Players")) {
            StartingWindow(5);
            rolldice.addActionListener(this);
            master.continuethegame(names);
        } else if (e.getActionCommand().equals("Undo")) {
            master.undo();
        } else if (e.getActionCommand().equals("Redo")) {
            master.redo();
        } else if (e.getActionCommand().equals("Quit")) {
            System.exit(0);
        } else if (e.getActionCommand().equals("Roll Dice")) {
            master.play();
        } else if (e.getActionCommand().equals("Ok")) {
            master = new GameMaster();
            master.start();

        } else if (e.getActionCommand().equals("About")) {
            about_jframe = new JFrame();
            about_textarea = new JTextArea();
            about_textarea.append("Welcome to SnakesandLadders \n"
                    + "The author of this game is Chaviaras Michalis \n"
                    + ".It is the classic table game with the snakes and the ladders. \n"
                    + "1)Push the \"New Game\" to start a new game and select the number of players \n"
                    + "2)Push \"Undo\" and the last player who played can take back the last move \n "
                    + "3)Push \"Redo\" and the last move that undoed will be repeated \n"
                    + "4)Push \"Quit\" and you will exit from the game \n");
            about_jframe.add(about_textarea);
            about_jframe.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
            about_jframe.pack();
            about_jframe.setVisible(true);
        }

    }

    /**
     * Precondition : 5>=number_of_player>=2
     * Postcondition: It assigns the names of the players to the "names"
     * Reads the names of the players
     * @param "number_of_players" How many players the game has
     */
    public void StartingWindow(int number_of_players) {

        names = new String[number_of_players];
        for (int i = 0; i < number_of_players; i++) {
            String temp = "";
            temp = JOptionPane.showInputDialog(null, "Player" + " " + (i + 1), "Enter your name", JOptionPane.QUESTION_MESSAGE);
            if (temp == null) {
                temp = "player_name_" + (i + 1);
            } else {
                while (temp.equals("") || temp.equals(" ")) {
                    temp = JOptionPane.showInputDialog(null, "Player" + " " + (i + 1), "Enter your name", JOptionPane.QUESTION_MESSAGE);
                }
            }
            names[i] = temp;

        }

    }

    /**
     *  Precondition: "p"!=null
     *  Postcondition: displays a window in which the name of the winner is appeared 
     *  @param "p" the winner player
     */
    public void winner(Player p) {
        this.dispose();
        JFrame win = new JFrame();
        win.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JPanel win1 = new JPanel();
        win1.setLayout(new GridLayout(2, 0));
        JLabel a = new JLabel("Congratulations!! The winner is :" + p.getName());
        JButton ok = new JButton("Ok");
        win1.add(a);
        win1.add(ok);
        ok.addActionListener(this);
        win.add(win1);
        win.pack();
        win.setVisible(true);
    }

    /**
     * Precondition: "Message"!=null
     * Postcondition: Writes the "Message" to the LogPanel
     * @param "Message" the message which will be written
     */
    public void writeonlogpanel(String Message) {
        if (Message != null) {
            Calendar cal = Calendar.getInstance();
            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
            textarea.append("[" + sdf.format(cal.getTime()) + "]" + "  " + Message + "\n");
        } else {
            System.out.println("i writeonlogpanel pire orisma null");
        }
    }
}
