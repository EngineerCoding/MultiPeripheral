package engineer.multiperipheral.nbt;

import java.util.ArrayList;

import net.minecraft.world.World;
import dan200.computercraft.api.peripheral.IPeripheral;
import engineer.multiperipheral.api.INBTHostedPeripheral;
import engineer.multiperipheral.wrapper.MultiIPeripheral;
import engineer.multiperipheral.wrapper.single.ISavedExternalPeripheralWrapper;

public class PositionInfo 
{
	public final World world;
	public final int xPos;
	public final int yPos;
	public final int zPos;
	
	public final int dimensionId;
	public final INBTHostedPeripheral[] handlers;
	
	public final boolean valid;
	
	public PositionInfo(World world, int x, int y, int z, MultiIPeripheral peripheral)
	{
		this.world = world;
		this.xPos = x;
		this.yPos = y;
		this.zPos = z;
		
		if(world != null)
			this.dimensionId = world.provider.dimensionId;
		else
			this.dimensionId = 0;
		
		if(peripheral != null)
		{
			ArrayList<INBTHostedPeripheral> nbtTypes = new ArrayList<INBTHostedPeripheral>();
			for(int i = 0; i < peripheral.types.length; i++)
			{
				IPeripheral periph = peripheral.getPeripheral(peripheral.types[i]);
				if(periph instanceof ISavedExternalPeripheralWrapper)
				{
					nbtTypes.add((INBTHostedPeripheral) ((ISavedExternalPeripheralWrapper) periph).instance);
				}
			}
			this.handlers = nbtTypes.toArray(new INBTHostedPeripheral[nbtTypes.size()]);
		}
		else
		{
			this.handlers = new INBTHostedPeripheral[0];
		}
		this.valid = (peripheral != null && world != null && this.handlers.length > 0);
	}
}
