package backend;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class ModelTest
{

	private Model model;

	@Test
	public void singleton()
	{
		Model m1 = Model.getSingleton();
		Model m2 = Model.getSingleton();
		assertSame(m1, m2);
	}

	@Test
	public void debug()
	{
		model = Model.getSingleton();
		assertTrue(model.setDebug());
		System.out.println("got past assertTrue");
		PortManager manager = model.getPortManager();
		String[] lastCommand = manager.getTokens();
		assertEquals("DEBUG", lastCommand[0]);
	}

}
