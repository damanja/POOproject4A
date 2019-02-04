package Code;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;

public class Serpent extends JPanel implements ActionListener{
	private int taille;
	private int tailleMin;
	private int nbrDeplacement = 0;
	private int seconde=0;
	private boolean inGame=true;
	private int nbrFoodManger=0;
	private int PIXEL = 10;
	private JButton score;
	
	private ArrayList<Food> f = new ArrayList<>();
	
    private boolean leftDirection = false;
    private boolean rightDirection = true;
    private boolean upDirection = false;
    private boolean downDirection = false;
    
    private Image corps;
    private Image food;
    private Image tete;
    private Image poison;
	
    private final int tab[][] = new int[Globals.B_WIDTH][Globals.B_HEIGHT];
    
    /* Coordonné du serpent à tout moment */
    private int x[] = new int [Globals.TAILLE_MAX];
    private int y[] = new int [Globals.TAILLE_MAX];

    private Timer timer;
    
    
	/* Constructeur d'un serpent */
	public Serpent (int difficulty, int nbrFood, JFrame fen) {
		/* 		*/

        setFocusable(true);
        setPreferredSize(new Dimension(Globals.B_WIDTH, Globals.B_HEIGHT));
        loadImages();
		/* 			*/
		taille = 4;
		tailleMin=2;
		Random rand = new Random();
		int repas = rand.nextInt(15) + 5 ; //Mettre entre 5 et 20 repas
		int repas_toxique = repas * difficulty;
		
		//placer la tete du serpent
		x[0] = 50 ;
		y[0] = 50 ;
		
		// initialiser le corps
		for(int i =1; i<taille; i++) {
			x[i] = 50 - i*PIXEL;
			y[i] = 50;
		}
		
		for(int i = 1; i<repas_toxique; i++) {
			ajouter_food_negative();
		}
		for(int i=1;i<repas; i++) {
			ajouter_food_positive();
		}
		
        JPanel cp = new JPanel();
        cp.setLayout(new BorderLayout());
        cp.add(this,BorderLayout.CENTER);
        
        JPanel pan = new JPanel();
        pan.setLayout(new FlowLayout());
        JLabel jtf = new JLabel("Score");
        pan.add(jtf);
        score = new JButton();
        score.setBackground(Color.white);
        score.setText(String.valueOf(0)); // Valeur total de repas manger
        pan.add(score);
        
        JLabel objectif = new JLabel("Objectif");
        pan.add(objectif);
        JButton goal = new JButton();
        goal.setBackground(Color.white);
        goal.setText(String.valueOf(nbrFood));
        pan.add(goal);
        
        cp.add(pan,BorderLayout.SOUTH);
        fen.setContentPane(cp);
		
		timer = new Timer(Globals.DELAY,this);
		timer.start();
	}
	
    private void loadImages() {

        ImageIcon iid = new ImageIcon("src/resources/corps.png");
        corps = iid.getImage();

        ImageIcon iia = new ImageIcon("src/resources/foodpos.png");
        food = iia.getImage();

        ImageIcon iih = new ImageIcon("src/resources/tete.png");
        tete = iih.getImage();
        
        ImageIcon iip = new ImageIcon("src/resources/foodneg.png");
        poison = iip.getImage();
    }
	public void deplacer() {
		
        for (int z = taille-1; z > 0; z--) {
            x[z] = x[(z - 1)];
            y[z] = y[(z - 1)];
        }

        if (leftDirection) {
            x[0] -= PIXEL;
        }

        if (rightDirection) {
            x[0] += PIXEL;
        }

        if (upDirection) {
            y[0] -= PIXEL;
        }

        if (downDirection) {
            y[0] += PIXEL;
        }

	}
	
	public void change_direction() {
		//double p = 0.5;
		double p = Math.random();
		
		/* On aura 60% de chance que le serpent ne change pas de direction et 40 % pour qu'il change */
		if(rightDirection) {
			if(p>0.6 && p<=0.8) {
				goUp();
			}
			else if(p>0.8 && p<=1.0) {
				goDown();
			}
		}
		
		else if(leftDirection) {
			if(p>0.6 && p<=0.8 ) {
				goUp();
			}
			else if(p>0.8 && p<=1.0 ) {
				goDown();
			}
		}
		
		else if(upDirection) {
			if(p>0.6 && p<=0.8) {
				goLeft();
			}
			else if(p>0.8 && p<=1.0) {
				goRight();
			}
		}
		
		else if(downDirection) {
			if(p>0.6 && p<=0.8 ) {
				goLeft();
			}
			else if(p>0.8 && p<=1.0 ) {
				goRight();
			}
		}
		
	}
	
