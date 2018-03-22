package assignment4;

import java.util.List;

public class DiagCritter extends Critter.TestCritter{

	private int direction;

	@Override
	public void doTimeStep() {
		// TODO Auto-generated method stub
		// Move
		walk(direction);

		// Reproduce
		if (getEnergy() > 150) {
			DiagCritter smolCrit = new DiagCritter();
			reproduce(smolCrit, Critter.getRandomInt(8));
		}

		// Turn -- bishop style movement (diagonals only)
		int new_dir = Critter.getRandomInt(4);
		switch(new_dir) {
			case 0: 
				direction = 1; 
			case 1: 
				direction = 3;
			case 2: 
				direction = 5;
			case 3: 
				direction = 7;
		}
	}

	@Override
	public boolean fight(String oponent) {
		// TODO Auto-generated method stub
		return true;
	}

	public String toString() { 
		return "+";
	}

	public DiagCritter() {
		direction = Critter.getRandomInt(8);
	}

}