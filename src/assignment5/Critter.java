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

package assignment5;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

//import assignment4.Critter;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;

//import assignment4.Critter;
//import assignment4.InvalidCritterException;
//import assignment4.Params;
//import assignment4.Position;
//import assignment4.Critter.TestCritter;

/*
 * See the PDF for descriptions of the methods and fields in this
 * class.
 * You may add fields, methods or inner classes to Critter ONLY
 * if you make your additions private; no new public, protected or
 * default-package code or data can be added to Critter.
 */

public abstract class Critter {

    /* START --- NEW FOR PROJECT 5 */
    public enum CritterShape {
        CIRCLE,
        SQUARE,
        TRIANGLE,
        DIAMOND,
        STAR
    }

    /* the default color is white, which I hope makes critters invisible by default
     * If you change the background color of your View component, then update the default
     * color to be the same as you background
     *
     * critters must override at least one of the following three methods, it is not
     * proper for critters to remain invisible in the view
     *
     * If a critter only overrides the outline color, then it will look like a non-filled
     * shape, at least, that's the intent. You can edit these default methods however you
     * need to, but please preserve that intent as you implement them.
     */
    public javafx.scene.paint.Color viewColor() {
        return javafx.scene.paint.Color.WHITE;
    }

    public javafx.scene.paint.Color viewOutlineColor() {
        return viewColor();
    }

    public javafx.scene.paint.Color viewFillColor() {
        return viewColor();
    }

    public abstract CritterShape viewShape();

    protected final String look(int direction, boolean steps) {
    	energy -= Params.LOOK_ENERGY_COST;
    	int x = x_coord;
    	int y = y_coord;
    	int[] tempCoord = move(direction, x, y);
    	x = tempCoord[0];
    	y = tempCoord[1];
    	if (steps) {//second move
    		tempCoord = move(direction, x, y);
    		x = tempCoord[0];
        	y = tempCoord[1];
    	}
    	Position checkCritter = new Position(x,y);
    	if (population.containsKey(checkCritter)) {
    		if (population.get(checkCritter) != null) {
    			ArrayList lookCrits = population.get(checkCritter);
    			return lookCrits.get(0).toString();
    		}
    	}
        return null;
    }

    public static String runStats(List<Critter> critters) {
        // TODO Implement this method
    	
    	String stat = "" + critters.size() + " critters as follows -- ";
    	Map<String, Integer> critter_count = new HashMap<String, Integer>();
        for (Critter crit : critters) {
            String crit_string = crit.toString();
            critter_count.put(crit_string,
                    critter_count.getOrDefault(crit_string, 0) + 1);
        }
        String prefix = "";
        for (String s : critter_count.keySet()) {
            stat += prefix + s + ":" + critter_count.get(s);
            prefix = ", ";
        }
        stat += "\n";
        return stat;
    }


