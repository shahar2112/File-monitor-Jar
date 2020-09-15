package il.co.ilrd.Jar;

import java.io.IOException;
import java.util.ArrayList;

public class TestJar {
	
	public static void main(String[] args) throws ClassNotFoundException, IOException
	{
		
		ArrayList<Class<?>> classList = 
		DynamicJarLoader.jarLoader("Shape", "/home/bla/shahar-maimon/java/myProjects/bin/il/co/ilrd/Jar/JarFile.jar");
		
		for(Class<?> classname : classList)
		{
			System.out.println(classname);
		}
	}

}


