import java.util.ArrayList;


public class LoopStackItem
{
	private String condition;
	private String progressString;
	private StringBuilder codeToExecute;
	private ArrayList<Integer> varAddress;
	
	public LoopStackItem(String condition)
	{
		this.condition = condition;
		codeToExecute = new StringBuilder();
		varAddress = new ArrayList<Integer>();
	}
	
	public LoopStackItem(String condition, String progressString)
	{
		this(condition);
		this.progressString = progressString;
	}
	
	public String getCondition()
	{
		return condition;
	}
	
	public String codeToExecute()
	{
		return codeToExecute.toString();
	}
	
	public void addCodeToExecute(String code)
	{
		codeToExecute.append(code);
	}
	
	public void addVarAddress(int address)
	{
		varAddress.add(address);
	}
	
	public ArrayList<Integer> getVarAddresses()
	{
		return varAddress;
	}
	
	public void setProgress(String prog)
	{
		progressString = prog;
	}
	
	public String getProgressString()
	{
		return progressString;
	}
}
