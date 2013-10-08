package backend;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * An interface required by all objects that will manage communication with the waterfall
 * @author Merlin
 *
 * Created:  Apr 21, 2011
 */
public interface PortManager {

	/**
	 * Open the connection to the waterfall
	 * @throws IOException
	 */
	public void open( ) throws IOException;
	/**
	 * Close the connection
	 * @throws IOException
	 */
	public void close( ) throws IOException;
	/**
	 * Get an output stream that will talk to the waterfall
	 * @return the output stream
	 * @throws IOException
	 */
	public OutputStream getOutputStream( ) throws IOException;
	/**
	 * Get an input stream that will transmit msgs from the waterfall
	 * @return the intput stream
	 * @throws IOException
	 */
	public InputStream getInputStream( ) throws IOException;
	/**
	 * Set the name of this device
	 * @param name
	 */
	void setDeviceName(String name);
	/**
	 * Get the serial ports
	 * @return the names
	 * @throws IOException
	 */
	public String[] getSerialPorts( ) throws IOException;
	
	/**
	 * Get the last command we were given
	 * @return the command as we parsed it
	 */
	public String[] getTokens();
}
