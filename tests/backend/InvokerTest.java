package backend;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import commands.Command;



/**
 * @author Merlin
 *
 * Created:  Mar 1, 2007
 */
public class InvokerTest
{
	private MockCommand c1;
	private Invoker invoker;
	private MockCommand c2;

	/**
	 * 
	 */
	@Before public void setUpInvokerAndCommand()
	{
		c1 = new MockCommand();
		c2 = new MockCommand();
		invoker = Invoker.getSingleton();
	}
	
	/**
	 * Make sure we always get the same object
	 */
	@Test 
	public void isSingleton()
	{
		Invoker a = Invoker.getSingleton();
		Invoker b = Invoker.getSingleton();
		assertSame(a,b);
	}
	/**
	 * Just make sure that executing one command calls its execute method and not its undo method
	 */
	@Test public void executesOne()
	{
		invoker.execute(c1);
		assertEquals(1,c1.numberOfExecutes);
		assertEquals(0,c1.numberOfUndos);
	}
	
	/**
	 * make sure that executing and undoing a command calles the appropriate methods
	 */
	@Test public void singleExecuteAndUndo()
	{
		invoker.execute(c1);
		invoker.undo();
		assertEquals(1,c1.numberOfExecutes);
		assertEquals(1,c1.numberOfUndos);
		
	}
	
	/**
	 * make sure undos get done in the appropriate order
	 */
	@Test public void twoExecutesFollowedByUndos()
	{
		invoker.execute(c1);
		invoker.execute(c2);
		invoker.undo();
		assertEquals(1,c1.numberOfExecutes);
		assertEquals(1,c2.numberOfExecutes);
		assertEquals(0,c1.numberOfUndos);
		assertEquals(1,c2.numberOfUndos);
		invoker.undo();
		assertEquals(1,c1.numberOfExecutes);
		assertEquals(1,c2.numberOfExecutes);
		assertEquals(1,c1.numberOfUndos);
		assertEquals(1,c2.numberOfUndos);
	}

	
	
	/**
	 * if a command in not undoable, it should not be saved in the undo stack
	 */
	@Test public void onlySaveUndoableCommands()
	{
		MockCommand c1 = new MockCommand();
		c1.setUndoable(true);
		MockCommand c2 = new MockCommand();
		c2.setUndoable(false);
		Invoker invoker = Invoker.getSingleton();
		invoker.execute(c1);
		invoker.execute(c2);
		invoker.undo();
		assertEquals(0,c2.numberOfUndos);
		assertEquals(1,c1.numberOfUndos);
	}
	private class MockCommand implements Command
	{

		private int numberOfExecutes;
		private int numberOfUndos;
		private boolean undoable=true;
		
		/**
		 * @param b
		 */
		public void setUndoable(boolean b)
		{
			this.undoable = b;
		}

		/**
		 * @see Command#undo()
		 */
		public void undo()
		{
			numberOfUndos++;
		}

		/**
		 * @see Command#getUndoable()
		 */
		public boolean getUndoable()
		{
			return undoable;
		}


		@Override
		public String buildCommandString()
		{
			numberOfExecutes++;
			return "debug";
		}
		
	}
}
