import java.util.ArrayList;

/**
 * Used to pass variables into another ProgramStackFrame
 * @author Craig Ferris
 *
 */
public class RegisterKeys 
{
	private ArrayList<Variable> registryKeys;
	
	/**
	 * Creates a new RegisterKeys using the list of variables passed in
	 * @param registryKeys The list of variables
	 */
	public RegisterKeys(ArrayList<Variable> registryKeys)
	{
		this.registryKeys = registryKeys;
	}
	
	/**
	 * Returns the registeryKey at a particular index
	 * @param index The index to return
	 * @return Returns the registerKey at the particular index
	 */
	public Variable getAt(int index)
	{
		return registryKeys.get(index);
	}
	
	/**
	 * Returns the number of variables stored
	 * @return Returns the number of variables stored
	 */
	public int length()
	{
		return registryKeys.size();
	}
	
	/**
	 * Sets the value of a variable passed in as a registryKey to value
	 * @param index The index of the registrKey
	 * @param value The value to set the variable to
	 * @param memory A pointer to memory
	 */
	public void setRegistryKey(int index, String value, Memory memory)
	{
		memory.setValueAtAddress(getAt(index).getAddress(), value);
	}
}
