package Code;

public class Serpent {
	private int taille;
	private int tailleMin;
	private int nbrDeplacement;
	
	private int x[];
	
	
	/* Constructeur d'un serpent */
	public Serpent (int t) {
		taille = t;
	}
	
	public void deplacer() {
		
	}
	
	public void manger() {
		
	}
	
	public void grossir() {
		taille = taille + 1;
	}
	
	public void retrecir(){
		taille = taille - 1;
	}
}
