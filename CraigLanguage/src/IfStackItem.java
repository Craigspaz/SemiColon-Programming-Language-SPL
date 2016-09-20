import java.util.ArrayList;


public class IfStackItem
{
	private String condition;
	private ArrayList<Integer> varAddress;
	
	public IfStackItem(String condition)
	{
		this.condition = condition;
		varAddress = new ArrayList<Integer>();
	}
	
	public String getCondition()
	{
		return condition;
	}
	
	public void addVarAddress(int address)
	{
		varAddress.add(address);
	}
	
	public ArrayList<Integer> getVarAddresses()
	{
		return varAddress;
	}
}
