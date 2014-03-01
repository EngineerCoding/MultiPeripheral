package engineer.multiperipheral.api;

import net.minecraft.nbt.NBTTagCompound;

/**
 * This interface is very experimental, this definitely needs work. Though it is in an usable state I would say, because it only breaks when another
 * mod (and mounts the peripheral) gets added between added between saves. This works the same as {@link IHostedPeripheral}, and add NBT data access.
 * @see IHostedPeripheral
 * @author Wesley 'Engineer' Ameling
 */
public interface INBTHostedPeripheral extends IHostedPeripheral
{
	public void writeToNBT(NBTTagCompound tag);
	
	public void readFromNBT(NBTTagCompound tag);
}
