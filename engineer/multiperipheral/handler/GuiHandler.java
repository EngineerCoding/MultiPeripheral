package engineer.multiperipheral.handler;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import cpw.mods.fml.common.network.IGuiHandler;
import engineer.multiperipheral.block.tileentity.TileMultiPeripheralBlock;
import engineer.multiperipheral.client.gui.inventory.GuiMultiPeripheral;
import engineer.multiperipheral.inventory.ContainerMultiPeripheral;

public class GuiHandler implements IGuiHandler
{
	@Override
	public Object getClientGuiElement(int guiid, EntityPlayer player, World world, int x, int y, int z) 
	{
		TileEntity tile = world.getBlockTileEntity(x, y, z);
		
		// Double check ;)
		if(tile instanceof TileMultiPeripheralBlock && tile instanceof IInventory)
			return new GuiMultiPeripheral(player.inventory, (IInventory) tile);
		
		return null;
	}

	@Override
	public Object getServerGuiElement(int guiid, EntityPlayer player, World world, int x, int y, int z) 
	{
		TileEntity tile = world.getBlockTileEntity(x, y, z);
		
		// Double check ;)
		if(tile instanceof TileMultiPeripheralBlock && tile instanceof IInventory)
			return new ContainerMultiPeripheral(player.inventory, (IInventory) tile);
		
		return null;
	}

}