    public static void displayWorld(Object pane) {
        // TODO Implement this method
    	GridPane grid = (GridPane) pane;
    	grid.getChildren().clear();
    	/*for (int i = 0; i <= 2; i++) {
    		for (int j = 0; j <= 2; j++) {
    		Shape s = new Rectangle(20, 20);
    		s.setFill(null);
    		s.setStroke(Color.ORANGE);
    		grid.add(s, i, j);
    		}
    	}*/
    	int width = 1000/Params.WORLD_WIDTH;
    	int height = 650/Params.WORLD_HEIGHT;
    	double size;
    	if (height > width) {
    	size = 950/Params.WORLD_WIDTH;
    	}
    	else {
    	size = 600/Params.WORLD_HEIGHT;
    	}
    	
    	Shape blank = new Rectangle(0, 0);
		blank.setFill(null);
		blank.setStroke(null);
		
		grid.add(blank, 0, 0);
    	
		for (int x = 0; x < Params.WORLD_WIDTH; x++) {
    		for (int y = 0; y < Params.WORLD_HEIGHT; y++) {
    			Shape s = new Rectangle(size, size);
        		s.setFill(Color.PEACHPUFF);
        		s.setStroke(Color.CHOCOLATE);
        		
        		grid.add(s, x + 1, y + 1);
        		
        		Position coords = new Position(x, y);
    			
    			ArrayList<Critter> entities;
    			if (population.containsKey(coords)) {
    	    		entities = population.get(coords);
    	    		Critter crit = entities.get(0);
    	    		CritterShape critShape = crit.viewShape();
    	    		Shape icon = new Rectangle(0,0);
    	    		switch (critShape) {
    	    			case CIRCLE:
    	    				//icon = new Circle(10);
    	    				icon = new Circle(size/2);
    	    				break;
    	    			case SQUARE:
    	    				//icon = new Rectangle(20,20);
    	    				icon = new Rectangle(size, size);
    	    				break;
    	    			case TRIANGLE:
    	    				icon = new Polygon();
    	    				//((Polygon) icon).getPoints().addAll(new Double[]{ (double) x,y+20.0, x+20.0, y+20.0, x+10.0, (double) y });
    	    				((Polygon) icon).getPoints().addAll(new Double[]{ (double) x+1,y+size-1, x+size-1, y+size-1, x+(size/2), (double) y+1});
    	    				break;
    	    			case DIAMOND:
    	    				icon = new Polygon();
    	    				//((Polygon) icon).getPoints().addAll(new Double[]{x +0.0, y+10.0, x+10.0, y+0.0, x+20.0, y+10.0 , x+10.0, y+20.0});
    	    				((Polygon) icon).getPoints().addAll(new Double[]{x +1.0, y+(size/2), x+(size/2), y+1.0, x+size-1.0, y+(size/2), x+(size/2), y+size-1.0});
    	    				break;
    	    			case STAR:
    	    				icon = new Polygon();
    	    				((Polygon) icon).getPoints().addAll(new Double[]{x+(size/12)+2.0,y+size-2.0, x+(size/2),y+(size*2/3), x+(size*11/12)-1.0,y+size-2.0, x+(size*2/3),y+(size*7/12), x+size-2.0,y+(size/3), x+(size*2/3),y+(size*1/3), x+(size/2),y+2.0, x+(size*1/3),y+(size*1/3), x+1.0,y+(size/3), x+(size*1/3),y+(size*7/12)});
    	    				break;
    	    			
    	    		}
    	    		icon.setFill(crit.viewFillColor());
    	    		icon.setStroke(crit.viewOutlineColor());
    	    		grid.add(icon, x + 1, y + 1);
    	    	}
    		}
    	}
    	
    }

	/* END --- NEW FOR PROJECT 5
			rest is unchanged from Project 4 */

    private int energy = 0;

    private int x_coord;
    private int y_coord;

    
    private static List<Critter> babies = new ArrayList<Critter>();
    //create a map
    
    private static Map<Position, ArrayList<Critter>> population = new HashMap<Position, ArrayList<Critter>>();
    
    /* Gets the package name.  This assumes that Critter and its
     * subclasses are all in the same package. */
    private static String myPackage;
    private boolean hasMoved;
    private boolean isFighting;

    static {
        myPackage = Critter.class.getPackage().toString().split(" ")[1];
    }

    private static Random rand = new Random();

    public static int getRandomInt(int max) {
        return rand.nextInt(max);
    }

    public static void setSeed(long new_seed) {
        rand = new Random(new_seed);
    }

