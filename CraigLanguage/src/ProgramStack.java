
/**
 * A stack for the program stack frames
 * @author Craig Ferris
 *
 */
public class ProgramStack 
{
	public static final int PROGRAM_STACK_SIZE = 8192;
	private ProgramStackFrame[] stack;
	private int pointer;
	
	/**
	 * Creates a new ProgramStack
	 */
	public ProgramStack()
	{
		stack = new ProgramStackFrame[8192];
		pointer = 0;
	}
	
	/**
	 * Adds a new ProgramStackFrame
	 * @param frame The frame to add to the stack
	 */
	public void push(ProgramStackFrame frame)
	{
		if(pointer + 1 >= PROGRAM_STACK_SIZE)
		{
			throw new IndexOutOfBoundsException();
		}
		stack[pointer++] = frame;
	}
	
	/**
	 * Returns the top of the stack
	 * @return Returns the top of the stack
	 */
	public ProgramStackFrame peek()
	{
		return stack[pointer - 1];
	}
	
	/**
	 * Removes the top of the stack
	 * @return Returns the top of the stack
	 */
	public ProgramStackFrame pop()
	{
		if(pointer <= 0)
		{
			throw new IndexOutOfBoundsException();
		}
		return stack[pointer--];
	}
}
