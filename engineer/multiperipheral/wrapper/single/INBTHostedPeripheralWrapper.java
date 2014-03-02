package engineer.multiperipheral.wrapper.single;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import engineer.multiperipheral.MultiPeripheral;
import engineer.multiperipheral.api.INBTHostedPeripheral;

public class INBTHostedPeripheralWrapper extends IHostedPeripheralWrapper
{
	public INBTHostedPeripheralWrapper(INBTHostedPeripheral peripheral, World world, int x, int y, int z, int side) 
	{
		super(peripheral, world, x, y, z, side);
	}
	
	public void writeToNBT(NBTTagCompound tag)
	{
		try
		{
			((INBTHostedPeripheral)this.instance).writeToNBT(tag);
		} 
		catch(Exception e)
		{
			MultiPeripheral.Log.severe("Cannot call INBTHostedlPeripheral.writeToNBT on " + this.instance.getClass().getName());
			MultiPeripheral.logThrowable(e);
		}
	}
	
	public void readFromNBT(NBTTagCompound tag)
	{
		try
		{
			((INBTHostedPeripheral)this.instance).readFromNBT(tag);
		} 
		catch(Exception e)
		{
			MultiPeripheral.Log.severe("Cannot call INBTHostedlPeripheral.readFromNBT on " + this.instance.getClass().getName());
			MultiPeripheral.logThrowable(e);
		}
	}
}
