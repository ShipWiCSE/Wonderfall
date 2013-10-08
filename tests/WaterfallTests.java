import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import commands.FullSetUpTest;

import backend.InvokerTest;
import backend.ModelTest;

/**
 * @author Merlin
 * 
 */
@RunWith(Suite.class)
@Suite.SuiteClasses(
{ 
	InvokerTest.class, 
	ModelTest.class,
	
	FullSetUpTest.class
})
	
	
public class WaterfallTests
{
}
