package engineer.multiperipheral.handler.provider;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import dan200.computercraft.api.peripheral.IPeripheralProvider;

public class ListImitator<E extends IPeripheralProvider> implements List<E>
{
	private final ArrayList<E> PERIPHERAL = new ArrayList<E>();
	protected ArrayList<E> peripheralProvider = new ArrayList<E>();
	
	public ListImitator(E provider, ArrayList<E> base)
	{
		PERIPHERAL.add(provider);
		for(E e : base)
		{
			peripheralProvider.add(e);
		}
	}

	@Override
	public boolean add(E e) 
	{
		return peripheralProvider.add(e);
	}

	@Override
	public void add(int index, E element) 
	{
		peripheralProvider.add(index, element);	
	}

	@Override
	public boolean addAll(Collection<? extends E> c) 
	{
		return peripheralProvider.addAll(c);
	}

	@Override
	public boolean addAll(int index, Collection<? extends E> c)
	{
		return peripheralProvider.addAll(index, c);
	}

	@Override
	public void clear()
	{
		peripheralProvider.clear();
	}

	@Override
	public boolean contains(Object o) 
	{
		return peripheralProvider.contains(o);
	}

	@Override
	public boolean containsAll(Collection<?> c)
	{
		return peripheralProvider.containsAll(c);
	}

	@Override
	public E get(int index) 
	{
		return peripheralProvider.get(index);
	}

	@Override
	public int indexOf(Object o) 
	{
		return peripheralProvider.indexOf(o);
	}

	@Override
	public boolean isEmpty()
	{
		return peripheralProvider.isEmpty();
	}

	@Override
	public Iterator<E> iterator() 
	{
		return PERIPHERAL.iterator();
	}

	@Override
	public int lastIndexOf(Object o) 
	{
		return peripheralProvider.lastIndexOf(o);
	}

	@Override
	public ListIterator<E> listIterator() 
	{
		return PERIPHERAL.listIterator();
	}

	@Override
	public ListIterator<E> listIterator(int index) 
	{
		return PERIPHERAL.listIterator(index);
	}

	@Override
	public boolean remove(Object o) 
	{
		return peripheralProvider.remove(o);
	}

	@Override
	public E remove(int index) 
	{
		return peripheralProvider.remove(index);
	}

	@Override
	public boolean removeAll(Collection<?> c) 
	{
		return peripheralProvider.removeAll(c);
	}

	@Override
	public boolean retainAll(Collection<?> c) 
	{
		return peripheralProvider.retainAll(c);
	}

	@Override
	public E set(int index, E element) 
	{
		return peripheralProvider.set(index, element);
	}

	@Override
	public int size() 
	{
		return PERIPHERAL.size();
	}

	@Override
	public List<E> subList(int fromIndex, int toIndex) 
	{
		return peripheralProvider.subList(fromIndex, toIndex);
	}

	@Override
	public Object[] toArray() 
	{
		return peripheralProvider.toArray();
	}

	@Override
	public <T> T[] toArray(T[] a) 
	{
		return peripheralProvider.toArray(a);
	}
}
