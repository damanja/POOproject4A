package Code;

import java.awt.Dimension;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class Plateau extends JFrame {
	private final int WIDTH = 500;
	private final int HEIGHT = 500;
	private final int PIXEL_SIZE = 10;
	private final int FOOD = 120;
	private final int RAND_POS = 26;
	private Serpent serp;
	private int tab[][] = new int [WIDTH][HEIGHT];
	private ArrayList<Food> f;
	
	
	public Plateau() {
   //     setPreferredSize(new Dimension(500, 500));
		
		initIHM();
		
	}
	
    private void initIHM() {
    	serp=new Serpent();
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
//        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
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