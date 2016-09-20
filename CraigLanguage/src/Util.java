
public class Util
{
	public static boolean isNumeric(String str)
	{
		boolean firstChar = true;
		boolean foundDecimal = false;
		for(char c : str.toCharArray())
		{
			if(!Character.isDigit(c))
			{
				if(firstChar && c == '-')
				{
					continue;
				}
				if(c == '.' && !foundDecimal)
				{
					foundDecimal = true;
					continue;
				}
				return false;
			}
			if(firstChar)
			{
				firstChar = false;
			}
		}
		return true;
	}
}
