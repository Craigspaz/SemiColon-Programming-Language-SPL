
public class Function
{
	private StringBuilder functionCode;
	private String functionName;
	private String[] parameters;
	
	public Function(String functionName, String[] parameters)
	{
		this.functionName = functionName;
		functionCode = new StringBuilder();
		this.parameters = parameters;
	}
	
	public void addCode(String code)
	{
		functionCode.append(code);
	}
	
	public String getFunctionName()
	{
		return functionName;
	}
	
	public String getFunctionCode()
	{
		return functionCode.toString();
	}
	
	public String[] getParameters()
	{
		return parameters;
	}
}
