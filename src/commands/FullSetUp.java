package commands;

import backend.Model;


/**
 * This command should completely overwrite the pattern the waterfall is displaying
 * @author Merlin
 *
 * Created:  Aug 8, 2011
 */
public class FullSetUp implements Command
{
	
	public static final int BLANK_LINES_BETWEEN = 4;
	private boolean[][] pattern;
	private int speed;
	private int patternLength;

	/**
	 * @param pattern the pattern the valves should use
	 * @param speed the rate at which each row of the pattern should be displayed
	 */
	public FullSetUp(boolean[][] pattern, int speed)
	{
		if (pattern[0].length != Model.NUMBER_OF_VALVES) 
		{
			throw new IllegalArgumentException("Pattern must be " + Model.NUMBER_OF_VALVES + " columns wide ");
		}
			
		this.pattern = pattern;
		this.speed = speed;
		
		int i = pattern.length-1;
		while ((i>=0) && hexConvert(i).equals( "0000"))
		{
			
			i--;
		}
		this.patternLength = Math.min(i+BLANK_LINES_BETWEEN+1, pattern.length);
	}

	/**
	 * Nope - all is lost with this one
	 * @see commands.Command#getUndoable()
	 */
	@Override
	public boolean getUndoable()
	{
		return false;
	}

	/**
	 * @see commands.Command#buildCommandString()
	 */
	public String buildCommandString()
	{
		String result = "new " + patternLength + "\n";
		for (int i=0;i<patternLength;i++)
		{
			result = result + hexConvert(i) + "\n";
		}
		result = result + "DELAY " + speed + "\n";
		result = result + "RUN\n";
		return result;
	}
	protected String hexConvert(int row)
	{
		int value = 0;
		int position = 1;
		for (int i = pattern[row].length-1; i >= 0; i--)
		{
			if (pattern[row][i])
			{
				value = value + position;
			}
			position = position*2;
		}
		String hexstr = Integer.toHexString(value);
		
		while (hexstr.length() < pattern[row].length/4)
		{
			hexstr = "0" + hexstr;
		}
		return hexstr;
	}

	/**
	 * @see commands.Command#undo()
	 */
	@Override
	public void undo()
	{
		// TODO Auto-generated method stub

	}

	
	protected boolean[][] getPattern()
	{
		return pattern;
	}

	protected int getSpeed()
	{
		return speed;
	}

	public int getPatternLength()
	{
		// TODO Auto-generated method stub
		return patternLength;
	}


}
