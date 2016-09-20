public class Memory
{
	private static final int STACK_SIZE = 1024;
	private String[] stack;

	public Memory()
	{
		stack = new String[STACK_SIZE];
	}

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

	public void removeFromStack(int address)
	{
		if (address >= STACK_SIZE)
		{
			throw new IndexOutOfBoundsException();
		}
		stack[address] = null;
	}

	public String getAtAddress(int address)
	{
		if (address >= STACK_SIZE)
		{
			throw new IndexOutOfBoundsException();
		}
		return stack[address];
	}

	public boolean setValueAtAddress(int address, String value)
	{
		if (address >= STACK_SIZE)
		{
			throw new IndexOutOfBoundsException();
		}
		stack[address] = value;
		return true;
	}

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
