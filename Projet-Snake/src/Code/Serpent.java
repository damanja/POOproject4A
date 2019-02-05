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
        setPreferredSize(new Dimension(Globals.B_WIDTH , Globals.B_HEIGHT));
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
	//	tab[50][50] = Globals.SNAKE_BODY;
		// initialiser le corps
		for(int i =1; i<taille; i++) {
			x[i] = 50 - i*Globals.PIXEL_SIZE;
			y[i] = 50;
	//		tab[50 - i*Globals.PIXEL_SIZE][50] = Globals.SNAKE_BODY; //actualiser le contenu du plateau
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

        ImageIcon iia = new ImageIcon("src/resources/pommevert.png");
        food = iia.getImage();

        ImageIcon iih = new ImageIcon("src/resources/tete.png");
        tete = iih.getImage();
        
        ImageIcon iip = new ImageIcon("src/resources/pommerouge.png");
        poison = iip.getImage();
    }
	public void deplacer() {
		
	//	tab[ x[(taille-1)]] [y[(taille-1)]] = 0;
        for (int z = taille; z > 0; z--) {
            x[z] = x[(z - 1)];
            y[z] = y[(z - 1)];
     //       tab[x[z]][y[z]] = tab[x[(z-1)]][(y[z]-1)];
        }

        if (leftDirection) {
            x[0] -= Globals.PIXEL_SIZE;
     //       tab[(x[0]-Globals.PIXEL_SIZE)][y[0]] = Globals.SNAKE_BODY;
        }

        else if (rightDirection) {
            x[0] += Globals.PIXEL_SIZE;
       //     tab[(x[0]+Globals.PIXEL_SIZE)][y[0]] = Globals.SNAKE_BODY;
        }

        else if (upDirection) {
            y[0] -= Globals.PIXEL_SIZE;
        //    tab[x[0]][(y[0]-Globals.PIXEL_SIZE)] = Globals.SNAKE_BODY;
        }

        else if (downDirection) {
            y[0] += Globals.PIXEL_SIZE;
          //  tab[x[0]][(y[0]+Globals.PIXEL_SIZE)] = Globals.SNAKE_BODY;
        }

	}
/*	public Dimension getPreferredSize() {
		return new Dimension(Globals.B_WIDTH,Globals.B_HEIGHT);
	}*/
	public void change_direction() {
		//double p = 0.5;
		double p = Math.random();
		
		
		if(rightDirection) {
			if(x[0]==Globals.LIM_DROITE) {
				if(y[0]==Globals.LIM_HAUT) {goDown();}
				else if(y[0]==Globals.LIM_BAS) {goUp();}
				else{
						if(p<0.5) goUp();
						else goDown();
					}
			}
			/* On aura 60% de chance que le serpent ne change pas de direction et 40 % pour qu'il change */
			else {
				if(y[0]==Globals.LIM_HAUT) {
					if(p<0.5) goDown();
				}
				else if(y[0]==Globals.LIM_BAS){
					if(p<0.5) goUp();
				}
				else {
					if(p>0.7 && p<=0.85) {
						goUp();
					}
					else if(p>0.85 && p<=1.0) {
						goDown();
					}
				}
			}
		}
		
		else if(leftDirection) {
			if(x[0]==Globals.LIM_GAUCHE) {
				if(y[0]==Globals.LIM_HAUT) {goDown();}
				else if(y[0]==Globals.LIM_BAS) {goUp();}
				else{
						if(p<0.5) {goUp();}
						else {goDown();}
					}
			}
			/* On aura 60% de chance que le serpent ne change pas de direction et 40 % pour qu'il change */
			else {
				if(y[0]==Globals.LIM_HAUT) {
					if(p<0.5) goDown();
				}
				else if(y[0]==Globals.LIM_BAS){
					if(p<0.5) goUp();
				}
				else {
					if(p>0.7 && p<=0.85) {
						goUp();
					}
					else if(p>0.85 && p<=1.0) {
						goDown();
					}
				}
			}
		}
		
		else if(upDirection) {
			if(y[0]==Globals.LIM_HAUT) {
				if(x[0]==Globals.LIM_GAUCHE) { goRight();}
				else if(x[0]==Globals.LIM_DROITE) {goLeft();}
				else{
						if(p<0.5) {goLeft();}
						else {goRight();}
					}
			}
			/* On aura 60% de chance que le serpent ne change pas de direction et 40 % pour qu'il change */
			else {
				if(x[0]==Globals.LIM_GAUCHE) {
					if(p<0.5) goRight();
				}
				else if(x[0]==Globals.LIM_DROITE){
					if(p<0.5) goLeft();
				}
				else {
					if(p>0.7 && p<=0.85) {
						goRight();
					}
					else if(p>0.85 && p<=1.0) {
						goLeft();
					}
				}
			}
		}
		
		else if(downDirection) {
			if(y[0]==Globals.LIM_BAS) {
				if(x[0]==Globals.LIM_GAUCHE) {goRight();}
				else if(x[0]==Globals.LIM_DROITE) { goLeft();}
				else{
						if(p<0.5) {goLeft();}
						else {goRight();}
					}
			}
			/* On aura 60% de chance que le serpent ne change pas de direction et 40 % pour qu'il change */
			else {
				if(x[0]==Globals.LIM_GAUCHE) {
					if(p<0.5) goRight();
				}
				else if(x[0]==Globals.LIM_DROITE){
					if(p<0.5) goLeft();
				}
				else {
					if(p>0.7 && p<=0.85) {
						goRight();
					}
					else if(p>0.85 && p<=1.0) {
						goLeft();
					}
				}
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
			tab[a][b]=0;
			return Globals.FOOD_POS;
		}
		else if(tab[a][b] == Globals.FOOD_NEG) {
			retrecir();
			nbrDeplacement=0;
			enlever_food_Negative(a,b);
			tab[a][b]=0;
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
	
	public boolean trouve_nourriture(int decalage) {
		int tete_x=x[0];
		int tete_y=y[0];
		if( (tete_x - decalage*Globals.PIXEL_SIZE)>= (Globals.LIM_GAUCHE)
				&& tab[(tete_x - decalage*Globals.PIXEL_SIZE)][tete_y]== Globals.FOOD_POS ) {
			goLeft();return true;
		}
		if( (tete_x + decalage*Globals.PIXEL_SIZE)<= Globals.LIM_DROITE 
				&& tab[(tete_x + decalage*Globals.PIXEL_SIZE)][tete_y]== Globals.FOOD_POS )  {
			goRight();return true; 
		}
		if( (tete_y - decalage*Globals.PIXEL_SIZE) >= Globals.LIM_HAUT 
				&& tab[tete_x][tete_y - decalage*Globals.PIXEL_SIZE]== Globals.FOOD_POS )  {
			goUp();return true; 
		}
		if( (tete_y + decalage*Globals.PIXEL_SIZE) <= Globals.LIM_BAS 
				&& tab[tete_x][tete_y + decalage*Globals.PIXEL_SIZE]== Globals.FOOD_POS )  {
			goDown();return true; 
		}
		return false;
	}
	
    public void actionPerformed(ActionEvent e) {
    	checkFood();
    	deplacer();
    	if(!trouve_nourriture(1) && !trouve_nourriture(2) && !trouve_nourriture(3)) change_direction();
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