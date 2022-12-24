package assignment5;
/* CRITTERS GUI <MyClass.java>
 * EE422C Project 5 submission by
 * Replace <...> with your actual data.
 * Prithvi Senthilkumar
 * ps33536
 * 17840
 * Hamza Shaikh
 * hms2659
 * 17835
 * Slip days used: <0>
 * Fall 2021
 */
/**
 * Critter2 has a 20% chance to walk, or an 80% chance to run in each time step, in a random direction
 * Always reproduces at a medium energy level
 * Has a 10% chance to fight, else it will either walk or run away (50% to walk, 40% to run)
 * @author Prithvi and Hamza
 *
 */
public class Critter2 extends Critter {

	@Override
    public String toString() {
        return "2";
    }
	
	@Override
	/**
	 * simulates the time step for the critter, walks/runs/reproduces
	 */
	public void doTimeStep() {
		// TODO Auto-generated method stub
		int decision = getRandomInt(10);
		int direction = getRandomInt(8);
		if (decision < 2) {
		walk(direction);
		}
		else {
			run(direction);
		}
		if (getEnergy() > 100) {
            Critter1 child = new Critter1();
            reproduce(child, Critter.getRandomInt(8));
        }
	}

	@Override
	/**
	 * @return true if critter will fight, false if not, tries to run/walk if false
	 */
	public boolean fight(String oponent) {
		int decision = getRandomInt(10);
		int direction = getRandomInt(8);
		if (decision == 9) {
			return true;
		}
		else if (decision < 5) {
			walk(direction);
		}
		else if (decision >= 5) {
			run(direction);
		}
		return false;
	}

	@Override
	public CritterShape viewShape() {
		// TODO Auto-generated method stub
		return Critter.CritterShape.SQUARE;
	}
	@Override
    public javafx.scene.paint.Color viewOutlineColor() {
        return javafx.scene.paint.Color.RED;
    }
	public javafx.scene.paint.Color viewColor() {
        return javafx.scene.paint.Color.PURPLE;
    }
}
