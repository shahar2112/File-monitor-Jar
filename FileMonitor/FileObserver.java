package il.co.ilrd.FileMonitor;

import java.io.IOException;
import il.co.ilrd.Observer.Dispatcher;
import il.co.ilrd.Observer.Observer;


public class FileObserver
{
	private MonitorLog monitorLog;
	private Observer<String> observer;
	
	public FileObserver(String src, String dest) throws IOException, InterruptedException 
	{
		monitorLog = new MonitorLog(src);
		observer = new ObservedFile(monitorLog.getDispatcher(), dest);
		observer.subscribe();
		monitorLog.startLog();
	}
	
	/* closing the monitor = src file and watchservice
	 * and the observer = dest file
	 */
	public void close() 
	{
		monitorLog.closeLog();
		observer.unsubscribe();
		observer.StopUpdate(null);
	}
	
	
	 /* A class that observed the src file and calls the crudFile to
	 * update or stop update if needed. This class extends the 
	 * abstract class observer. */
	private class ObservedFile extends Observer<String>
	{
		private CrudedFile crudFile;
		
		private ObservedFile(Dispatcher<String> dispatcher, String dest) throws IOException 
		{
			super(dispatcher);
			crudFile = new CrudedFile(dest);
		}
		
		@Override
		public void StopUpdate(String t) 
		{
			crudFile.close();
		}
		
		@Override
		public void update(String t) 
		{
			crudFile.create(t);
		}
	}
}


