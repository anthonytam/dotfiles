/**
 * 
 * @author csc207student 
 * Represents a Jug. The jug has a max capacity and a current capacity. The
 * capacity of the jug can be modified and the jug can return if it is full.
 */
public class Jug {
	private int capacity;
	private int currentVolume;
	
	/**
	 * Creates a new jug in the jug game. The jug can be spilled into other jugs
	 * and has a set capacity.
	 * @param capacity An integer representing the capacity of the jug
	 */
	public Jug(int capacity) {
		this.capacity = capacity;
		this.currentVolume = 0;
	}
	
	/**
	 * Creates a new jug in the jug game. The jug can be spilled into other jugs
	 * and has a set capacity.
	 * @param capacity An integer representing the capacity of the jug
	 * @param currentVolume An integer representing the initial capacity of the jug
	 */
	public Jug(int capacity, int currentVolume) {
		this.capacity = capacity;
		this.currentVolume = currentVolume;
	}
	
	/**
	 * Gets the max capacity of the jug
	 * @return An integer containing the capacity of the jug
	 */
	public int getCapacity() {
		return this.capacity;
	}
	
	/**
	 * Gets the current volume of the jug
	 * @return An integer containing the current volume of the jug
	 */
	public int getCurrentVolume() {
		return this.currentVolume;
	}
	
	/**
	 * Finds if the jug is currently at full capacity
	 * @return A boolean value representing if the jug is currently full
	 */
	public boolean isFull() {
		return this.getCurrentVolume() == this.getCapacity();
	}
	
	/**
	 * Change the current volume of the jug. Positive values increase the volume,
	 * negative values decrease the volume.
	 * @param netChange the amount to change the volume by
	 * @return A boolean value if the change was successful
	 */
	public boolean changeVolume(int netChange) {
		if (this.getCurrentVolume() + netChange <= this.getCapacity() && this.getCurrentVolume() + netChange >= 0) {
			this.currentVolume += netChange;
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * Returns a string representation of the status of the jug.
	 */
	@Override
	public String toString() {
		return this.getCurrentVolume() + "/" + this.getCapacity();
	}
}
