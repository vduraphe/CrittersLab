package assignment4;

import java.util.List;

public class UpDownCrit extends Critter.TestCritter{

	private int direction;

	@Override
	public void doTimeStep() {
		// TODO Auto-generated method stub
		// Move
		walk(direction);

		// Reproduce
		if (getEnergy() > 150) {
			UpDownCrit upCrit = new UpDownCrit();
			reproduce(upCrit, Critter.getRandomInt(8));
		}

		// Turn -- bishop style movement (diagonals only)
		int new_dir = Critter.getRandomInt(2);
		switch(new_dir) {
		case 0: 
			direction = 2; 
		case 1: 
			direction = 6;
		}
	}

	@Override
	public boolean fight(String oponent) {
		// TODO Auto-generated method stub
		return true;
	}

	public String toString() { 
		return "|";
	}

	public UpDownCrit() {
		direction = Critter.getRandomInt(8);
	}

}