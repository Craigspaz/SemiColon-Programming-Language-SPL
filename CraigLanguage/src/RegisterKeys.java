import java.util.ArrayList;

public class RegisterKeys 
{
	private ArrayList<Variable> registryKeys;
	
	public RegisterKeys(ArrayList<Variable> registryKeys)
	{
		this.registryKeys = registryKeys;
	}
	
	public Variable getAt(int index)
	{
		return registryKeys.get(index);
	}
	
	public int length()
	{
		return registryKeys.size();
	}
	
	public void setRegistryKey(int index, String value, Memory memory)
	{
		memory.setValueAtAddress(getAt(index).getAddress(), value);
	}
}
