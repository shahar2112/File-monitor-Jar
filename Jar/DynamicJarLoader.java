package il.co.ilrd.Jar;

import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class DynamicJarLoader 
{
	public static ArrayList<Class<?>> jarLoader(String inter, String jarpath) throws IOException, ClassNotFoundException
	{
		ArrayList<Class<?>> classlist = new ArrayList<Class<?>>();
		JarFile jarFile = new JarFile(jarpath);

		Enumeration<JarEntry> e = jarFile.entries();

		URL[] urls = { new URL("jar:file:" + jarpath + "!/") };

		URLClassLoader cl = URLClassLoader.newInstance(urls);

		while (e.hasMoreElements())
		{
		    JarEntry je = e.nextElement();
		    if(je.isDirectory() || !je.getName().endsWith(".class"))
		    {
		        continue;
		    }
		    // -6 because of .class
		    String className = je.getName().substring(0,je.getName().length()-6);
		    className = className.replace('/', '.');
		    Class<?> c = cl.loadClass(className);

		   Class<?>[] interfaces = c.getInterfaces();
		   
		   for(Class<?> interf: interfaces)
		   {
			   if(inter.equals(interf.getSimpleName()))
			   {
				   classlist.add(c);
			   }
		   }
		}

		jarFile.close();

		return classlist;	
	}
}
