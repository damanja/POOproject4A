package Code;

public class FoodNegative implements Food{
	private int val_nutrition;
	private int x;
	private int y;
	@Override
	public void ajouter(int x, int y) {
		// TODO Auto-generated method stub
		
	}
	
	public FoodNegative(int x,int y) {
		this.x = x;
		this.y = y;
	}
	
	@Override
	public void enlever(int x, int y) {
		// TODO Auto-generated method stub
		
	}
	
	public boolean equals(Object o ) {
		if(!(o instanceof FoodNegative)) return false;
		FoodNegative f = (FoodNegative) o;
		return f.x==this.x && f.y==this.y;
	}

	@Override
	public int getX() {
		// TODO Auto-generated method stub
		return x;
	}

	@Override
	public int getY() {
		// TODO Auto-generated method stub
		return y;
	}
}
