
public class IfStack
{
	private IfStackItem[] stack;
	private int pointer;
	
	public IfStack()
	{
		stack = new IfStackItem[8192];
		pointer = 0;
	}
	
	public void push(String condition)
	{
		if(pointer + 1 >= 8192)
		{
			throw new IndexOutOfBoundsException();
		}
		stack[pointer++] = new IfStackItem(condition);
	}
	
	public IfStackItem peek()
	{
		if(pointer <= 0)
		{
			throw new IndexOutOfBoundsException();
		}
		return stack[pointer - 1];
	}
	
	public void pop()
	{
		if(pointer <= 0 )
		{
			throw new IndexOutOfBoundsException();
		}
		stack[--pointer] = null;
	}
	
	public boolean isEmpty()
	{
		return pointer == 0;
	}
}
