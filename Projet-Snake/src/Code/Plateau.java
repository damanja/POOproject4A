package Code;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class Plateau extends JFrame {
	private final int WIDTH = 500;
	private final int HEIGHT = 500;
	private final int PIXEL_SIZE = 10;
	private final int FOOD = 120;
	private final int RAND_POS = 26;
	private Serpent serp;
	private int tab[][] = new int [Globals.B_WIDTH][Globals.B_HEIGHT];
	private ArrayList<Food> f;
	private int difficulty = 1;
	private int nbrFood;
	
	public Plateau(int difficulty, int nbrFood) {
		
//		initIHM();
	  	serp=new Serpent(difficulty,nbrFood,this);
	    add(serp);
	    pack();
	  
        setResizable(true);
        
        setTitle("Snake");

        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                int reponse = JOptionPane.showConfirmDialog(serp,
                        "Voulez-vous quitter l'application",
                        "Confirmation",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE);
                if(reponse == JOptionPane.YES_OPTION ){
                    System.exit(0);
                }
            }
        });
/*        
        JPanel cp = new JPanel();
        cp.setLayout(new BorderLayout());
        cp.add(serp,BorderLayout.CENTER);
        
        JPanel pan = new JPanel();
        pan.setLayout(new FlowLayout());
        JLabel jtf = new JLabel("Score");
        pan.add(jtf);
        JButton score = new JButton();
        score.setBackground(Color.white);
        score.setText(String.valueOf(serp.getFoodManger())); // Valeur total de repas manger
        pan.add(score);
        
        JLabel objectif = new JLabel("Objectif");
        pan.add(objectif);
        JButton goal = new JButton();
        goal.setBackground(Color.white);
        goal.setText(String.valueOf(10));
        pan.add(goal);
        
        cp.add(pan,BorderLayout.SOUTH);
        setContentPane(cp);
        pack();*/
	}
	
    private void initIHM() {
    	serp=new Serpent(difficulty,nbrFood,this);
        add(serp);
               
        setResizable(true);
        pack();
        
        setTitle("Snake");

        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                int reponse = JOptionPane.showConfirmDialog(serp,
                        "Voulez-vous quitter l'application",
                        "Confirmation",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE);
                if(reponse == JOptionPane.YES_OPTION ){
                    System.exit(0);
                }
            }
        });


        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
    }

	
	public void enlever_food(int x,int y) {
		f.remove(new FoodNegative(x,y));
	}
	
	public int get_value(int x, int y) {
		return tab[x][y];
	}
	
	public void set_value(int x, int y,int val) {
		tab[x][y] = val;
	}
}