    /**
     * create and initialize a Critter subclass.
     * critter_class_name must be the unqualified name of a concrete
     * subclass of Critter, if not, an InvalidCritterException must be
     * thrown.
     *
     * @param critter_class_name
     * @throws InvalidCritterException
     */
    public static void createCritter(String critter_class_name)
            throws InvalidCritterException {
        // TODO: Complete this method
    	String className = myPackage + "." + critter_class_name;
		try {
			Class<?> c = Class.forName(className);
			Critter crit = (Critter) c.newInstance();
			crit.energy = Params.START_ENERGY;//set start energy
	    	crit.x_coord = getRandomInt(Params.WORLD_WIDTH);
	    	crit.y_coord = getRandomInt(Params.WORLD_HEIGHT);
	    	spawn(crit);
	    	
		} catch (ClassNotFoundException
				| InstantiationException
				| IllegalAccessException
				| ClassCastException e) {
			throw new InvalidCritterException(critter_class_name);
		}
		
    }
    /**
     * Takes a critter whose coordinates have been set, and adds it to the pop map.
     * crit must be a valid critter with coordinates already set.
     * @param crit
     */
    private static void spawn(Critter crit) {
    	Position coords = new Position(crit.x_coord, crit.y_coord);
    	ArrayList<Critter> entities;
    	if (population.containsKey(coords)) {
    		entities = population.get(coords);
    	}
    	else {
    		entities = new ArrayList<Critter>();
    		population.put(coords, entities);
    	}
    	entities.add(crit);
    }
    
    /**
     * Gets a list of critters of a specific type.
     *
     * @param critter_class_name What kind of Critter is to be listed.
     *        Unqualified class name.
     * @return List of Critters.
     * @throws InvalidCritterException
     */
    public static List<Critter> getInstances(String critter_class_name)
            throws InvalidCritterException {
        // TODO: Complete this method
    	String className = myPackage + "." + critter_class_name;
		try {
			Class<?> c = Class.forName(className);
			List<Critter> instList = new ArrayList<Critter>();
        	for (ArrayList<Critter> entities : population.values()) {
        		for (Critter crit : entities) {
        			if (c.isInstance(crit)) {
        				instList.add(crit);
        			}
        		}
        	}
            return instList;
	    	
		} catch (ClassNotFoundException | ClassCastException e) {
			throw new InvalidCritterException(critter_class_name);
		}
    }

