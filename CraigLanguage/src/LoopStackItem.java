import java.util.ArrayList;


/**
 * Stores the functionality of a looping structure
 * @author Craig Ferris
 *
 */
public class LoopStackItem
{
	private String condition;
	private String progressString;
	private StringBuilder codeToExecute;
	private ArrayList<Integer> varAddress;
	
	/**
	 * Creates a new LoopStackItem
	 * @param condition The condition of the loop
	 */
	public LoopStackItem(String condition)
	{
		this.condition = condition;
		codeToExecute = new StringBuilder();
		varAddress = new ArrayList<Integer>();
	}
	
	/**
	 * Creates a new LoopStackItem
	 * @param condition The condition of the loop
	 * @param progressString If in a for loop is the incrementor / decrementor etc
	 */
	public LoopStackItem(String condition, String progressString)
	{
		this(condition);
		this.progressString = progressString;
	}
	
	/**
	 * Returns the condition of the loop
	 * @return Returns the condition of the loop
	 */
	public String getCondition()
	{
		return condition;
	}
	
	/**
	 * Returns the code of the loop
	 * @return Returns the code of the loop
	 */
	public String codeToExecute()
	{
		return codeToExecute.toString();
	}
	
	/**
	 * Adds code to the looping structure
	 * @param code The code to add to the looping structure
	 */
	public void addCodeToExecute(String code)
	{
		codeToExecute.append(code);
	}
	
	/**
	 * Stores the address of a variable declared inside of the loop
	 * @param address The address of the variable declared inside of the loop
	 */
	public void addVarAddress(int address)
	{
		varAddress.add(address);
	}
	
	/**
	 * Returns a list of all of the variables declared inside of the loop
	 * @return Returns a list of all of the variables declared inside of the loop
	 */
	public ArrayList<Integer> getVarAddresses()
	{
		return varAddress;
	}
	
	/**
	 * Sets the incrementor / decrementor etc of the loop
	 * @param prog The incrementor / decrementor etc of the loop
	 */
	public void setProgress(String prog)
	{
		progressString = prog;
	}
	
	/**
	 * Returns the incrementor / decrementor etc of the loop
	 * @return Returns the incrementor / decrementor etc of the loop
	 */
	public String getProgressString()
	{
		return progressString;
	}
}
