package il.co.ilrd.FileMonitor;

import java.io.Serializable;


public interface CrudRepository<T extends Serializable, ID extends Serializable> extends AutoCloseable
{
	public ID create(T data);
	public T read(ID id);
	public void update (ID id, T data);
	public void delete(ID id);
}

