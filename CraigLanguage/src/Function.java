
/**
 * Stores the code and parameters of a declared function
 * @author Craig Ferris
 *
 */
public class Function
{
	private StringBuilder functionCode;
	private String functionName;
	private String[] parameters;
	
	/**
	 * Creates a new function
	 * @param functionName The name of the function. The same name that is used to call the function
	 * @param parameters The parameters the function takes
	 */
	public Function(String functionName, String[] parameters)
	{
		this.functionName = functionName;
		functionCode = new StringBuilder();
		this.parameters = parameters;
	}
	
	/**
	 * Adds code to be stored for execution of the function later
	 * @param code The code to be stored
	 */
	public void addCode(String code)
	{
		functionCode.append(code);
	}
	
	/**
	 * Returns the function name
	 * @return Returns the function name
	 */
	public String getFunctionName()
	{
		return functionName;
	}
	
	/**
	 * Returns the function's code
	 * @return Returns the functions code
	 */
	public String getFunctionCode()
	{
		return functionCode.toString();
	}
	
	/**
	 * Returns the parameters of the function
	 * @return Returns the parameters of the function
	 */
	public String[] getParameters()
	{
		return parameters;
	}
}
