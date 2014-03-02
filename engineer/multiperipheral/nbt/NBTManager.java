package engineer.multiperipheral.nbt;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.ChunkPosition;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import engineer.multiperipheral.MultiPeripheral;
import engineer.multiperipheral.api.INBTHostedPeripheral;
import engineer.multiperipheral.handler.WorldEventHandler;
import engineer.multiperipheral.wrapper.MultiIPeripheral;

public class NBTManager 
{	
	private final FileManager fileManager;
	private ArrayList<PositionInfo> list = new ArrayList<PositionInfo>();
	
	public NBTManager()
	{
		new WorldEventHandler(this);
		this.fileManager = new FileManager();
	}
	
	public void onChunkUnload(Chunk chunk)
	{
		@SuppressWarnings("unchecked")
		Map<ChunkPosition, TileEntity> tiles = chunk.chunkTileEntityMap;
		Iterator<TileEntity> itMap = tiles.values().iterator();
		while(itMap.hasNext())
		{
			TileEntity tile = itMap.next();
			
			Iterator<PositionInfo> itPos = this.list.iterator();
			while(itPos.hasNext())
			{
				PositionInfo posInfo = itPos.next();
				if(posInfo.xPos == tile.xCoord && posInfo.yPos == tile.yCoord && posInfo.zPos == tile.zCoord)
				{
					this.onChunkUnload(posInfo);
					itPos.remove();
				}
			}
		}
	}
	
	public void onChunkUnload(PositionInfo info)
	{
		NBTTagCompound nbt = this.fileManager.getNBT(info.dimensionId);
		NBTTagCompound positionNBT = nbt.getCompoundTag(String.format("%d:%d:%d", info.xPos, info.yPos, info.zPos));
		for(int i = 0; i < info.handlers.length; i++)
		{
			NBTTagCompound handlerNBT = positionNBT.getCompoundTag(String.format("%d", i));
			try
			{
				info.handlers[i].writeToNBT(handlerNBT);
			}
			catch(Exception e)
			{
				MultiPeripheral.Log.severe("Cannot call ISavedExternalPeripheral.writeToNBT on " + info.handlers[i].getClass().getName());
				MultiPeripheral.logThrowable(e);
			}
			positionNBT.setCompoundTag(String.format("%d", i), handlerNBT);
		}
		nbt.setCompoundTag(positionNBT.getName(), positionNBT);
		this.fileManager.writeCompound(nbt, info.dimensionId);
	}
	
	public boolean addMultiIPeripheral(MultiIPeripheral peripheral, World world, int x, int y, int z)
	{
		PositionInfo info = new PositionInfo(world, x, y, z, peripheral);
		if(info.valid)
		{
			Iterator<PositionInfo> it = this.list.iterator();
			while(it.hasNext())
			{
				PositionInfo _info = it.next();
				if(_info.dimensionId == info.dimensionId && _info.xPos == info.xPos && _info.yPos == info.yPos && _info.zPos == info.zPos)
				{
					this.onChunkUnload(_info);
					it.remove();
				}
			}
			
			NBTTagCompound nbt = this.fileManager.getNBT(info.dimensionId);
			NBTTagCompound positionNBT = nbt.getCompoundTag(String.format("%d:%d:%d", x, y, z));
			for(INBTHostedPeripheral periph : info.handlers)
			{
				String name = null;
				try 
				{
					name = periph.getType();
				}
				catch(Exception e)
				{
					MultiPeripheral.Log.severe("Cannot call INBTHostedlPeripheral.getType on " + periph.getClass().getName());
					MultiPeripheral.logThrowable(e);
				}
				
				if(name != null)
				{
					NBTTagCompound handlerNBT = positionNBT.getCompoundTag(String.format("%s", name));
					try
					{
						periph.readFromNBT(handlerNBT);
					}
					catch(Exception e)
					{
						MultiPeripheral.Log.severe("Cannot call INBTHostedlPeripheral.readFromNBT on " + periph.getClass().getName());
						MultiPeripheral.logThrowable(e);
					}
					positionNBT.setCompoundTag(String.format("%s", name), handlerNBT);
				}
			}
			nbt.setCompoundTag(positionNBT.getName(), positionNBT);
			this.fileManager.writeCompound(nbt, info.dimensionId);
			this.list.add(info);
		}
		return false;
	}
}
