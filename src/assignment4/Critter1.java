package assignment4;

import java.util.List;

public class Critter1 extends Critter.TestCritter{

	private int direction;

	@Override
	public void doTimeStep() {
		// TODO Auto-generated method stub
		// Move
		walk(direction);

		// Reproduce
		if (getEnergy() > 150) {
			Critter1 smolCrit = new Critter1();
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
		return "1";
	}

	public Critter1() {
		direction = Critter.getRandomInt(8);
	}

}