package engineer.multiperipheral.handler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.chunk.Chunk;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.EventPriority;
import net.minecraftforge.event.ForgeSubscribe;
import net.minecraftforge.event.world.ChunkEvent;
import net.minecraftforge.event.world.WorldEvent;
import engineer.multiperipheral.nbt.NBTManager;

public class WorldEventHandler 
{
	public final NBTManager manager;
	
	public WorldEventHandler(NBTManager manager)
	{
		MinecraftForge.EVENT_BUS.register(this);
		this.manager = manager;
	}
	
	@ForgeSubscribe(priority=EventPriority.HIGHEST)
	public void onWorldSave(WorldEvent.Save saveEvent)
	{
		if(saveEvent.world != null && !saveEvent.world.isRemote)
		{
			@SuppressWarnings("unchecked")
			List<TileEntity> tiles = saveEvent.world.loadedTileEntityList;
			Iterator<TileEntity> it = tiles.iterator();
			
			Map<Integer, List<Integer>> map = new HashMap<Integer, List<Integer>>();
			while(it.hasNext())
			{
				TileEntity tile = it.next();
				int x = tile.xCoord >> 4;
				int z = tile.zCoord >> 4;
				
				
				if(!map.containsKey(x))
				{
					map.put(x, new ArrayList<Integer>());
					map.get(x).add(z);
					this.manager.onChunkUnload(saveEvent.world.getChunkFromChunkCoords(x, z));
				}
				else
				{
					List<Integer> list = map.get(x);
					if(!list.contains(z))
					{
						list.add(z);
						this.manager.onChunkUnload(saveEvent.world.getChunkFromChunkCoords(x, z));
					}
				}
			}
			map.clear();
		}
	}
	
	@ForgeSubscribe(priority=EventPriority.HIGHEST)
	public void onChunkUnload(ChunkEvent.Unload unloadEvent)
	{
		Chunk chunk = unloadEvent.getChunk();
		if(chunk != null && !chunk.worldObj.isRemote)
			this.manager.onChunkUnload(chunk);
	}
}