    /**
     * Clear the world of all critters, dead and alive
     */
    public static void clearWorld() {
        // TODO: Complete this method
    	population.clear();
    }
    /**
     * Main sequence to simulate time in the world.
     * Iterates through the population and does each Critter's time step
     * Afterwards, if 2 critters are at the same coordinate, it calls doEncounter with both of them
     * After encounters, update the rest energy of all critters, remove the dead, add clovers, and promote babies
     */
    public static void worldTimeStep() {
        // TODO: Complete this method
    	for (Critter animal : TestCritter.getPopulation()) {
    		animal.hasMoved = false;
    		animal.isFighting = false;
    		animal.doTimeStep();
    	}
    	
    	
    	Set<Position> keys = population.keySet();
    	Set<Position> copyKeys = new HashSet<Position>();
    	for (Position key : keys) {//create a temporary set to iterate through
    		copyKeys.add(key);
    	}
    	
    	
    	for (Position coords : copyKeys) {
    		ArrayList<Critter> animals = population.get(coords);
    		while (animals != null && animals.size() > 1) {
    			doEncounters(animals.get(0), animals.get(1));
    			animals = population.get(coords);
    		}
    	}
    	
    	updateRestEnergy();
    	cullDead();
    	genClover();
    	for (Critter baby : babies) {
    		spawn(baby);
    	}
    	babies.clear();
    }
    /**
     * Simulates the interaction between 2 critters.
     * entityA and entityB must be valid Critters.
     * Calls the fight method so see if critter fights and gets random value for roll
     * Whichever critter has the higher roll survives, and the other critter is killed and removed from population
     * @param entityA
     * @param entityB
     */
    private static void doEncounters(Critter entityA, Critter entityB) {
    	entityA.isFighting = true;
    	entityB.isFighting = true;
    	int rollA = 0;
    	int rollB = 0;
    	if (entityA.fight(entityB.toString()) && (entityA.energy > 0)) {
    		rollA = getRandomInt(entityA.energy);
    	}
    	if (entityB.fight(entityA.toString()) && (entityB.energy > 0)) {
    		rollB = getRandomInt(entityB.energy);
    	}
    	// Check if A and B are both alive and if they are in the same position
    	Position aCoord = new Position(entityA.x_coord, entityA.y_coord);
    	Position bCoord = new Position(entityB.x_coord, entityB.y_coord);
    	if ((aCoord.equals(bCoord)) && (entityA.energy > 0) && (entityB.energy > 0)) {
	    	if (rollA >= rollB) {
	    		entityA.energy += entityB.energy/2;
	    		entityB.energy = 0;
	    	}
	    	else {
	    		entityB.energy += entityA.energy/2;
	    		entityA.energy = 0;
	    	}
    	}
    	if (entityA.energy <= 0) {
    		ArrayList<Critter> aList = population.get(aCoord);
    		aList.remove(entityA);
    		if (!aList.isEmpty()) {
    			population.put(aCoord, aList);
    		}
    		else {
    			population.remove(aCoord);
    		}
    	}
    	if (entityB.energy <= 0) {
    		ArrayList<Critter> bList = population.get(bCoord);
    		bList.remove(entityB);
    		if (!bList.isEmpty()) {
    			population.put(bCoord, bList);
    		}
    		else {
    			population.remove(bCoord);
    		}
    	}
    }
    /**
     * Subtracts the rest energy from each critter in the population
     */
    private static void updateRestEnergy() {
    	for (Critter animal : TestCritter.getPopulation()) {
    		animal.energy -= Params.REST_ENERGY_COST;
    	}
    }
    /**
     * Creates new Clovers every world time step based on Params clover count
     */
    private static void genClover() {
    	for (int i = 0; i < Params.REFRESH_CLOVER_COUNT; i++) {
    		try {
				createCritter("Clover");
			} catch (InvalidCritterException e) {
				e.printStackTrace();
			}
    	}
    }
    /**
     * Called in worldTimeStep()
     * iterated through critter population, and if a critter's energy is <= 0, remove it from population map
     */
    private static void cullDead() {
    	for (Critter animal : TestCritter.getPopulation()) {
    		if (animal.energy <= 0) {
    			Position coords = new Position(animal.x_coord, animal.y_coord);
    			ArrayList<Critter> entities = population.get(coords);
    			entities.remove(animal);
    			if (!entities.isEmpty()) {
    				population.put(coords, entities);
    			}
    			else {
    				population.remove(coords);
    			}
    		}
    	}
    }
    /**
     * Prints a border, size depends of parameters in Params.java
     * For each position, it sees if the coordinates are in the population map
     * If so, then it prints the toString of the 1st critter at that coordinate in the map
     */
    /*
    public static void displayWorld() {
        // TODO: Complete this method
    	char[] border = new char[Params.WORLD_WIDTH + 2];
    	border[0] = '+';
    	for (int i = 0; i < Params.WORLD_WIDTH; i++) {
    		border[i + 1] = '-';
    	}
    	border[border.length - 1] = '+';
    	System.out.println(border);
    	
    	for (int y = 0; y < Params.WORLD_HEIGHT; y++) {
    		System.out.print('|');
    		for (int x = 0; x < Params.WORLD_WIDTH; x++) {
    			Position coords = new Position(x, y);
    			
    			ArrayList<Critter> entities;
    			if (population.containsKey(coords)) {
    	    		entities = population.get(coords);
    	    		System.out.print(entities.get(0));
    	    	}
    	    	else {
    	    		System.out.print(' ');
    	    	}
    		}
    		System.out.println('|');
    	}
    	
    	System.out.println(border);
    }
    */

