package commands;

/**
 * Operations required by all command objects
 * 
 * @author Merlin
 *
 * Created:  Apr 26, 2011
 */
public interface Command
{

	/**
	 * Check to see if this command can be undone
	 * @return true if it supports undo
	 */
	public boolean getUndoable();

	/**
	 * Undo the actions that were done by this command.  Assumes that the command has been executed
	 */
	public void undo();

	/**
	 * @return the command string that tells the waterfall what this command wants
	 */
	public String buildCommandString();

}
