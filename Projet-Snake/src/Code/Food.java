package Code;

import java.util.ArrayList;

public interface Food {
	void ajouter(ArrayList<Food> f);
	void enlever(int x, int y);
	int getX();
	int getY();
}