    /**
     * Prints out how many Critters of each type there are on the
     * board.
     *
     * @param critters List of Critters.
     */
    /*
    public static void runStats(List<Critter> critters) {
        System.out.print("" + critters.size() + " critters as follows -- ");
        Map<String, Integer> critter_count = new HashMap<String, Integer>();
        for (Critter crit : critters) {
            String crit_string = crit.toString();
            critter_count.put(crit_string,
                    critter_count.getOrDefault(crit_string, 0) + 1);
        }
        String prefix = "";
        for (String s : critter_count.keySet()) {
            System.out.print(prefix + s + ":" + critter_count.get(s));
            prefix = ", ";
        }
        System.out.println();
    }
    */

    public abstract void doTimeStep();

    public abstract boolean fight(String oponent);

    /* a one-character long string that visually depicts your critter
     * in the ASCII interface */
    public String toString() {
        return "";
    }

    protected int getEnergy() {
        return energy;
    }
    /**
     * Takes in a direction parameter, which is an integer
     * If it hasn't moved yet, gets the coordinates for where the critter is supposed to moved based on input
     * Removes the critter from the old position in the map, and sets it new coordinate values.
     * Then calls spawn to add it to the population map
     * @param direction
     */
    protected final void walk(int direction) {
        // TODO: Complete this method
    	energy -= Params.WALK_ENERGY_COST;
    	if (!hasMoved) {
	    	int[] temp = move(direction, x_coord, y_coord);
	    	if (isFighting) {
	    		Position coords = new Position(temp[0], temp[1]);
	    		ArrayList<Critter> check = population.get(coords);
	    		if ((check != null) && (!check.isEmpty())) {
	    			hasMoved = true;
	    			return;
	    		}
	    	}
	    	Position oldCoords = new Position(x_coord, y_coord);
	    	ArrayList<Critter> entities = population.get(oldCoords);
	    	entities.remove(this);
	    	if (entities.size() <= 0) {
	    		population.remove(oldCoords);
	    	}
	    	x_coord = temp[0];
	    	y_coord = temp[1];
	    	spawn(this);
    	}
    	hasMoved = true;
    }
    /**
     * same as walk, but calls move twice so it moves 2 spaces in a direction instead of 1
     * @param direction
     */
    protected final void run(int direction) {
        // TODO: Complete this method
    	energy -= Params.RUN_ENERGY_COST;
    	if (!hasMoved) {
    		int[] move1 = move(direction, x_coord, y_coord);
    		int[] move2 = move(direction, move1[0], move1[1]);
    		if (isFighting) {
	    		Position coords = new Position(move2[0], move2[1]);
	    		ArrayList<Critter> check = population.get(coords);
	    		if ((check != null) && (!check.isEmpty())) {
	    			hasMoved = true;
	    			return;
	    		}
	    	}
	    	Position oldCoords = new Position(x_coord, y_coord);
	    	ArrayList<Critter> entities = population.get(oldCoords);
	    	entities.remove(this);
	    	if (entities.size() <= 0) {
	    		population.remove(oldCoords);
	    	}
	    	for (int i = 0; i < 2; i++) {
	    		int[] temp = move(direction, x_coord, y_coord);
	    		x_coord = temp[0];
	    		y_coord = temp[1];
	    	}
	    	spawn(this);
    	}
    	hasMoved = true;
    }
    /**
     * Takes in direction, and x and y coordinates as parameters
     * Returns an int array consisting of the new coordinates based on the direction given
     * Also can wrap around screen if it moves outside the bounds
     * @param direction
     * @param x_coord
     * @param y_coord
     * @return
     */
    private static int[] move(int direction, int x_coord, int y_coord) {
    	switch (direction) {
		case 0:
			x_coord += 1;
			break;
		case 1:
			x_coord += 1;
			y_coord -= 1;
			break;
		case 2:
			y_coord -= 1;
			break;
		case 3:
			x_coord -= 1;
			y_coord -= 1;
			break;
		case 4:
			x_coord -= 1;
			break;
		case 5:
			x_coord -= 1;
			y_coord += 1;
			break;
		case 6:
			y_coord += 1;
			break;
		case 7:
			x_coord += 1;
			y_coord += 1;
			break;
    	}
    	if (x_coord < 0) {
    		x_coord = Params.WORLD_WIDTH - 1;
    	}
    	else if (x_coord > Params.WORLD_WIDTH - 1) {
    		x_coord = 0;
    	}
    	if (y_coord < 0) {
    		y_coord = Params.WORLD_HEIGHT - 1;
    	}
    	else if (y_coord > Params.WORLD_HEIGHT - 1) {
    		y_coord = 0;
    	}
    	int[] temp = {x_coord, y_coord};
    	return temp;
    }
    /**
     * Takes an offspring Critter and initializes its postion based on direction relative to parent
     * offspring must be a valid, instantiated critter.
     * Parent must have enough energy or else it returns
     * Subtracts from parents energy and adds to child energy, and get new coordinates by calling move()
     * Add offspring to babies list, which will be promoted at end of time step
     * @param offspring
     * @param direction
     */
    protected final void reproduce(Critter offspring, int direction) {
        // TODO: Complete this method
    	if (this.energy < Params.MIN_REPRODUCE_ENERGY) {
    		return;
    	}
    	offspring.energy = this.energy/2;
    	this.energy -= offspring.energy;
    	
    	
    	int[] coords = offspring.move(direction, this.x_coord, this.y_coord);
    	offspring.x_coord = coords[0];
    	offspring.y_coord = coords[1];
    	
    	babies.add(offspring);
    	
    	
    }

