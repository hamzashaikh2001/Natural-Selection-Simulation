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
 * Critter3 has a 20% chance to walk, after which it will try to run but will fail and lose energy
 * has a 40% chance to sucessfully run in doTimeStep
 * has 40% reproduction rate at a low energy level, so it reproduces less often but will reach threshold faster
 * has a 60% chance to fight, so it is aggressive, other 40% it will walk if look is null, but will fail if already moved
 * @author Prithvi and Hamza
 *
 */
public class Critter3 extends Critter {

	@Override
	/**
	 * simulates the time step for the critter, walks/runs/reproduces
	 */
	public void doTimeStep() {
		int decision = getRandomInt(10);
		int direction = getRandomInt(8);
		int spawnDecide = getRandomInt(5);
		if (decision < 2) {
		walk(direction);
		}
		if (decision < 6) {
			run(direction);//has the possiblity to be called after a walk, in which case don't move again
		}
		if (spawnDecide < 2 && getEnergy() > 40) {
            Critter3 child = new Critter3();
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
		if (decision > 3) {
			return true;
		}
		else if (look(direction, false) == null){
			walk(direction);
		}
		return false;
	}
	@Override
	public String toString() {
		return "3";
	}

	@Override
	public CritterShape viewShape() {
		// TODO Auto-generated method stub
		return Critter.CritterShape.TRIANGLE;
	}
	@Override
    public javafx.scene.paint.Color viewOutlineColor() {
        return javafx.scene.paint.Color.RED;
    }
	public javafx.scene.paint.Color viewColor() {
        return javafx.scene.paint.Color.YELLOW;
    }

}
