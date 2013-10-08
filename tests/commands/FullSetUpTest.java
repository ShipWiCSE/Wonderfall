package commands;

import static org.junit.Assert.*;

import org.junit.Test;

import backend.Invoker;

/**
 * @author Merlin
 * 
 *         Created: Sep 12, 2011
 */
public class FullSetUpTest
{
	private static final boolean[][] SMALL_PATTERN_1 =
	{
			{ true, true, true, true, true, true, true, true, true, true, true, true, true, true,
					true, true },
			{ false, false, false, false, false, false, false, false, false, false, false, false,
					false, false, false, false } };
	private static final boolean[][] TOO_WIDE_PATTERN =
	{
			{ true, true, true, true, true, true, true, true, true, true, true, true, true, true,
					true, true, true },
			{ false, false, false, false, false, false, false, false, false, false, false, false,
					false, false, false } };
	private static final boolean[][] TOO_NARROW_PATTERN =
	{
	{ true, true, true, true, true, true, true, true, true, true, true, true, true, true, true },
	{ false, false, false, false, false, false, false, false, false, false, false, false, false } };

	private static final boolean[][] NOT_FULL_PATTERN =
	{
	{ true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true },
	{ true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, false },
	{ true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, false },
	{ true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, false },
	{ false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false },
	{ false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false },
	{ false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false },
	{ false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false },
	{ false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false },
	{ false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false },
	{ false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false } };
	private static final int SPEED_1 = 15;
	private static final String SMALL_PATTERN_COMMAND = "new 2\nffff\n0000\nDELAY 15\nRUN\n";
	private static final String NOT_FULL_PATTERN_COMMAND = "new 8\nffff\nfffe\nfffe\nfffe\n0000\n0000\n0000\n0000\nDELAY 15\nRUN\n";

	/**
	 * 
	 */
	@Test
	public void testIsUndoable()
	{
		FullSetUp t = new FullSetUp(SMALL_PATTERN_1, SPEED_1);
		assertFalse(t.getUndoable());
	}

	/**
	 * Just make sure it remembers what it is told at creation
	 */
	@Test
	public void initialization()
	{
		FullSetUp t = new FullSetUp(SMALL_PATTERN_1, SPEED_1);
		assertArrayEquals(SMALL_PATTERN_1, t.getPattern());
		assertEquals(SPEED_1, t.getSpeed());
	}

	@Test
	public void watchItRun()
	{
		Invoker i = Invoker.getSingleton();
		FullSetUp t = new FullSetUp(SMALL_PATTERN_1, SPEED_1);
		i.execute(t);
	}

	/**
	 * Pattern must be the correct width
	 */
	@Test(expected = IllegalArgumentException.class)
	public void patternWidthTooBig()
	{
		new FullSetUp(TOO_WIDE_PATTERN, SPEED_1);
	}

	/**
	 * Pattern must be the correct width
	 */
	@Test(expected = IllegalArgumentException.class)
	public void patternWidthTooSMALL()
	{
		new FullSetUp(TOO_NARROW_PATTERN, SPEED_1);
	}

	/**
	 * Make sure it builds the correct command string
	 */
	@Test
	public void simpleCommand()
	{
		FullSetUp t = new FullSetUp(SMALL_PATTERN_1, SPEED_1);
		assertEquals(SMALL_PATTERN_COMMAND, t.buildCommandString());
	}

	/**
	 * Test conversions from the boolean array to hex for the command
	 */
	@Test
	public void hexConversions()
	{
		FullSetUp t = new FullSetUp(SMALL_PATTERN_1, SPEED_1);
		assertEquals("ffff", t.hexConvert(0));
		assertEquals("0000", t.hexConvert(1));
	}

	@Test
	public void testShorteningPattern()
	{
		FullSetUp t = new FullSetUp(NOT_FULL_PATTERN, SPEED_1);
		assertEquals(NOT_FULL_PATTERN.length - 8 + 1 + FullSetUp.BLANK_LINES_BETWEEN, t.getPatternLength());
		assertEquals(NOT_FULL_PATTERN_COMMAND, t.buildCommandString());
	}

}