    /**
     * The TestCritter class allows some critters to "cheat". If you
     * want to create tests of your Critter model, you can create
     * subclasses of this class and then use the setter functions
     * contained here.
     * <p>
     * NOTE: you must make sure that the setter functions work with
     * your implementation of Critter. That means, if you're recording
     * the positions of your critters using some sort of external grid
     * or some other data structure in addition to the x_coord and
     * y_coord functions, then you MUST update these setter functions
     * so that they correctly update your grid/data structure.
     */
    static abstract class TestCritter extends Critter {

        protected void setEnergy(int new_energy_value) {
            super.energy = new_energy_value;
        }

        protected void setX_coord(int new_x_coord) {
        	Position oldCoords = new Position(super.x_coord, super.y_coord);
        	ArrayList<Critter> entities = population.get(oldCoords);
        	entities.remove(this);
        	if (entities.size() <= 0) {
        		population.remove(oldCoords);
        	}
        	super.x_coord = new_x_coord;
            Position coord = new Position(super.x_coord, super.y_coord);
            spawn(this);
            
        }

        protected void setY_coord(int new_y_coord) {
        	Position oldCoords = new Position(super.x_coord, super.y_coord);
        	ArrayList<Critter> entities = population.get(oldCoords);
        	entities.remove(this);
        	if (entities.size() <= 0) {
        		population.remove(oldCoords);
        	}
        	super.y_coord = new_y_coord;
        	Position coord = new Position(super.x_coord, super.y_coord);
            spawn(this);
        }

        protected int getX_coord() {
            return super.x_coord;
        }

        protected int getY_coord() {
            return super.y_coord;
        }

        
        /**
         * This method getPopulation has to be modified by you if you
         * are not using the population ArrayList that has been
         * provided in the starter code.  In any case, it has to be
         * implemented for grading tests to work.
         * @return popList
         */
        protected static List<Critter> getPopulation() {
        	List<Critter> popList = new ArrayList<Critter>();
        	for (ArrayList<Critter> entities : population.values()) {
        		popList.addAll(entities);
        	}
            return popList;
        }

        /**
         * This method getBabies has to be modified by you if you are
         * not using the babies ArrayList that has been provided in
         * the starter code.  In any case, it has to be implemented
         * for grading tests to work.  Babies should be added to the
         * general population at either the beginning OR the end of
         * every timestep.
         * @return babies
         */
        protected static List<Critter> getBabies() {
            return babies;
        }
    }
}
