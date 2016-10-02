
/**
 * Handles execution of an if statement
 * @author Craig Ferris
 *
 */
public class IfStack
{
	private IfStackItem[] stack;
	private int pointer;
	
	/**
	 * Creates a new If structure
	 */
	public IfStack()
	{
		stack = new IfStackItem[8192];
		pointer = 0;
	}
	
	/**
	 * Adds the condition of an if statement
	 * @param condition The condition
	 */
	public void push(String condition)
	{
		if(pointer + 1 >= 8192)
		{
			throw new IndexOutOfBoundsException();
		}
		stack[pointer++] = new IfStackItem(condition);
	}
	
	/**
	 * Returns the if statement that is currently in scope
	 * @return
	 */
	public IfStackItem peek()
	{
		if(pointer <= 0)
		{
			throw new IndexOutOfBoundsException();
		}
		return stack[pointer - 1];
	}
	
	/**
	 * Removes the if statement currently in scope from the stack
	 */
	public void pop()
	{
		if(pointer <= 0 )
		{
			throw new IndexOutOfBoundsException();
		}
		stack[--pointer] = null;
	}
	
	/**
	 * Returns true if the if stack is empty and false otherwise
	 * @return Returns true if the if stack is empy and false otherwise
	 */
	public boolean isEmpty()
	{
		return pointer == 0;
	}
}
