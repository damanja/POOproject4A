package Code;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.Timer;

public class Serpent extends JPanel implements ActionListener{
	private int taille;
	private int tailleMin;
	private int nbrDeplacement = 0;
	private int tailleMax;
	private int seconde=0;
	
	private Plateau p;
	private int PIXEL = 10;
	private int TETE_SERPENT = 0;
	private int CORPS_SERPENT = 1;
	private int queue_serpent;
	private final int ALL_PIXELS = 3000;
	private final int DELAY = 100;
	private final int WIDTH = 500;
	private final int HEIGHT = 500;
	private final int PIXEL_SIZE = 10;
	private final int FOOD = 120;
	private final int FOOD_NEG = 140;
	private final int FOOD_POS = 130;
	private final int RAND_POS = 26;
	
	//private int tab[][] = new int [WIDTH][HEIGHT];
	private ArrayList<Food> f = new ArrayList<>();
	private ArrayList<FoodNegative> fn= new ArrayList<>();
	private ArrayList<FoodPositive> fp = new ArrayList<>();
	private int nbr=300;
	
    private boolean leftDirection = false;
    private boolean rightDirection = true;
    private boolean upDirection = false;
    private boolean downDirection = false;
    
    private Image corps;
    private Image food;
    private Image tete;
    private Image poison;
	
    private final int tab[][] = new int[ALL_PIXELS][ALL_PIXELS];
    
    /* Coordonné du serpent à tout moment */
    private int x[] = new int [ALL_PIXELS];
    private int y[] = new int [ALL_PIXELS];

    private Timer timer;
    
    
	/* Constructeur d'un serpent */
	public Serpent () {
		/* 		*/

        setFocusable(true);
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        //setPreferredSize(new Dimension(500, 500));
        loadImages();
		
		/* 			*/
		taille = 3;
		tailleMax = 10;
		
		
		//placer la tete du serpent
		x[0] = 50 ;
		y[0] = 50 ;
		int i;
		// initialiser le corps
		for(i =1; i<taille; i++) {
			x[i] = 50 - i*PIXEL;
			y[i] = 50;
		}
		
		for(i = 1; i<5; i++) {
			ajouter_food_negative();
		}
		for(i=1;i<2; i++) {
			ajouter_food_positive();
		}
		
		timer = new Timer(DELAY,this);
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
        int r = (int) (Math.random() * RAND_POS);
        
        Random rand = new Random();
        int food_x = ((rand.nextInt(WIDTH))/PIXEL_SIZE )* PIXEL_SIZE;
        int food_y = ((rand.nextInt(HEIGHT))/PIXEL_SIZE) * PIXEL_SIZE;
        
        f.add(new FoodNegative(food_x,food_y));
        tab[food_x][food_y] = FOOD_NEG;
	}
	
	public void ajouter_food_positive() {
        int r = (int) (Math.random() * RAND_POS);
        Random rand = new Random();
        
        int food_x = ((rand.nextInt(WIDTH))/PIXEL_SIZE )* PIXEL_SIZE;
        int food_y = ((rand.nextInt(HEIGHT))/PIXEL_SIZE) * PIXEL_SIZE;
        
        f.add(new FoodPositive(food_x,food_y));
        tab[food_x][food_y] = FOOD_POS;
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
		
		if(tab[a][b] == FOOD_POS) {
			grossir();
			enlever_food_Positive(a,b);
			return FOOD_POS;
		}
		else if(tab[a][b] == FOOD_NEG) {
			retrecir();
			enlever_food_Negative(a,b);
			return FOOD_NEG;
		}
		else return 0;
	}
	
	
	public void grossir() {
		taille = taille + 1;		
	}
	
	public void retrecir(){
		taille = taille - 1;
	}
	
	public void checkFood() {
		seconde += DELAY ;
		nbrDeplacement++;
		/* On ajoute un food negative toutes les 4 secondes */
		if(seconde>4000) {
			ajouter_food_negative();
			seconde=0;
		}
		int m= manger();
		if(m==FOOD_NEG) {
			ajouter_food_negative();
			nbrDeplacement = 0;
		}
		if(m==FOOD_POS) {
			ajouter_food_positive();
			nbrDeplacement = 0;
		}
		if(nbrDeplacement==50) {
			retrecir();
			nbrDeplacement=0;
		}

		
	}
	
	public void gameOver() {
		
	}
	
    public void actionPerformed(ActionEvent e) {

    	checkFood();
    	change_direction();
        checkCollision();
    	deplacer();
    	if(taille<tailleMin) gameOver();
        repaint();
    }
	
    
    
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
     //   g.drawImage(food, food_x, food_y, this);
//		g.clearRect(0, 0, WIDTH, HEIGHT);
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
	
	public void checkGameOver() {
		if(nbr>500) timer.stop();
	}
}