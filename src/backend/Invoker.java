package backend;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;

import commands.Command;

/**
 * Executes a sequence of commands and supports the ability to undo the ones
 * that are undoable in reverse order
 * 
 * @author Merlin
 * 
 *         Created: Apr 26, 2011
 */
public class Invoker
{

	private static Invoker singleton;
	ArrayList<Command> undoStack = new ArrayList<Command>();
	private PortManagerComm portManager;
	private PrintWriter toWaterfall;
	private BufferedReader fromWaterfall;

	private Invoker()
	{
		try
		{
			portManager = new PortManagerComm();
//			portManager.start();
			 String[] x = portManager.getSerialPorts();
			 portManager.open();
			toWaterfall = new PrintWriter(portManager.getOutputStream(), true);
			fromWaterfall = new BufferedReader(new InputStreamReader(portManager.getInputStream()));
		} catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * Cause the command to be executed and remember it for later undos
	 * 
	 * @param c
	 *            the command to be executed
	 */
	public void execute(Command c)
	{
		if (c.getUndoable())
			undoStack.add(c);
		try
		{
			String command = c.buildCommandString().toUpperCase();

			toWaterfall.print("\n");
			toWaterfall.flush();
			String response;
			do
			{
				response = fromWaterfall.readLine();
				System.out.println("respons: " + response);
			}while (!response.contains("ENTER CMD"));
			response = fromWaterfall.readLine();
			
			// now ready for command
			
//			toWaterfall.print("NEW 2\n");
//			toWaterfall.flush();
//			
//			do {
//				response = fromWaterfall.readLine();
//				System.out.println("r : " + response);
//			} while (!response.contains("Start entering"));
//			
//			
//			
//			

			String[] comparts = command.split("\n");
			for (int i=0;i<comparts.length;i++)
			{
				toWaterfall.print(comparts[i]);
				toWaterfall.print("\n");
				toWaterfall.flush();

				try
				{
					Thread.sleep(100);
				} catch (InterruptedException e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				//				
//				response = fromWaterfall.readLine();
//				while (response.length() == 0)
//					response = fromWaterfall.readLine();
//				response = response.toUpperCase();
//				System.out.println("Waterfall response:" + response);
			}
			
			do {
				response = fromWaterfall.readLine();
				System.out.format("r: (%s)\n", response);
			} while (!response.contains("ENTER CMD"));
			fromWaterfall.readLine();
			
			
		} catch (IOException e)
		{
			e.printStackTrace();
		}

	}

	/**
	 * Undo the most recent executed and not undone command
	 */
	public void undo()
	{
		Command lastCommand = undoStack.remove(undoStack.size() - 1);
		lastCommand.undo();
	}

	/**
	 * @return the only one of these in the syste
	 */
	public static Invoker getSingleton()
	{
		if (singleton == null)
		{
			singleton = new Invoker();
		}
		return singleton;
	}
}
