
public class LoopStack
{
	private LoopStackItem[] stack;
	private int pointer;
	
	public LoopStack()
	{
		stack = new LoopStackItem[8192];
		pointer = 0;
	}
	
	public void push(String condition, String progressString)
	{
		if(pointer + 1 >= 8192)
		{
			throw new IndexOutOfBoundsException();
		}
		stack[pointer++] = new LoopStackItem(condition,progressString);
	}
	
	public void push(String item)
	{
		if(pointer + 1 >= 8192)
		{
			throw new IndexOutOfBoundsException();
		}
		stack[pointer++] = new LoopStackItem(item);
	}
	
	public LoopStackItem peek()
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