	public void checkCollision() {
		double p=Math.random();
		if((y[0] == HEIGHT && downDirection) || (y[0]== 0 && upDirection)) {
			if(x[0] == 0) goRight();
        	else if(x[0] == WIDTH) goLeft();
        	else {
        		if(p<0.5) goLeft();
        		else if(p>=0.5) goRight();
        	}
		}
	/*	
		if ( y[0]== 0 && upDirection) { //le serpent est tout en bas ou en haut
        	if(x[0] == 0) goRight();
        	else if(x[0] == WIDTH) goLeft();
        	else {
        		if(p<0.5 && !(leftDirection)) goLeft();
        		else if(p>=0.5 && !(rightDirection)) goRight();
        	}
        }
*/		
        if ((x[0] == WIDTH && rightDirection) || (x[0] == 0 && leftDirection)) {
        	if(y[0] == 0) goDown();
        	else if (y[0] == HEIGHT) goUp();
        	else {
        		if(p<0.5 ) goUp();
        		else if(p>=0.5 ) goDown();
        	}
        } 
        

	}
	
	
	public void goLeft() {
	    leftDirection = true;
	    rightDirection = false;
	    upDirection = false;
	    downDirection = false;
	}
	
	public void goRight() {
	    leftDirection = false;
	    rightDirection = true;
	    upDirection = false;
	    downDirection = false;
	}
	
	public void goUp() {
	    leftDirection = false;
	    rightDirection = false;
	    upDirection = true;
	    downDirection = false;
	}
	
	public void goDown() {
	    leftDirection = false;
	    rightDirection = false;
	    upDirection = false;
	    downDirection = true;
	}
	
	public void ajouter_food_negative() {
        Random rand = new Random();
        int food_x = ((rand.nextInt(Globals.B_WIDTH))/Globals.PIXEL_SIZE )* Globals.PIXEL_SIZE;
        int food_y = ((rand.nextInt(Globals.B_HEIGHT))/Globals.PIXEL_SIZE) * Globals.PIXEL_SIZE;
        f.add(new FoodNegative(food_x,food_y));
        tab[food_x][food_y] = Globals.FOOD_NEG;
	}
	
	
	public void ajouter_food_positive() {
        Random rand = new Random();
        int food_x = ((rand.nextInt(Globals.B_WIDTH))/Globals.PIXEL_SIZE )* Globals.PIXEL_SIZE;
        int food_y = ((rand.nextInt(Globals.B_HEIGHT))/Globals.PIXEL_SIZE) * Globals.PIXEL_SIZE;
        f.add(new FoodPositive(food_x,food_y));
        tab[food_x][food_y] = Globals.FOOD_POS;
	}
	
	public void enlever_food_Negative(int x,int y) {
		f.remove(new FoodNegative(x,y));
		
	}
	
	public void enlever_food_Positive(int x,int y) {
		f.remove(new FoodPositive(x,y));
		
	}
	
	public int manger() {
		
		int a = x[0];
		int b = y[0];
		
		if(tab[a][b] == Globals.FOOD_POS) {
			grossir();
			nbrDeplacement=0;
			score.setText(String.valueOf(getFoodManger()));
			enlever_food_Positive(a,b);
			return Globals.FOOD_POS;
		}
		else if(tab[a][b] == Globals.FOOD_NEG) {
			retrecir();
			nbrDeplacement=0;
			enlever_food_Negative(a,b);
			return Globals.FOOD_NEG;
		}
		else return 0;
	}
	
	
	public void grossir() {
		taille = taille + 1;		
		nbrFoodManger++;
	}
	
	public void retrecir(){
		taille = taille - 1;
		if(taille<tailleMin) {
			inGame=false;
			timer.stop();
		}
	} 
	
	public int getFoodManger() {
		return nbrFoodManger;
	}
	
	public void checkFood() {
		seconde += Globals.DELAY ;
		nbrDeplacement++;
		/* On ajoute un food negative toutes les 4 secondes */
		if(seconde>4000) {
			ajouter_food_negative();
			seconde=0;
		}
		int m= manger();
		if(m==Globals.FOOD_NEG) {
			ajouter_food_negative();
		}
		if(m==Globals.FOOD_POS) {
			ajouter_food_positive();
		}
		if(nbrDeplacement==50) {
			retrecir();
			nbrDeplacement=0;
		}
	}
	
    public void actionPerformed(ActionEvent e) {
    	checkFood();
    	change_direction();
        checkCollision();
    	deplacer();
        repaint();
    }
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
     //   g.drawImage(food, food_x, food_y, this);
        if(inGame) {
		setBackground(Color.CYAN);
        for (int z = 0; z < taille; z++) {
            if (z == 0) {
                g.drawImage(tete, x[z], y[z], this);
            } else {
                g.drawImage(corps, x[z], y[z], this);
            }
        }
        
        for(Food alim : f) {
        	if(alim instanceof FoodNegative) g.drawImage(poison, alim.getX(), alim.getY(), this);
        	if(alim instanceof FoodPositive) { 
        		g.drawImage(food, alim.getX(), alim.getY(), this);
        		}
        	}
        	
        
        Toolkit.getDefaultToolkit().sync();
        }
        else gameOver(g);
	}

    private void gameOver(Graphics g) {
        String msg = "Game Over";
        Font small = new Font("Helvetica", Font.BOLD, 20);
        FontMetrics metr = getFontMetrics(small);

        g.setColor(Color.white);
        g.setFont(small);
        g.drawString(msg, (WIDTH - metr.stringWidth(msg)) / 2, HEIGHT / 2);
    }
}