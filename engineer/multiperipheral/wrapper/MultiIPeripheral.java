package engineer.multiperipheral.wrapper;

import java.util.ArrayList;

import dan200.computercraft.api.lua.ILuaContext;
import dan200.computercraft.api.peripheral.IComputerAccess;
import dan200.computercraft.api.peripheral.IPeripheral;
import engineer.multiperipheral.lua.IStandardMethod;
import engineer.multiperipheral.lua.StandardMethods;
import engineer.multiperipheral.util.ArrayUtil;
import engineer.multiperipheral.util.ArrayUtil.ArrayIterator;
import engineer.multiperipheral.util.wrapper.MultiIPeripheralUtil;
import engineer.multiperipheral.wrapper.peripheral.IHostedPeripheralWrapper;


public class MultiIPeripheral implements IPeripheral, IStandardMethod
{
	public final IPeripheral[] handlers;
	public final String[][] methods;
	public final String[] types;
	
	public final boolean isPeripheral;
	
	public MultiIPeripheral(IPeripheral ... peripherals)
	{
		ArrayList<IPeripheral> handlers = new ArrayList<IPeripheral>();
		ArrayList<String[]> methods = new ArrayList<String[]>();
		ArrayList<String> types = new ArrayList<String>();
		
		for(IPeripheral peripheral : peripherals)
		{
			if(peripheral != null)
			{
				String type = MultiIPeripheralUtil.getType(peripheral);
				if(type != null && !type.equals(""))
				{
					String[] _methodNames = MultiIPeripheralUtil.getMethodNames(peripheral);
					if(_methodNames != null)
					{
						ArrayList<String> methodNames = new ArrayList<String>();
						for(String name : _methodNames)
							if(name != null)
								methodNames.add(name);
						
						methods.add(methodNames.toArray(new String[methodNames.size()]));
						handlers.add(peripheral);
						types.add(type);
					}
				}
			}
		}
		
		if(handlers.size() > 0)
		{
			this.isPeripheral = true;
			boolean addStandard = handlers.size() > 1;
			
			this.handlers = handlers.toArray(new IPeripheral[handlers.size() + (addStandard ? 1 : 0)]);
			this.methods = methods.toArray(new String[methods.size() + (addStandard ? 1 : 0)][]);
			this.types = types.toArray(new String[types.size() + (addStandard ? 1 : 0)]);
			
			if(addStandard)
			{
				IPeripheral standardPeripheral = new IHostedPeripheralWrapper(new StandardMethods(this), null, 0, 0, 0, 0);
				this.methods[this.methods.length - 1] = standardPeripheral.getMethodNames();
				this.types[this.types.length - 1] = standardPeripheral.getType();
				this.handlers[this.handlers.length - 1] = standardPeripheral;	
			}
		}
		else
		{
			this.handlers = null;
			this.methods = null;
			this.types = null;
			this.isPeripheral = false;
		}
	}

	@Override
	public String getType() 
	{
		if(this.isPeripheral)
			return (this.types.length > 1 ? "MultiPeripheral" : this.types[0]);
		return null;
	}

	@Override
	public String[] getMethodNames() 
	{
		if(this.isPeripheral)
		{
			String[] methods = new String[ArrayUtil.getLength(this.methods)];
			ArrayIterator<String> it = new ArrayIterator<String>(methods);
			
			for(int i = 0; i < this.methods.length; i++)
			{
				for(String method : this.methods[i])
				{
					methods[it.getIndex()] = (this.types.length > 1 && !this.types[i].equals("standard")? method + "_" + this.types[i] : method);
					it.next();
				}
			}
			return methods;
		}
		return null;
	}

	@Override
	public Object[] callMethod(IComputerAccess computer, ILuaContext context, int method, Object[] arguments) throws Exception 
	{
		int total = -1;
		for(int i = 0; i < this.methods.length; i++)
			for(int j = 0; j < this.methods[i].length; j++)
				if(++total == method)
					return this.handlers[i].callMethod(computer, context, j, arguments); 
					
		return null;
	}

	@Override
	public void attach(IComputerAccess computer) 
	{
		if(this.isPeripheral)
			for(IPeripheral peripheral : this.handlers)
				MultiIPeripheralUtil.attach(peripheral, computer);
	}

	@Override
	public void detach(IComputerAccess computer) 
	{
		if(this.isPeripheral)
			for(IPeripheral peripheral : this.handlers)
				MultiIPeripheralUtil.detach(peripheral, computer);
	}

	@Override
	public boolean equals(IPeripheral other) 
	{
		if(other instanceof MultiIPeripheral)
		{
			MultiIPeripheral multi = (MultiIPeripheral) other;
			for(String type : this.types)
				if(multi.isPeripheral(type) != -1)
					return false;
			return true;
		}
		return false;
	}
	
	@Override
	public boolean equals(Object e)
	{
		if(e instanceof IPeripheral)
			return equals((IPeripheral) e);
		return false;
	}
	
	private int isPeripheral(String peripheral)
	{
		if(peripheral != null)
			for(int i = 0; i < this.types.length; i++)
				if(this.types[i].equals(peripheral))
					return i;
		return -1;
	}

	// *** IStandardMethod *** \\
	
	@Override
	public String[] getTypes() 
	{
		return this.types;
	}

	@Override
	public IPeripheral getPeripheral(String peripheral)
	{
		int index = this.isPeripheral(peripheral);
		if(index != -1)
			return this.handlers[index];
			
		return null;
	}
}
