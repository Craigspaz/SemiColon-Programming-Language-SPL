import java.util.ArrayList;

public class ProgramStackFrame 
{
	private ArrayList<Variable> variables;
	private RegisterKeys keys;
	
	public ProgramStackFrame(RegisterKeys keys)
	{
		this.keys = keys;
		variables = new ArrayList<Variable>();
	}
	
	public void addVariable(Variable variable)
	{
		variables.add(variable);
	}
	
	public ArrayList<Variable> getVariables()
	{
		return variables;
	}
	
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
	
	public void freeStackFrameMemory(Memory memory)
	{
		for(Variable i : variables)
		{
			memory.removeFromStack(i.getAddress());
		}
	}
	
	public void freeRegistryKeys(Memory memory)
	{
		for(int i = 0; i < keys.length(); i++)
		{
			memory.removeFromStack(keys.getAt(i).getAddress());
		}
	}
	
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
