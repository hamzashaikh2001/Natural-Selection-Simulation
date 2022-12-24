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
 * Critter1 has a 50% chance of either running or walking in a random direction
 * always reproduces at a lower energy count
 * Critter1 also has a 50% chance to fight, if it chooses not to fight, it stays at its position
 * @author Prithvi and Hamza
 *
 */
public class Critter1 extends Critter {

    @Override
    public String toString() {
        return "1";
    }
	
	@Override
	/**
	 * simulates the time step for the critter, walks/runs/reproduces
	 */
	public void doTimeStep() {
		// TODO Auto-generated method stub
		int decision = getRandomInt(2);
		int direction = getRandomInt(8);
		if (decision == 0) {
		walk(direction);
		}
		else {
			run(direction);
		}
		if (getEnergy() > 50) {
            Critter1 child = new Critter1();
            reproduce(child, Critter.getRandomInt(8));
        }
	}

	@Override
	/**
	 * @return true if critter will fight, false if not, tries to run/walk if false
	 */
	public boolean fight(String oponent) {
		int decision = getRandomInt(2);
		if (decision == 1) {
			return true;
		}
		return false;
	}

	@Override
	public CritterShape viewShape() {
		// TODO Auto-generated method stub
		return Critter.CritterShape.STAR;
	}
	@Override
    public javafx.scene.paint.Color viewOutlineColor() {
        return javafx.scene.paint.Color.BLUE;
    }
	public javafx.scene.paint.Color viewColor() {
        return javafx.scene.paint.Color.AQUA;
    }

}
