import java.util.ArrayList;

/**
 * Stores all of the variables currently in scope
 * @author Craig Ferris
 *
 */
public class ProgramStackFrame 
{
	private ArrayList<Variable> variables;
	private RegisterKeys keys;
	
	/**
	 * Creates a new ProgramStackFrame using registerkeys from the previous stackframe
	 * @param keys The register keys
	 */
	public ProgramStackFrame(RegisterKeys keys)
	{
		this.keys = keys;
		variables = new ArrayList<Variable>();
	}
	
	/**
	 * Adds a variable to the frame
	 * @param variable The variable to add
	 */
	public void addVariable(Variable variable)
	{
		variables.add(variable);
	}
	
	/**
	 * Returns a list of all variables declared in the frame
	 * @return Returns a list of all variables declared in the frame
	 */
	public ArrayList<Variable> getVariables()
	{
		return variables;
	}
	
	/**
	 * Returns a variable declared in the frame based off its name
	 * @param name The name of the variable
	 * @return Returns a variable declared in the frame based off its name
	 */
	public Variable getVariableByName(String name)
	{
		for(Variable v : variables)
		{
			if(v.getName().equals(name))
			{
				return v;
			}
		}
		return null;
	}
	
	/**
	 * Returns a variable declared in the frame based on its address
	 * @param address The address of the variable to return
	 * @return Returns a variable declared in the frame based on its address
	 */
	public Variable getVariableByAddress(int address)
	{
		for(Variable v : variables)
		{
			if(v.getAddress() == address)
			{
				return v;
			}
		}
		return null;
	}
	
	/**
	 * Frees the memory of the variables declared in frame
	 * @param memory A pointer to memory
	 */
	public void freeStackFrameMemory(Memory memory)
	{
		for(Variable i : variables)
		{
			memory.removeFromStack(i.getAddress());
		}
	}
	
	/**
	 * Clears the memory of registrykeys
	 * @param memory A pointer to memory
	 */
	public void freeRegistryKeys(Memory memory)
	{
		for(int i = 0; i < keys.length(); i++)
		{
			memory.removeFromStack(keys.getAt(i).getAddress());
		}
	}
	
	/**
	 * Fress the memory of a variable at a particular address
	 * @param address The address of the memory to free
	 * @param memory A pointer to memory
	 */
	public void freeVariableAtAddress(int address, Memory memory)
	{
		for(Variable v : variables)
		{
			if(v.getAddress() == address)
			{
				variables.remove(v);
				break;
			}
		}
		memory.removeFromStack(address);
	}
	
	/**
	 * Return true if there is a variable with the passed in name
	 * @param variable The name of the variable to look for
	 * @return Return true if there is a variable with the passed in name
 	 */
	public boolean contains(String variable)
	{
		for(Variable v : variables)
		{
			if(v.getName().equals(variable))
			{
				return true;
			}
		}
		return false;
	}
}
