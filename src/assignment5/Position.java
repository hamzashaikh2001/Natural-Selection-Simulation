package assignment5;
/*
 * CRITTERS Critter.java
 * EE422C Project 4 submission by
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
import java.util.Objects;
/**
 * Holds an x and y coordinate to represent a position on the coordinate
 * Contains an equals function to see if 2 postions have equal x and y values
 * Contains hashCode() so it can be implemented in a hashMap.
 * @author Prithvi and Hamza
 *
 */
public class Position {
	private int xValue;
	private int yValue;
	private int key;
	
	/**
	 * @return an integer with the hashCode
	 */
	@Override
	public int hashCode() {
		return Objects.hash(xValue, yValue);
	}

	
	/**
	 * @return true if x's and y's are equal
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Position other = (Position) obj;
		return xValue == other.xValue && yValue == other.yValue;
	}

	public Position(int xValue, int yValue) {
		this.xValue = xValue;
		this.yValue = yValue;
		key = hashCode();
	}
}