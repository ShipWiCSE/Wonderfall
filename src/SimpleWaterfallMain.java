import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.Scanner;

import backend.PortManager;
import backend.PortManagerDebug;



public class SimpleWaterfallMain
{
		
	/**
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException
	{
		
		Scanner inputDevice;
		inputDevice = new Scanner(System.in);

		PortManager portManager = new PortManagerDebug();
		((PortManagerDebug)portManager).start();
		PrintWriter toWaterfall = new PrintWriter(portManager.getOutputStream(),true);
		BufferedReader fromWaterfall = new BufferedReader(new InputStreamReader(portManager.getInputStream()));
		System.out.println("ENTER CMD");
		String input = inputDevice.next().toUpperCase();
		while (!input.startsWith("QUIT"))
		{
			toWaterfall.println("");
			toWaterfall.print(input);
			String response = fromWaterfall.readLine( );
			while (response.length() == 0)
				response = fromWaterfall.readLine();
			response = response.toUpperCase();
			System.out.println("Waterfall response:" + response);
			input = inputDevice.next().toUpperCase();
		}
	}
	
}
