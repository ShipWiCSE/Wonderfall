package backend;

import gnu.io.CommPortIdentifier;
import gnu.io.NoSuchPortException;
import gnu.io.PortInUseException;
import gnu.io.SerialPort;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Enumeration;
import java.util.LinkedList;

/**
 * Manages communication with the waterfall
 * @author Merlin
 *
 * Created:  Apr 21, 2011
 */
public class PortManagerComm implements PortManager

{
	String deviceName;
	  SerialPort port;
	  
		/**
		 * 
		 */
		public PortManagerComm()
		{
			this.deviceName = "/dev/tty.usbmodemfd131";
			this.port = null;
		}

		/**
		 * @see backend.PortManager#setDeviceName(java.lang.String)
		 */
		public void setDeviceName(String name)
		{
			this.deviceName = name;
		}
		
		/**
		 * @see backend.PortManager#open()
		 */
		public void open() throws IOException
		{
			if (deviceName == null) 
				throw new IOException("device name not set.");
			
			try
			{
				CommPortIdentifier comm = CommPortIdentifier.getPortIdentifier(deviceName);
				port = (SerialPort) comm.open("test", 2000);
			}
			catch (NoSuchPortException e)
			{
				e.printStackTrace();
				throw new IOException(e);
			}
			catch (PortInUseException e)
			{
				e.printStackTrace();
				throw new IOException(e);
			}
		}

		/**
		 * @see backend.PortManager#close()
		 */
		public void close() throws IOException
		{
			if (port == null)
				throw new IOException("Port not opened.");

			port.close( );
			port = null;
		}

		/**
		 * @see backend.PortManager#getOutputStream()
		 */
		public OutputStream getOutputStream() throws IOException
		{
			if (port == null)
				throw new IOException("Port not opened.");
			
			return port.getOutputStream();
		}

		/**
		 * @see backend.PortManager#getInputStream()
		 */
		public InputStream getInputStream() throws IOException
		{
			if (port == null)
				throw new IOException("Port not opened.");
			
			return port.getInputStream();
		}

		/**
		 * @see backend.PortManager#getSerialPorts()
		 */
		public String[] getSerialPorts() throws IOException
		{
			String ports[];
			
			LinkedList<String> portList = new LinkedList<String>( );
			Enumeration<?> e = CommPortIdentifier.getPortIdentifiers();
			while (e.hasMoreElements())
			{
				CommPortIdentifier comm = (CommPortIdentifier) e.nextElement();
				portList.add(comm.getName());
			}
			
			ports = new String[ portList.size() ];
			ports = portList.toArray(ports);
			
			return ports;
		}

		/**
		 * @see backend.PortManager#getTokens()
		 */
		public String[] getTokens()
		{
			// TODO Auto-generated method stub
			return null;
		}

}
