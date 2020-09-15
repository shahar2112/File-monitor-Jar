package il.co.ilrd.FileMonitor;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class CrudedFile implements CrudRepository<String, Integer>
{
	private BufferedWriter buffer;
	private int lineCtr;
	
	public CrudedFile(String dest)
	{
		try 
		{
			buffer = new BufferedWriter(new FileWriter(dest));
		} catch (IOException e) {e.printStackTrace();}
		
		lineCtr = 0;
	}

	@Override
	public void close() 
	{
		try
		{
			buffer.close();
		} catch (IOException e) {e.printStackTrace();}
	}
	
	/* The method gets the new entry (new change) and writes it into the buffer,
	 * return the line number of the buffer */
	@Override
	public Integer create(String data)
	{
		try 
		{
			System.out.println(data); // print changes also to terminal
			this.buffer.write(data);
			this.buffer.newLine();
		} catch (IOException e) {e.printStackTrace();}
		
		return ++lineCtr;
	}

	@Override
	public String read(Integer id){return null;}

	@Override
	public void update(Integer id, String data) {}

	@Override
	public void delete(Integer id) {}
}
