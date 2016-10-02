
/**
 * A utilities class to handle methods needed throughout the program
 * @author Craig Ferris
 *
 */
public class Util
{
	/**
	 * Returns if the string passed in is a number
	 * @param str The string to check
	 * @return Returns true if the string is a number or false if the string is not a number
	 */
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
	
	/**
	 * Returns if the string passed in is a floating point number
	 * @param str The string to check
	 * @return Returns if the string is a floating point number
	 */
	public static boolean isFloatingPoint(String str)
	{
		return isNumeric(str) && str.contains(".");
	}
}
