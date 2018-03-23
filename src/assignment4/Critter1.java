package assignment4;
/*
 * /* CRITTERS Critter1.java
 * EE422C Project 4 submission by
 * Vaidehi Duraphe
 * vd5374
 * Anika Agarwal
 * aa59662
 * Slip days used: <0>
 * Spring 18
 */

import java.util.List;
/**
 * Critter only moves in diagonals
 * @author Vaidehi
 *
 */
public class Critter1 extends Critter.TestCritter{

	private int direction;

	@Override
	public void doTimeStep() {
		// TODO Auto-generated method stub
		walk(direction);
		if (getEnergy() > 150) {
			Critter1 smolCrit = new Critter1();
			reproduce(smolCrit, Critter.getRandomInt(8));
		}

		// diag movement only
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
		return true;
	}

	public String toString() { 
		return "1";
	}

	public Critter1() {
		direction = Critter.getRandomInt(8);
	}

}