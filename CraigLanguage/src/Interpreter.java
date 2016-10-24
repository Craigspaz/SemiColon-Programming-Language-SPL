import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Pattern;

public class Interpreter
{

	public static void main(String[] args)
	{
		// TEST CODE
		//new Interpreter("./testFolder/test.cr");
		new Interpreter("./testFolder/mathtest.cr");
	}

	private ProgramStack stack;
	private Memory memory;
	private LoopStack loopStack;
	private IfStack ifStack;
	private ArrayList<Function> declaredFunctions;
	private Function declareFunc;

	private String previousCodeExecuted = "";

	/**
	 * Creates a new Interpreter that interprets a file
	 * 
	 * @param filename
	 *            The file to interpret
	 */
	public Interpreter(String filename)
	{
		stack = new ProgramStack();

		ProgramStackFrame mainFrame = new ProgramStackFrame(null);
		stack.push(mainFrame);
		memory = new Memory();
		loopStack = new LoopStack();
		ifStack = new IfStack();
		declaredFunctions = new ArrayList<Function>();

		/** READS CODE FROM FILE AND EXECUTES IT **/

		String code = readFile(filename);
		if(code == null)
		{
			System.out.println("Error reading file!");
		}
		execute(code, false);
	}
	
	/**
	 * Reads in a file and handles comments
	 * @param filename The filename to parse
	 * @return Returns a string with the code in the file
	 */
	private String readFile(String filename)
	{
		try
		{
			Scanner scanner = new Scanner(new File(filename));
			StringBuilder builder = new StringBuilder();
			while(scanner.hasNextLine())
			{
				String line = scanner.nextLine();
				if(!line.startsWith("//"))
				{
					builder.append(line);
				}
			}
			scanner.close();
			return builder.toString();
		}
		catch (FileNotFoundException e)
		{
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Creates a new Interpreter that interprets a String of code
	 * 
	 * @param code
	 *            The code to interpret
	 * @param loop
	 *            Is True if in a loop and False if not in a loop
	 */
	public Interpreter(String code, boolean loop)
	{
		execute(code, loop);
	}

	/**
	 * Calculates the value of arithmetic operations : Helper method
	 * 
	 * @param toProcess
	 *            The String of arithmetic operations to evaluate
	 * @return Returns a String representation of the result
	 */
	private String calculateValue(String toProcess)
	{

		if (toProcess.contains("~"))
		{
			String variable = toProcess.split("\\~")[1].trim();
			Variable findVar = stack.peek().getVariableByName(variable);
			return memory.getAtAddress(Integer.parseInt(memory.getAtAddress(findVar.getAddress())));
		}
		else if (toProcess.contains("@"))
		{
			String variable = toProcess.split("\\@")[1].trim();
			Variable findVar = stack.peek().getVariableByName(variable);
			return Integer.toString(findVar.getAddress());
		}
		else if(toProcess.contains("[") && toProcess.contains("]"))
		{
			int index = Integer.parseInt(toProcess.substring(toProcess.indexOf("[") + 1, toProcess.indexOf("]")));
			
			String variable = toProcess.split("\\[")[0].trim();
			String variableMem = memory.getAtAddress(stack.peek().getVariableByName(variable).getAddress());
			if(variableMem.startsWith("{") && variableMem.endsWith("}"))
			{
				String[] array = variableMem.substring(1, variableMem.length() - 1).trim().split(",");
				return array[index].trim();
			}
			else
			{
				return variableMem.charAt(index) + "";
			}
		}
		else if (toProcess.contains("*"))
		{
			String[] parts = toProcess.split("\\*");
			int product = 1;
			float fproduct = 1.0f;
			boolean isFProd = false;
			for (String p : parts)
			{
				String r = calculateValue(p.trim());
				if (Util.isFloatingPoint(r))
				{
					float v = Float.parseFloat(r);
					fproduct *= v;
					isFProd = true;
				}
				else if (Util.isNumeric(r))
				{
					int v = Integer.parseInt(r);
					product *= v;
				}
			}

			if (isFProd)
			{
				return Float.toString(fproduct);
			}
			return Integer.toString(product);
		}
		else if (toProcess.contains("%"))
		{
			String[] parts = toProcess.split("\\%");
			int mod = 1;
			float fmod = 1.0f;
			boolean isFMod = false;
			boolean firstNum = true;
			for (String p : parts)
			{
				String r = calculateValue(p.trim());
				if (Util.isFloatingPoint(r))
				{
					float v = Float.parseFloat(r);
					if (firstNum)
					{
						fmod = v;
						firstNum = false;
					}
					else
					{
						fmod *= v;
					}
					isFMod = true;
				}
				else if (Util.isNumeric(r))
				{
					int v = Integer.parseInt(r);
					if (firstNum)
					{
						mod = v;
						firstNum = false;
					}
					else
					{
						mod %= v;
					}
				}
			}
			if (isFMod)
			{
				return Float.toString(fmod);
			}
			return Integer.toString(mod);
		}
		else if (toProcess.contains("/"))
		{
			String[] parts = toProcess.split("\\/");
			int quotient = 1;
			float fquotient = 1.0f;
			boolean isFQuot = false;
			boolean firstNum = true;
			for (String p : parts)
			{
				String r = calculateValue(p.trim());
				if (Util.isFloatingPoint(r))
				{
					float v = Float.parseFloat(r);
					if (firstNum)
					{
						fquotient = v;
						firstNum = false;
					}
					else
					{
						fquotient /= v;
					}
					isFQuot = true;
				}
				else if (Util.isNumeric(r))
				{
					int v = Integer.parseInt(r);
					if (firstNum)
					{
						quotient = v;
						firstNum = false;
					}
					else
					{
						quotient /= v;
					}
				}
			}
			if (isFQuot)
			{
				return Float.toString(fquotient);
			}
			return Integer.toString(quotient);
		}
		else if (toProcess.contains("+"))
		{
			String[] parts = toProcess.split("\\+");
			int sum = 0;
			float fsum = 0.0f;
			boolean isFSum = false;
			for (String p : parts)
			{
				String r = calculateValue(p.trim());
				if (Util.isFloatingPoint(r))
				{
					float v = Float.parseFloat(r);
					fsum += v;
					isFSum = true;
				}
				else if (Util.isNumeric(r))
				{
					int v = Integer.parseInt(r);
					sum += v;
				}
			}
			if (isFSum)
			{
				return Float.toString(fsum);
			}
			return Integer.toString(sum);
		}
		else if (toProcess.contains("-"))
		{
			String[] parts = toProcess.split("\\-");
			int difference = 0;
			float fdifference = 0.0f;
			boolean isFDiff = false;
			boolean firstNum = true;
			for (String p : parts)
			{
				String r = calculateValue(p.trim());
				if (Util.isFloatingPoint(r))
				{
					float v = Float.parseFloat(r);
					if (firstNum)
					{
						fdifference = v;
						firstNum = false;
					}
					else
					{
						fdifference -= v;
					}
					isFDiff = true;
				}
				else if (Util.isNumeric(r))
				{
					int v = Integer.parseInt(r);
					if (firstNum)
					{
						difference = v;
						firstNum = false;
					}
					else
					{
						difference -= v;
					}
				}
			}
			if (isFDiff)
			{
				return Float.toString(fdifference);
			}
			return Integer.toString(difference);
		}
		else
		{
			if (Util.isNumeric(toProcess))
			{
				return toProcess;
			}
			else
			{
				Variable findVar = stack.peek().getVariableByName(toProcess);
				if (findVar != null)
				{
					String value = memory.getAtAddress(findVar.getAddress());
					return value;
				}
				else
				{
					// The String to be processed must be a string
					if (!toProcess.contains("\""))
					{
						System.out.println("Error: Unknown Value!");
					}
				}
			}
		}

		return toProcess;
	}

	/**
	 * Handles order of operations for arithmetic operations
	 * 
	 * @param toProcess
	 *            The String to process
	 * @return Returns the value calculated
	 */
	private String calculate(String toProcess)
	{
		String tmp = toProcess;
		while (tmp.contains("("))
		{
			int startIndex = tmp.indexOf("(");
			int tmpIndex = 0;
			while (tmpIndex != -1)
			{
				tmpIndex = tmp.indexOf("(", startIndex + 1);
				if (tmpIndex != -1)
				{
					startIndex = tmpIndex;
				}
			}
			int endIndex = tmp.indexOf(")", startIndex);
			String toCalculate = tmp.substring(startIndex + 1, endIndex);
			String calc = calculateValue(toCalculate);
			StringBuilder builder = new StringBuilder();
			if (startIndex != 0)
			{
				builder.append(tmp.substring(0, startIndex));
			}
			builder.append(calc);
			builder.append(tmp.substring(endIndex + 1));
			tmp = builder.toString();
		}

		if (Util.isNumeric(tmp))
		{
			return tmp;
		}
		else
		{
			return calculateValue(tmp);
		}
	}

	/**
	 * Creates a new variable
	 * 
	 * @param line
	 *            The line the variable is created on
	 */
	private void parseVariableCreationLine(String line)
	{
		String name = null;
		String value = null;
		if(line.contains("="))
		{
			name = line.substring(4, line.indexOf("=")).trim();
			value = line.substring(line.indexOf("=") + 1).trim();
		}
		
		if(name == null)
		{
			return;
		}
		
		if(value == null)
		{
			value = "0";
		}

		if(value.startsWith("{") || value.endsWith("}"))
		{
			// DO Nothing
		}
		else if (value.contains("\""))
		{
			String v = value.substring(1, value.length() - 1);
			value = v;
		}
		else
		{
			value = calculate(value);
		}
		int nextAddress = memory.getNextFreeAddress();
		if (memory.addVariable(value, nextAddress))
		{
			Variable newVariable = new Variable(nextAddress, name);
			stack.peek().addVariable(newVariable);
			if (!loopStack.isEmpty())
			{
				loopStack.peek().addVarAddress(nextAddress);
			}
		}
		else
		{
			// HANDLE MEMORY ADDRESS ALREADY IN USE
			System.out.println("ERROR MEMORY ADDRESS IN USE");
		}
	}

	/**
	 * Evaluates boolean statements
	 * 
	 * @param condition
	 *            The boolean expression to evaluate
	 * @return Returns true if the expression is true or false if the expression
	 *         is false
	 */
	public boolean checkCondition(String condition)
	{
		boolean isTrue = false;
		condition = condition.trim();

		if (condition.contains("("))
		{
			String tmp = condition;
			if (tmp.startsWith("if"))
			{
				tmp = tmp.substring(2);
			}
			else if (tmp.startsWith("else if"))
			{
				tmp = tmp.substring(7);
			}
			while (tmp.contains("("))
			{
				int startIndex = tmp.indexOf("(");
				int tmpIndex = 0;
				while (tmpIndex != -1)
				{
					tmpIndex = tmp.indexOf("(", startIndex + 1);
					if (tmpIndex != -1)
					{
						startIndex = tmpIndex;
					}
				}
				int endIndex = tmp.indexOf(")", startIndex);
				String toCalculate = tmp.substring(startIndex + 1, endIndex);
				boolean calc = checkCondition(toCalculate);
				StringBuilder builder = new StringBuilder();
				if (startIndex != 0)
				{
					builder.append(tmp.substring(0, startIndex));
				}
				builder.append(calc);
				builder.append(tmp.substring(endIndex + 1));
				tmp = builder.toString();
			}
			return checkCondition(tmp);
		}
		else if (condition.equals("true"))
		{
			return true;
		}
		else if (condition.equals("false"))
		{
			return false;
		}
		else if (condition.contains("&&"))
		{
			String[] parts = condition.split("\\&&");
			String first = parts[0].trim();
			String second = parts[1].trim();
			if (condition.contains("(") && condition.contains(")"))
			{
				first = parts[0].split("\\(")[1].trim();
				second = parts[1].split("\\)")[0].trim();
			}
			boolean f = checkCondition(first);
			boolean s = checkCondition(second);
			if (f && s)
			{
				return true;
			}
			else
			{
				return false;
			}

		}
		else if (condition.contains("||"))
		{
			String[] parts = condition.split(Pattern.quote("||"));
			String first = parts[0].trim();
			String second = parts[1].trim();
			if (condition.contains("(") && condition.contains(")"))
			{
				first = parts[0].split("\\(")[1].trim();
				second = parts[1].split("\\)")[0].trim();
			}
			boolean f = checkCondition(first);
			boolean s = checkCondition(second);
			if (f || s)
			{
				return true;
			}
			else
			{
				return false;
			}

		}
		else if (condition.contains(">="))
		{
			String[] parts = condition.split(">=");
			String first = parts[0].trim();
			String second = parts[1].trim();
			if (condition.contains("(") && condition.contains(")"))
			{
				first = parts[0].split("\\(")[1].trim();
				second = parts[1].split("\\)")[0].trim();
			}
			first = calculateValue(first);
			second = calculateValue(second);

			if (Util.isNumeric(first) && Util.isNumeric(second))
			{
				float f = Float.parseFloat(first);
				float sec = Float.parseFloat(second);
				if (f >= sec)
				{
					isTrue = true;
				}
				else
				{
					isTrue = false;
				}
			}
			else
			{
				System.out.println("ERROR if statement containing >= requires that both values are numeric values");
			}
		}
		else if (condition.contains("<="))
		{
			String[] parts = condition.split("<=");
			String first = parts[0].trim();
			String second = parts[1].trim();
			if (condition.contains("(") && condition.contains(")"))
			{
				first = parts[0].split("\\(")[1].trim();
				second = parts[1].split("\\)")[0].trim();
			}
			first = calculateValue(first);
			second = calculateValue(second);

			if (Util.isNumeric(first) && Util.isNumeric(second))
			{
				float f = Float.parseFloat(first);
				float sec = Float.parseFloat(second);
				if (f <= sec)
				{
					isTrue = true;
				}
				else
				{
					isTrue = false;
				}
			}
			else
			{
				System.out.println("ERROR if statement containing >= requires that both values are numeric values");
			}
		}
		else if (condition.contains("=="))
		{
			String[] parts = condition.split("==");
			String first = parts[0].trim();
			String second = parts[1].trim();
			if (condition.contains("(") && condition.contains(")"))
			{
				first = parts[0].split("\\(")[1].trim();
				second = parts[1].split("\\)")[0].trim();
			}
			first = calculateValue(first);
			second = calculateValue(second);
			if (first.equals(second))
			{
				isTrue = true;
			}
			else
			{
				isTrue = false;
			}
		}
		else if (condition.contains(">"))
		{
			String[] parts = condition.split(">");
			String first = parts[0].trim();
			String second = parts[1].trim();
			if (condition.contains("(") && condition.contains(")"))
			{
				first = parts[0].split("\\(")[1].trim();
				second = parts[1].split("\\)")[0].trim();
			}
			first = calculateValue(first);
			second = calculateValue(second);

			if (Util.isNumeric(first) && Util.isNumeric(second))
			{
				float f = Float.parseFloat(first);
				float sec = Float.parseFloat(second);
				if (f > sec)
				{
					isTrue = true;
				}
				else
				{
					isTrue = false;
				}
			}
			else
			{
				System.out.println("ERROR if statement containing >= requires that both values are numeric values");
			}
		}
		else if (condition.contains("<"))
		{
			String[] parts = condition.split("<");
			String first = parts[0].trim();
			String second = parts[1].trim();
			if (condition.contains("(") && condition.contains(")"))
			{
				first = parts[0].split("\\(")[1].trim();
				second = parts[1].split("\\)")[0].trim();
			}
			first = calculateValue(first);
			second = calculateValue(second);

			if (Util.isNumeric(first) && Util.isNumeric(second))
			{
				float f = Float.parseFloat(first);
				float sec = Float.parseFloat(second);
				if (f < sec)
				{
					isTrue = true;
				}
				else
				{
					isTrue = false;
				}
			}
			else
			{
				System.out.println("ERROR if statement containing >= requires that both values are numeric values");
			}
		}
		else
		{
			String value = calculateValue(condition);
			if (Util.isNumeric(value))
			{
				int num = Integer.parseInt(value);
				if (num == 0)
				{
					isTrue = false;
				}
				else
				{
					isTrue = true;
				}
			}
			else
			{
				isTrue = false;
			}
		}
		return isTrue;
	}

	/**
	 * Sets a variable equal to a value
	 * 
	 * @param trimmed
	 *            The String form of the value to calculate
	 */
	public void handleEquals(String trimmed)
	{
		trimmed = trimmed.trim();
		if (!trimmed.contains("=") || trimmed.equals(""))
		{
			return;
		}
		// Setting Variable to something
		String name = trimmed.substring(0, trimmed.indexOf("=")).trim();
		String value = trimmed.substring(trimmed.indexOf("=") + 1).trim();

		if(name.contains("[") && name.contains("]"))
		{
			String n = name.substring(0, name.indexOf("["));
			String i = name.substring(name.indexOf("[") + 1,name.indexOf("]"));
			int index = Integer.parseInt(i);
			String m = memory.getAtAddress(stack.peek().getVariableByName(n).getAddress());
			if(m.startsWith("{") && m.endsWith("}"))
			{
				String[] array = m.substring(1, m.length() - 1).split("\\,");
				array[index] = value;
				StringBuilder result = new StringBuilder();
				result.append("{");
				int counter = 0;
				for(String a : array)
				{
					result.append(a);
					if(counter + 1 != array.length)
					{
						result.append(",");
					}
					counter++;
				}
				result.append("}");
				memory.setValueAtAddress(stack.peek().getVariableByName(n).getAddress(), result.toString());
			}
			else
			{
				StringBuilder result = new StringBuilder();
				result.append(m.substring(0,index));
				result.append(value);
				result.append(m.substring(index + 1, m.length()));
				memory.setValueAtAddress(stack.peek().getVariableByName(n).getAddress(), result.toString());
			}
		}
		else if (value.contains("~"))
		{
			String variable = value.split("\\~")[1].trim();
			Variable findVar = stack.peek().getVariableByName(variable);
			value = memory.getAtAddress(Integer.parseInt(memory.getAtAddress(findVar.getAddress())));
		}
		else if (value.contains("@")) // Address Of operator
		{
			String variable = value.split("\\@")[1].trim();
			Variable findVar = stack.peek().getVariableByName(variable);
			value = Integer.toString(findVar.getAddress());
		}
		else if (value.contains("*"))
		{
			String[] splitAtPlus = value.split("\\*");
			int product = 1;
			float fproduct = 1.0f;
			boolean usingFloat = false;
			for (String a : splitAtPlus)
			{
				String b = a.trim();
				if (Util.isFloatingPoint(b))
				{
					float toAdd = Float.parseFloat(b);
					fproduct += toAdd;
					usingFloat = true;
				}
				else if (Util.isNumeric(b))
				{
					int toAdd = Integer.parseInt(b);
					product *= toAdd;
				}
				else
				{
					Variable findVar = stack.peek().getVariableByName(b);
					if (findVar != null)
					{
						String toAddStr = memory.getAtAddress(findVar.getAddress());
						if (Util.isFloatingPoint(toAddStr) && usingFloat)
						{
							float toAdd = Float.parseFloat(b);
							fproduct += toAdd;
						}
						else if (Util.isNumeric(toAddStr))
						{
							int toAdd = Integer.parseInt(toAddStr);
							product *= toAdd;
						}
						else
						{
							System.out.println("ERROR: Value is not numeric");
						}
					}
					else
					{
						// HANDLE Error
						System.out.println("ERROR CANNOT FIND VARIABLE");
					}
				}
			}
			if (usingFloat)
			{
				value = Float.toString(fproduct);
			}
			value = Integer.toString(product);
		}
		else if (value.contains("/"))
		{
			String[] splitAtPlus = value.split("\\/");
			int quotient = 1;
			float fquotient = 1.0f;
			boolean usingFloat = false;
			boolean firstNum = true;
			for (String a : splitAtPlus)
			{
				String b = a.trim();
				if (Util.isFloatingPoint(b))
				{
					float toAdd = Float.parseFloat(b);
					if (firstNum)
					{
						fquotient = toAdd;
						firstNum = false;
					}
					else
					{
						fquotient /= toAdd;
					}
					usingFloat = true;
				}
				else if (Util.isNumeric(b))
				{
					int toAdd = Integer.parseInt(b);
					if (firstNum)
					{
						quotient = toAdd;
						firstNum = false;
					}
					else
					{
						quotient /= toAdd;
					}
				}
				else
				{
					Variable findVar = stack.peek().getVariableByName(b);
					if (findVar != null)
					{
						String toAddStr = memory.getAtAddress(findVar.getAddress());
						if (Util.isFloatingPoint(toAddStr) && usingFloat)
						{
							float toAdd = Float.parseFloat(toAddStr);
							if (firstNum)
							{
								fquotient = toAdd;
								firstNum = false;
							}
							else
							{
								fquotient /= toAdd;
							}
						}
						else if (Util.isNumeric(toAddStr))
						{
							int toAdd = Integer.parseInt(toAddStr);
							if (firstNum)
							{
								quotient = toAdd;
								firstNum = false;
							}
							else
							{
								quotient /= toAdd;
							}
						}
						else
						{
							System.out.println("ERROR: Value is not numeric");
						}
					}
					else
					{
						// HANDLE Error
						System.out.println("ERROR CANNOT FIND VARIABLE");
					}
				}
			}
			if (usingFloat)
			{
				value = Float.toString(fquotient);
			}
			else
			{
				value = Integer.toString(quotient);
			}
		}
		else if (value.contains("%"))
		{
			String[] splitAtPlus = value.split("\\%");
			int mod = 1;
			float fmod = 1.0f;
			boolean usingFloat = false;
			boolean firstNum = true;
			for (String a : splitAtPlus)
			{
				String b = a.trim();
				if (Util.isFloatingPoint(b))
				{
					float toAdd = Integer.parseInt(b);
					if (firstNum)
					{
						fmod = toAdd;
						firstNum = false;
					}
					else
					{
						fmod %= toAdd;
					}
					usingFloat = true;
				}
				else if (Util.isNumeric(b))
				{
					int toAdd = Integer.parseInt(b);
					if (firstNum)
					{
						mod = toAdd;
						firstNum = false;
					}
					else
					{
						mod %= toAdd;
					}
				}
				else
				{
					Variable findVar = stack.peek().getVariableByName(b);
					if (findVar != null)
					{
						String toAddStr = memory.getAtAddress(findVar.getAddress());
						if (Util.isFloatingPoint(toAddStr) && usingFloat)
						{
							float toAdd = Integer.parseInt(toAddStr);
							if (firstNum)
							{
								fmod = toAdd;
								firstNum = false;
							}
							else
							{
								fmod %= toAdd;
							}
						}
						else if (Util.isNumeric(toAddStr))
						{
							int toAdd = Integer.parseInt(toAddStr);
							if (firstNum)
							{
								mod = toAdd;
								firstNum = false;
							}
							else
							{
								mod %= toAdd;
							}
						}
						else
						{
							System.out.println("ERROR: Value is not numeric");
						}
					}
					else
					{
						// HANDLE Error
						System.out.println("ERROR CANNOT FIND VARIABLE");
					}
				}
			}
			if (usingFloat)
			{
				value = Float.toString(fmod);
			}
			else
			{
				value = Integer.toString(mod);
			}
		}
		else if (value.contains("+"))
		{
			String[] splitAtPlus = value.split("\\+");
			int sum = 0;
			float fsum = 0.0f;
			boolean usingFloat = false;
			for (String a : splitAtPlus)
			{
				String b = a.trim();
				if (Util.isFloatingPoint(b))
				{
					float toAdd = Float.parseFloat(b);
					fsum += toAdd;
					usingFloat = true;
				}
				else if (Util.isNumeric(b))
				{
					int toAdd = Integer.parseInt(b);
					sum += toAdd;
				}
				else
				{
					Variable findVar = stack.peek().getVariableByName(b);
					if (findVar != null)
					{
						String toAddStr = memory.getAtAddress(findVar.getAddress());
						if (Util.isFloatingPoint(toAddStr) && usingFloat)
						{
							float toAdd = Float.parseFloat(toAddStr);
							fsum += toAdd;
							usingFloat = true;
						}
						else if (Util.isNumeric(toAddStr))
						{
							int toAdd = Integer.parseInt(toAddStr);
							sum += toAdd;
						}
						else
						{
							System.out.println("ERROR: Value is not numeric");
						}
					}
					else
					{
						// HANDLE Error
						System.out.println("ERROR CANNOT FIND VARIABLE");
					}
				}
			}
			if (usingFloat)
			{
				value = Float.toString(fsum);
			}
			else
			{
				value = Integer.toString(sum);
			}
		}
		else if (value.contains("-"))
		{
			String[] splitAtPlus = value.split("\\-");
			int difference = 1;
			float fdifference = 1.0f;
			boolean usingFloat = true;
			boolean firstNum = true;
			for (String a : splitAtPlus)
			{
				String b = a.trim();
				if (Util.isFloatingPoint(b))
				{
					float toAdd = Float.parseFloat(b);
					if (firstNum)
					{
						fdifference = toAdd;
						firstNum = false;
					}
					else
					{
						fdifference -= toAdd;
					}
					usingFloat = true;
				}
				else if (Util.isNumeric(b))
				{
					int toAdd = Integer.parseInt(b);
					if (firstNum)
					{
						difference = toAdd;
						firstNum = false;
					}
					else
					{
						difference -= toAdd;
					}
				}
				else
				{
					Variable findVar = stack.peek().getVariableByName(b);
					if (findVar != null)
					{
						String toAddStr = memory.getAtAddress(findVar.getAddress());
						if (Util.isFloatingPoint(toAddStr) && usingFloat)
						{
							float toAdd = Float.parseFloat(toAddStr);
							if (firstNum)
							{
								fdifference = toAdd;
								firstNum = false;
							}
							else
							{
								fdifference -= toAdd;
							}
							usingFloat = true;
						}
						else if (Util.isNumeric(toAddStr))
						{
							int toAdd = Integer.parseInt(toAddStr);
							if (firstNum)
							{
								difference = toAdd;
								firstNum = false;
							}
							else
							{
								difference -= toAdd;
							}
						}
						else
						{
							System.out.println("ERROR: Value is not numeric");
						}
					}
					else
					{
						// HANDLE Error
						System.out.println("ERROR CANNOT FIND VARIABLE");
					}
				}
			}
			if (usingFloat)
			{
				value = Float.toString(fdifference);
			}
			else
			{
				value = Integer.toString(difference);
			}
		}

		
		if (name.contains("~"))
		{
			int address = stack.peek().getVariableByName(name.split("\\~")[1]).getAddress();
			memory.setValueAtAddress(Integer.parseInt(memory.getAtAddress(address)),calculateValue(value));
		}
		else if(name.contains("[") || name.contains("]"))
		{
			//DO nothing
		}
		else
		{
			Variable findVar = stack.peek().getVariableByName(name);
			memory.setValueAtAddress(findVar.getAddress(), value);
		}
	}

	/**
	 * Parses the inputed code
	 * 
	 * @param code
	 *            The code to parse
	 * @param loop
	 *            Is true if in a loop and false if not in a loop
	 */
	public void execute(String code, boolean loop)
	{
		String[] splitAtSemi = code.split(";");
		// boolean inIfStatement = false;
		boolean skipIf = false;
		boolean skipToEndOfIf = false;
		boolean skipToEndOfCurrentLoop = false;
		boolean declaringFunction = false;
		for (String s : splitAtSemi)
		{
			if (declaringFunction)
			{
				if (s.equals("endfunc"))
				{
					declaringFunction = false;
					declaredFunctions.add(declareFunc);
					continue;
				}
				declareFunc.addCode(s + ";");
				continue;
			}
			if (!previousCodeExecuted.equals(""))
			{
				if (!loopStack.isEmpty())
				{
					loopStack.peek().addCodeToExecute(previousCodeExecuted);
				}
				previousCodeExecuted = "";
			}
			String trimmed = s.trim();
			if(trimmed.startsWith("//"))
			{
				continue;
			}
			if ((!trimmed.startsWith("for") && !loopStack.isEmpty()))
			{
				loopStack.peek().addCodeToExecute(trimmed + ";");
			}

			// TMP IF STATEMENT CODE
			if (skipIf)
			{
				if (trimmed.startsWith("else if"))
				{
					skipIf = false;
				}
				else if (trimmed.equals("else"))
				{
					skipIf = false;
					continue;
				}
				else if ((trimmed.equals("endif")))
				{
					skipIf = false;
					skipToEndOfIf = false;
					for (int i : ifStack.peek().getVarAddresses())
					{
						stack.peek().freeVariableAtAddress(i, memory);
					}
					ifStack.pop();
					continue;
				}
				else
				{
					continue;
				}
			}

			if (skipToEndOfIf)
			{
				skipIf = true;
				continue;
			}
			// END TMP IF STATEMENT CODE

			// TMP LOOP CODE
			if (trimmed.equals("endloop"))
			{
				skipToEndOfCurrentLoop = false;
				if (loopStack.isEmpty())
				{
					continue;
				}
				String progressString = loopStack.peek().getProgressString();
				handleEquals(progressString);

				String con = loopStack.peek().getCondition();
				boolean condition = checkCondition(con);
				// System.out.println("Debug - " + con);
				if (condition)
				{
					String codeToExecute = loopStack.peek().codeToExecute();
					for (int i : loopStack.peek().getVarAddresses())
					{
						stack.peek().freeVariableAtAddress(i, memory);
					}
					previousCodeExecuted = codeToExecute;
					loopStack.pop();
					execute(codeToExecute, true);
					continue;
				}
				else
				{
					String codeToExecute = loopStack.peek().codeToExecute();
					for (int i : loopStack.peek().getVarAddresses())
					{
						stack.peek().freeVariableAtAddress(i, memory);
					}
					previousCodeExecuted = codeToExecute;
					loopStack.pop();
					loop = false;
					continue;
				}
			}

			if (skipToEndOfCurrentLoop)
			{
				continue;
			}
			// END TMP LOOP CODE

			// TMP DO WHILE LOOP CODE

			// END TMP DO WHILE LOOP CODE

			if (!ifStack.isEmpty() && !ifStack.peek().getCondition().equals("skippedIF")
					&& (trimmed.startsWith("else if") || trimmed.equals("else")))
			{
				skipToEndOfIf = true;
				// inIfStatement = false;
			}
			else if(trimmed.startsWith("include "))
			{
				String filePath = trimmed.substring(trimmed.indexOf("\"") + 1, trimmed.indexOf("\"",trimmed.indexOf("\"") + 1));
				String includeCode = readFile(filePath);
				execute(includeCode,false);
			}
			else if (trimmed.equals("endif"))
			{
				skipToEndOfIf = false;
				// inIfStatement = false;
				for (int i : ifStack.peek().getVarAddresses())
				{
					stack.peek().freeVariableAtAddress(i, memory);
				}
				ifStack.pop();
				continue;
			}
			// Variable Declaration
			else if (trimmed.startsWith("var"))
			{
				parseVariableCreationLine(trimmed);
			}
			// TEMP
			else if (trimmed.startsWith("Print"))
			{
				String param = trimmed.substring(trimmed.indexOf("(") + 1, trimmed.indexOf(")"));
				if (param.contains("\""))
				{
					param = param.substring(param.indexOf("\"") + 1, param.indexOf("\"", param.indexOf("\"") + 1));
					System.out.println(param);
				}
				else
				{
					if (param.contains("*") || param.contains("/") || param.contains("+") || param.contains("-")
							|| param.contains("@") || param.contains("~"))
					{
						String value = calculateValue(param);
						System.out.println(value);
					}
					else
					{
						/*Variable findVar = stack.peek().getVariableByName(param);
						if (findVar != null)
						{
							String value = memory.getAtAddress(findVar.getAddress());
							System.out.println(value);
						}
						else
						{
							// HANDLE Error
							System.out.println("ERROR CANNOT FIND VARIABLE");
						}*/
						System.out.println(calculateValue(param));
					}
				}
			}
			else if(trimmed.startsWith("append"))
			{
				String param = trimmed.substring(trimmed.indexOf("(") + 1, trimmed.indexOf(")"));
				String arrayName = param.substring(0,param.indexOf(","));
				String value = param.substring(param.indexOf(",") + 1, param.length());
				String a = memory.getAtAddress(stack.peek().getVariableByName(arrayName).getAddress());
				value = calculateValue(value);
				if(value.startsWith("\"") && value.endsWith("\""))
				{
					value = value.substring(1, value.length() - 1);
				}
				if(a.startsWith("{") && a.endsWith("}"))
				{
					StringBuilder result = new StringBuilder();
					if(a.length() == 2)
					{
						result.append("{" + value + "}");
					}
					else
					{
						result.append(a.substring(0, a.length() - 1));
						result.append(",");
						result.append(value);
						result.append("}");
					}
					memory.setValueAtAddress(stack.peek().getVariableByName(arrayName).getAddress(), result.toString());
				}
				else
				{
					a += value;
					memory.setValueAtAddress(stack.peek().getVariableByName(arrayName).getAddress(), a);
				}
			}
			else
			{
				if ((trimmed.startsWith("if") && trimmed.split("\\(")[0].trim().equals("if"))
						|| (trimmed.startsWith("else if") && trimmed.split("\\(")[0].trim().equals("else if")))
				{
					boolean isTrue = checkCondition(trimmed);

					if (isTrue)
					{
						// System.out.println("DEBUG IF - TRUE");
						ifStack.push(trimmed);
						// inIfStatement = true;
					}
					else
					{
						// System.out.println("DEBUG IF - FALSE");
						skipIf = true;
						ifStack.push("skippedIF");
						// HANDLE else
					}
				}
				else if (trimmed.equals("else"))
				{
					ifStack.push("else");
					// inIfStatement = true;
				}
				else if ((trimmed.startsWith("for") && trimmed.split("\\(")[0].trim().equals("for")))
				{
					String paramString = trimmed.substring(trimmed.indexOf("(") + 1, trimmed.indexOf(")"));
					String[] paramSplit = paramString.split("\\,");
					// if (!loop)
					if (!stack.peek().contains(paramSplit[0].substring(4, paramSplit[0].indexOf("=")).trim()))
					{
						if (!paramSplit[0].isEmpty())
						{
							parseVariableCreationLine(paramSplit[0]);
						}
					}

					String condition = paramSplit[1].trim();
					boolean isTrue = checkCondition(condition);

					if (isTrue)
					{
						loopStack.push(condition, paramSplit[2].trim());
						loopStack.peek().addCodeToExecute(trimmed + ";");
					}
					else
					{
						skipToEndOfCurrentLoop = true;
					}
				}
				else if (trimmed.startsWith("while(") && trimmed.split("\\(")[0].trim().equals("while"))
				{
					String paramString = trimmed.substring(trimmed.indexOf("(") + 1, trimmed.indexOf(")"));

					String condition = paramString.trim();
					boolean isTrue = checkCondition(condition);

					if (isTrue)
					{
						loopStack.push(condition, "");
						loopStack.peek().addCodeToExecute(trimmed + ";");
					}
					else
					{
						skipToEndOfCurrentLoop = true;
					}
				}
				else if (trimmed.contains("="))
				{
					handleEquals(trimmed);
				}
				else if (trimmed.startsWith("function "))
				{
					String name = trimmed.substring(trimmed.indexOf(" ") + 1, trimmed.indexOf("(")).trim();
					String parm = trimmed.substring(trimmed.indexOf("(") + 1, trimmed.indexOf(")")).trim();
					String[] parameters = parm.split("\\,");
					declareFunc = new Function(name, parameters);
					declaringFunction = true;
				}
				else
				{
					boolean doneLoop = false;
					for (Function f : declaredFunctions)
					{
						String functionName = trimmed.substring(0, trimmed.indexOf("("));
						String parm = trimmed.substring(trimmed.indexOf("(") + 1, trimmed.indexOf(")"));
						String[] parameters = parm.split(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)", -1);

						if (f.getFunctionName().equals(functionName.trim())
								&& parameters.length == f.getParameters().length)
						{
							for(int i = 0; i < parameters.length; i++)
							{
								parameters[i] = calculateValue(parameters[i]);
							}
							RegisterKeys keys = new RegisterKeys(new ArrayList<Variable>());
							stack.push(new ProgramStackFrame(keys));

							if (!parameters[0].equals("") || parameters.length != 1)
							{
								for (int i = 0; i < parameters.length; i++)
								{
									String nline = f.getParameters()[i] + " = " + parameters[i] + ";";
									execute(nline, false);
								}
							}
							execute(f.getFunctionCode(), false);
							stack.pop();
							doneLoop = true;
							break;
						}
					}
					if (doneLoop)
					{
						continue;
					}
					System.out.println("ERROR: UNDEFINED COMMAND");
				}
			}
		}
	}
}
