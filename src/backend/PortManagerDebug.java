package backend;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.io.PrintWriter;

/**
 * Simulates the communication with the device for debugging purposes
 * @author Merlin
 *
 * Created:  Apr 21, 2011
 */
public class PortManagerDebug extends Thread implements PortManager
{
	boolean ready = false;
	boolean close = false;
	boolean all_complete = true;

	PipedInputStream input, clientInput;
	PipedOutputStream output, clientOutput;
	private String[] tokens;
	private boolean running;

	/**
	 * @see backend.PortManager#getOutputStream()
	 */
	public OutputStream getOutputStream()
	{
		return clientOutput;
	}

	/**
	 * @see backend.PortManager#getInputStream()
	 */
	public InputStream getInputStream()
	{
		return clientInput;
	}

	/**
	 * @throws IOException
	 */
	public PortManagerDebug() throws IOException
	{
		input = new PipedInputStream();
		clientInput = new PipedInputStream();

		output = new PipedOutputStream(clientInput);
		clientOutput = new PipedOutputStream(input);

	}

	/**
	 * @see java.lang.Thread#start()
	 */
	@Override
	public void start()
	{
		super.start();
		System.out.println("I started");
		while (!ready)
		{
			synchronized (this)
			{
				try
				{
					this.wait(100000);
				} catch (InterruptedException e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * @see java.lang.Thread#run()
	 */
	@Override
	public void run()
	{

		System.out.println("I am running");
		BufferedReader in = new BufferedReader(new InputStreamReader(input));
		PrintWriter out = new PrintWriter(output, true);
	

		synchronized (this)
		{
			this.ready = true;
			this.notifyAll();
		}

		try
		{
			while (!close)
			{
				//System.out.println("I am waiting for input");
				String line = in.readLine();

				line = line.toUpperCase();
				tokens = line.split(" ");

				if (tokens[0].equals("DEBUG"))
				{
					System.out.println("Sending: " + line);
				} else if (tokens[0].equals("RUN"))
				{
					out.println("Starting the stored pattern");
				} else if (tokens[0].equals("NEW"))
				{
					out.println("Setting up a new pattern");
				} else
				{
					
				}
				out.println("ENTER CMD");
				out.flush();
			}
		} catch (IOException E)
		{

		}
	}

	public String[] getTokens()
	{
		return tokens;
	}

	/**
	 * @see backend.PortManager#close()
	 */
	public void close()
	{
		synchronized (this)
		{
			this.close = true;
			this.interrupt();
		}
	}

	/**
	 * @see backend.PortManager#open()
	 */
	public void open()
	{
		
	}

	/**
	 * @see backend.PortManager#setDeviceName(java.lang.String)
	 */
	public void setDeviceName(String name)
	{
		
	}

	/**
	 * @see backend.PortManager#getSerialPorts()
	 */
	public String[] getSerialPorts() throws IOException
	{
		String comms[] =
		{ "fake comms" };
		return comms;
	}
	
	
}
