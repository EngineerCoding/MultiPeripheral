package engineer.multiperipheral.wrapper.peripheral;

import dan200.computercraft.api.peripheral.IComputerAccess;
import dan200.computercraft.api.peripheral.IPeripheral;
import engineer.multiperipheral.MultiPeripheral;
import engineer.multiperipheral.api.ILuaPeripheral;
import engineer.multiperipheral.wrapper.MethodFinder;

public class ILuaPeripheralWrapper extends MethodFinder implements IPeripheral
{
	public final ILuaPeripheral instance;
	public final boolean isPeripheral;
	
	public ILuaPeripheralWrapper(ILuaPeripheral peripheral)
	{
		super(peripheral);
		this.instance = peripheral;
		
		this.isPeripheral = (this.getType() != null && this.instance != null);
	}

	@Override
	public String getType()
	{
		try
		{
			return this.instance.getType();
		}
		catch(Exception e)
		{
			MultiPeripheral.Log.severe(String.format("Cannot call IExternalPeripheral.getType on %s", this.instance.getClass().getName()));
			MultiPeripheral.logThrowable(e);
		}
		return null;
	}
	
	@Override
	public void attach(IComputerAccess computer) 
	{
		try
		{
			this.instance.attach(computer);
		}
		catch(Exception e)
		{
			MultiPeripheral.Log.severe(String.format("Cannot call ILuaPeripheral.attach on %s", this.instance.getClass().getName()));
			MultiPeripheral.logThrowable(e);
		}
	}

	@Override
	public void detach(IComputerAccess computer) 
	{
		try
		{
			this.instance.detach(computer);
		}
		catch(Exception e)
		{
			MultiPeripheral.Log.severe(String.format("Cannot call ILuaPeripheral.detach on %s\n", this.instance.getClass().getName()));
			MultiPeripheral.logThrowable(e);
		}
	}

	@Override
	public boolean equals(IPeripheral other) 
	{
		if(other instanceof ILuaPeripheralWrapper)
		{
			ILuaPeripheralWrapper wrapper = (ILuaPeripheralWrapper) other;
			return wrapper.instance.equals(this.instance);
		}
		return false;
	}
}
