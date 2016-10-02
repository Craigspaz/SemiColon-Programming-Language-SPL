
/**
 * A stack used to store nested loops
 * @author Craig Ferris
 *
 */
public class LoopStack
{
	private LoopStackItem[] stack;
	private int pointer;
	
	/**
	 * Creates a new LoopStack
	 */
	public LoopStack()
	{
		stack = new LoopStackItem[8192];
		pointer = 0;
	}
	
	/**
	 * Adds a nested loop
	 * @param condition The condition of the nested loop
	 * @param progressString The incrementor / decrementor etc of the loop
	 */
	public void push(String condition, String progressString)
	{
		if(pointer + 1 >= 8192)
		{
			throw new IndexOutOfBoundsException();
		}
		stack[pointer++] = new LoopStackItem(condition,progressString);
	}
	
	/**
	 * Adds a nested loop
	 * @param item The condition of the nested loop
	 */
	public void push(String item)
	{
		if(pointer + 1 >= 8192)
		{
			throw new IndexOutOfBoundsException();
		}
		stack[pointer++] = new LoopStackItem(item);
	}
	
	/**
	 * Returns the loop currently in scope
	 * @return Returns the loop currently in scope
	 */
	public LoopStackItem peek()
	{
		if(pointer <= 0)
		{
			throw new IndexOutOfBoundsException();
		}
		return stack[pointer - 1];
	}
	
	/**
	 * Removes the loop currently in scope
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
	 * Returns true if the stack is empty
	 * @return Returns true if the stack is empty
	 */
	public boolean isEmpty()
	{
		return pointer == 0;
	}
}
