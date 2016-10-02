import java.util.ArrayList;

/**
 * Stores the value of an individual if statement
 * @author Craig Ferris
 *
 */
public class IfStackItem
{
	private String condition;
	private ArrayList<Integer> varAddress;
	
	/**
	 * Creates a new if statement
	 * @param condition The condition of the if statement
	 */
	public IfStackItem(String condition)
	{
		this.condition = condition;
		varAddress = new ArrayList<Integer>();
	}
	
	/**
	 * Returns the condition of the if statement
	 * @return Returns the condition of the if statement
	 */
	public String getCondition()
	{
		return condition;
	}
	
	/**
	 * Stores the address of a variable declared in the if statement
	 * @param address The address to store
	 */
	public void addVarAddress(int address)
	{
		varAddress.add(address);
	}
	
	/**
	 * Returns the address of variables declared inside of the if statement
	 * @return Returns the addresses of variables declared inside of the if statement
	 */
	public ArrayList<Integer> getVarAddresses()
	{
		return varAddress;
	}
}
