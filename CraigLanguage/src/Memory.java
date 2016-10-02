/**
 * Stores variables values in an addressed list
 * @author Craig Ferris
 *
 */
public class Memory
{
	private static final int STACK_SIZE = 1024;
	private String[] stack;

	/**
	 * Creates a new Memory
	 */
	public Memory()
	{
		stack = new String[STACK_SIZE];
	}

	/**
	 * Adds a new variable
	 * @param value The value the variable is set to
	 * @param address The address of the new variable
	 * @return Returns true if there was no issue creating the variable
	 */
	public boolean addVariable(String value, int address)
	{
		if (address >= STACK_SIZE)
		{
			throw new IndexOutOfBoundsException();
		}
		if (stack[address] != null)
		{
			return false;
		}
		stack[address] = value;
		return true;
	}

	/**
	 * Removes a variable from memory based on its address
	 * @param address The address of the variable to free
	 */
	public void removeFromStack(int address)
	{
		if (address >= STACK_SIZE)
		{
			throw new IndexOutOfBoundsException();
		}
		stack[address] = null;
	}

	/**
	 * Returns the value of memory at a particular address
	 * @param address The address to get the value at
	 * @return Returns teh value of memory at a particular address
	 */
	public String getAtAddress(int address)
	{
		if (address >= STACK_SIZE)
		{
			throw new IndexOutOfBoundsException();
		}
		return stack[address];
	}

	/**
	 * Sets the value of memory at a particular address to a new value
	 * @param address The address to modify
	 * @param value The value to set the the variable to
	 * @return Returns true if there was no issue setting the value
	 */
	public boolean setValueAtAddress(int address, String value)
	{
		if (address >= STACK_SIZE)
		{
			throw new IndexOutOfBoundsException();
		}
		stack[address] = value;
		return true;
	}

	/**
	 * Returns the next available address
	 * @return Returns the new available address
	 */
	public int getNextFreeAddress()
	{
		for (int i = 0; i < STACK_SIZE; i++)
		{
			if (stack[i] == null)
			{
				return i;
			}
		}
		return -1;
	}
}
