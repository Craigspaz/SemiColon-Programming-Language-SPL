
public class ProgramStack 
{
	public static final int PROGRAM_STACK_SIZE = 8192;
	private ProgramStackFrame[] stack;
	private int pointer;
	
	public ProgramStack()
	{
		stack = new ProgramStackFrame[8192];
		pointer = 0;
	}
	
	public void push(ProgramStackFrame frame)
	{
		if(pointer + 1 >= PROGRAM_STACK_SIZE)
		{
			throw new IndexOutOfBoundsException();
		}
		stack[pointer++] = frame;
	}
	
	public ProgramStackFrame peek()
	{
		return stack[pointer - 1];
	}
	
	public ProgramStackFrame pop()
	{
		if(pointer <= 0)
		{
			throw new IndexOutOfBoundsException();
		}
		return stack[pointer--];
	}
}
