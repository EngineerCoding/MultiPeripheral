package engineer.multiperipheral.wrapper.single;

import net.minecraft.world.World;
import dan200.computercraft.api.peripheral.IComputerAccess;
import dan200.computercraft.api.peripheral.IPeripheral;
import engineer.multiperipheral.MultiPeripheral;
import engineer.multiperipheral.api.IHostedPeripheral;
import engineer.multiperipheral.wrapper.MethodFinder;

public class IExternalPeripheralWrapper extends MethodFinder implements IPeripheral
{
	public final IHostedPeripheral instance;
	public final boolean isPeripheral;
	public final boolean isValid;
	
	public IExternalPeripheralWrapper(IHostedPeripheral peripheral, World world, int x, int y, int z, int side)
	{
		super(peripheral);
		this.instance = peripheral;
		
		this.isValid = (this.instance != null);
		this.isPeripheral = (this.isValidPosition(world, x, y, z, side) && this.getType() != null);
	}

	@Override
	public String getType()
	{
		if(this.isValid)
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
		}
		return null;
	}
	
	@Override
	public void attach(IComputerAccess computer) 
	{
		if(this.isValid)
		{
			try
			{
				this.instance.attach(computer);
			}
			catch(Exception e)
			{
				MultiPeripheral.Log.severe(String.format("Cannot call IExternalPeripheral.attach on %s", this.instance.getClass().getName()));
				MultiPeripheral.logThrowable(e);
			}
		}
	}

	@Override
	public void detach(IComputerAccess computer) 
	{
		if(this.isValid)
		{
			try
			{
				this.instance.detach(computer);
			}
			catch(Exception e)
			{
				MultiPeripheral.Log.severe(String.format("Cannot call IExternalPeripheral.detach on %s", this.instance.getClass().getName()));
				MultiPeripheral.logThrowable(e);
			}
		}
	}

	@Override
	public boolean equals(IPeripheral other) 
	{
		if(other instanceof IExternalPeripheralWrapper)
		{
			IExternalPeripheralWrapper wrapper = (IExternalPeripheralWrapper) other;
			return wrapper.instance.equals(this.instance);
		}
		return false;
	}
	
	private boolean isValidPosition(World world, int x, int y, int z, int side)
	{
		boolean isValid = false;
		try 
		{
			isValid = this.instance.isValidPosition(world, x, y, z, side);
		} 
		catch(Exception e) 
		{
			MultiPeripheral.Log.severe(String.format("Cannot call IExternalPeripheral.isValidPosition on %s", this.instance.getClass().getName()));
			MultiPeripheral.logThrowable(e);
		}
		return isValid;
	}
}
