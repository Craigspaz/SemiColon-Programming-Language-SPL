
/**
 * Maps a memory address to the variable name
 * @author Craig Ferris
 *
 */
public class Variable 
{
	private int address;
	private String name;
	
	/**
	 * Creates a new variable
	 * @param address The memory address of the variable
	 * @param name The name in the code of the variable
	 */
	public Variable(int address, String name)
	{
		this.address = address;
		this.name = name;
	}
	
	/**
	 * Changes the address the variable points to
	 * @param address The memory address to point to
	 */
	public void setAddress(int address)
	{
		this.address = address;
	}
	
	/**
	 * Returns the memory address the variable is pointing to
	 * @return Returns the memory address the variable is pointing to
	 */
	public int getAddress()
	{
		return address;
	}
	
	/**
	 * Returns the name of the variable
	 * @return Returns the name of the variable
	 */
	public String getName()
	{
		return name;
	}
}
