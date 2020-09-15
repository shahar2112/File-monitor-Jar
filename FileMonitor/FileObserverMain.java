package il.co.ilrd.FileMonitor;

import java.io.IOException;

public class FileObserverMain {

	public static void main(String[] args) throws IOException, InterruptedException 
	{
		//String src = "/var/log/syslog";
		String src = "/home/student/shahar-maimon/java/myProjects/src/il/co/ilrd/JDBC/Test.txt";
		
		String dest = "/home/student/shahar-maimon/java/myProjects/src/il/co/ilrd/FileMonitor/testchanges.txt";
		//String dest = "/home/bla/shahar-maimon/java/myProjects/src/il/co/ilrd/FileMonitor/testchanges.txt";
		FileObserver filetest = new FileObserver(src, dest);
		
		Thread.sleep(1000);
		filetest.close();
	}
}
