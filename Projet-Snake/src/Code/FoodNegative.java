package Code;

import java.util.ArrayList;
import java.util.Random;

public class FoodNegative implements Food{
	private int val_nutrition;
	private int x;
	private int y;
	@Override
	public void ajouter(ArrayList<Food> f) {
		// TODO Auto-generated method stub
        Random rand = new Random();
        int food_x = ((rand.nextInt(Globals.B_WIDTH))/Globals.PIXEL_SIZE )* Globals.PIXEL_SIZE;
        int food_y = ((rand.nextInt(Globals.B_HEIGHT))/Globals.PIXEL_SIZE) * Globals.PIXEL_SIZE;
        f.add(new FoodNegative(food_x,food_y));
        //tab[food_x][food_y] = Globals.FOOD_NEG;
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
