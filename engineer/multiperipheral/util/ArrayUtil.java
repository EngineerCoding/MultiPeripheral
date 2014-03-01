package engineer.multiperipheral.util;


public class ArrayUtil
{
	public static class ArrayIterator<E>
	{
		private final E[] array;
		private int index = 0;
		
		public ArrayIterator(E[] array)
		{
			this.array = array;
		}
		
		public boolean hasNext()
		{
			return index < array.length;
		}
		
		public E next()
		{
			if(index >= array.length)
				return null;
			return this.array[index++];
		}
		
		public int getIndex()
		{
			return index;
		}
	}
	
	public static <T> int getLength(T[][] array)
	{
		int l = 0;
		for(T[] t : array)
			for(@SuppressWarnings("unused") T _ : t)
				l += 1;
		return l;
	}
}
