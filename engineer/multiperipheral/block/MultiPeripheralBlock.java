package engineer.multiperipheral.block;

import java.util.HashMap;

import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import engineer.multiperipheral.MultiPeripheral;
import engineer.multiperipheral.block.tileentity.TileMultiPeripheralBlock;

public class MultiPeripheralBlock extends BlockContainer
{
	private class Position
	{
		public final int x;
		public final int y;
		public final int z;
		public final int dimension;
		
		public Position(int x, int y, int z, int dimension)
		{
			this.x = x;
			this.y = y;
			this.z = z;
			this.dimension = dimension;
		}
		
		@Override
		public boolean equals(Object other)
		{
			if(other instanceof Position)
			{
				Position pos = (Position) other;
				return pos.x == this.x && pos.y == this.y && pos.z == this.z && pos.dimension == this.dimension;
			}
			return false;
		}
	}
	
	private final HashMap<Position, HashMap<Integer, TileEntity>> map = new HashMap<Position, HashMap<Integer, TileEntity>>();
	private boolean isTileSet = false;
	
	public MultiPeripheralBlock(int id)
	{
		super(id, Material.rock);
		this.setCreativeTab(CreativeTabs.tabBlock);
	}

	public synchronized void getBlockForSlot(World world, int x, int y, int z, int slot)
	{
		TileEntity tile = world.getBlockTileEntity(x, y, z);
		if(tile instanceof TileMultiPeripheralBlock)
		{
			TileMultiPeripheralBlock t = (TileMultiPeripheralBlock)tile;
			ItemStack stack = t.getStackInSlot(slot);
			if(stack != null)
			{
				Item item = stack.getItem();
				if(item instanceof ItemBlock)
				{
					int blockID = ((ItemBlock) item).getBlockID();
					
					Block block = Block.blocksList[blockID];
					if(block instanceof BlockContainer)
					{
						Position pos = new Position(x, y, z, world.provider.dimensionId);
						if(!map.containsKey(pos))
						{
							HashMap<Integer, TileEntity> tileMap = new HashMap<Integer, TileEntity>();
							tileMap.put(blockID, ((BlockContainer)block).createNewTileEntity(world));
							tileMap.put(this.blockID, this.createNewTileEntity(world));
							this.map.put(pos, tileMap);
						}
						world.setBlockTileEntity(x, y, z, this.map.get(pos).get(blockID));
					}
				}
			}
		}
	}
	
	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ)
	{
		player.openGui(MultiPeripheral.instance, 0, world, x, y, z);
		return true;
	}
	
	@Override
	public TileEntity createNewTileEntity(World world) 
	{
		return new TileMultiPeripheralBlock();
	}
	
	@Override
	public boolean canDragonDestroy(World world, int x, int y, int z)
	{
		return false;
	}
}
