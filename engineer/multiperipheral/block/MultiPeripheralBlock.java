package engineer.multiperipheral.block;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import engineer.multiperipheral.MultiPeripheral;
import engineer.multiperipheral.block.tileentity.TileMultiPeripheralBlock;

public class MultiPeripheralBlock extends BlockContainer
{
	public MultiPeripheralBlock(int id)
	{
		super(id, Material.rock);
		this.setCreativeTab(CreativeTabs.tabBlock);
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
}
