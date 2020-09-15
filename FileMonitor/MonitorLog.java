package il.co.ilrd.FileMonitor;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;

import il.co.ilrd.Observer.Dispatcher;


 /* The class is responsible to log the file and track the changes made
  * in the file. */
public class MonitorLog 
{
	private WatchService ws;
	private Dispatcher<String> dispatcher;
	private WatchKey srcKey;
	private BufferedReader input;
	private String fileName;
	private Path directoryName;
	private Thread logThread;
	private boolean toStop = false;
	 
	
	public MonitorLog(String src) throws IOException
	{
		Path srcPath = Paths.get(src);
		
		//creating a new watchservice
		ws = FileSystems.getDefault().newWatchService();
		
		dispatcher = new Dispatcher<String>();
		
		//replace path to file name
		fileName = srcPath.getFileName().toString();
		
		//replace path to directory
		directoryName = srcPath.getParent();
		
		//create new buffer reader for src
		input = new BufferedReader(new FileReader(src));
		
		moveToLastLine();
	
		//link the watchservice to the src file with register
		srcKey = directoryName.register(ws, StandardWatchEventKinds.ENTRY_MODIFY);
	}
	
	
	//start a new thread to watch and update changes from src
	public void startLog() throws InterruptedException, IOException 
	{ 
		logThread = new Thread(() ->
		{  
			try 
			{
				while(!toStop)
				{
					srcKey = this.ws.take();
					watchEventsForFile();
					srcKey.reset();
				}
			} 
			catch (InterruptedException e){	e.printStackTrace();}
		});
		logThread.start();
	}
	
	/* changes flag of event loop and waits until the logThread perform 
	 * the last take call (take is blocking the thread)
	 * and closes watch service */
	public void closeLog()
	{
		toStop = true;
		try 
		{
			logThread.join();
		} catch (InterruptedException e1) {e1.printStackTrace();}
		try 
		{
			ws.close();
			input.close();
		} catch (IOException e) {e.printStackTrace();}
	}
	
	
	
	public Dispatcher<String> getDispatcher()
	{
		return dispatcher;
	}
	
	
	/*********Utility funcs*********/
	
	//loop through all events in the file and update the changes
	private void watchEventsForFile()
	{
		for (WatchEvent<?> event : srcKey.pollEvents()) 
		{
			 Path changed = (Path)(event.context());

			 if (changed.endsWith(fileName) &&
				(event.kind() == StandardWatchEventKinds.ENTRY_MODIFY))
			 {
	               try
	               {
	            	   dispatcher.updateAll(input.readLine());
	               } catch (IOException e) {e.printStackTrace();}
	         }
		}
	}
	
	private void moveToLastLine()
	{
		//move to the last line of file before we register
		try 
		{
			while(input.readLine() != null)
			{}
		} catch (IOException e) 
		{
			e.printStackTrace();
		}
	}
}



