package engineer.multiperipheral.wrapper.single;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import engineer.multiperipheral.MultiPeripheral;
import engineer.multiperipheral.api.INBTHostedPeripheral;

public class ISavedExternalPeripheralWrapper extends IExternalPeripheralWrapper
{
	public ISavedExternalPeripheralWrapper(INBTHostedPeripheral peripheral, World world, int x, int y, int z, int side) 
	{
		super(peripheral, world, x, y, z, side);
	}
	
	public void writeToNBT(NBTTagCompound tag)
	{
		try
		{
			((INBTHostedPeripheral)this.instance).writeToNBT(tag);
		} 
		catch(ClassCastException e)
		{
			MultiPeripheral.Log.severe(String.format("Internal error, report to mod author: cant cast to ISavedExternalPeripheral: %b %b", this.instance != null, INBTHostedPeripheral.class.isAssignableFrom(this.instance.getClass())));
		}
		catch(Exception e)
		{
			MultiPeripheral.Log.severe("Cannot call ISavedExternalPeripheral.writeToNBT on " + this.instance.getClass().getName());
			MultiPeripheral.logThrowable(e);
		}
	}
	
	public void readFromNBT(NBTTagCompound tag)
	{
		try
		{
			((INBTHostedPeripheral)this.instance).readFromNBT(tag);
		} 
		catch(ClassCastException e)
		{
			MultiPeripheral.Log.severe(String.format("Internal error, report to mod author: cant cast to ISavedExternalPeripheral: %b %b", this.instance != null, INBTHostedPeripheral.class.isAssignableFrom(this.instance.getClass())));
		}
		catch(Exception e)
		{
			MultiPeripheral.Log.severe("Cannot call ISavedExternalPeripheral.readFromNBT on " + this.instance.getClass().getName());
			MultiPeripheral.logThrowable(e);
		}
	}
}
