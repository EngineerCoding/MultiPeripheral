package engineer.multiperipheral;

import cpw.mods.fml.common.network.NetworkRegistry;
import engineer.multiperipheral.block.MultiPeripheralBlock;
import engineer.multiperipheral.handler.GuiHandler;

public class CommonProxy 
{
	public static class Blocks
	{
		public static MultiPeripheralBlock multiPeripheralBlock;
		
	}
	
	public void init()
	{
		this.makeAndRegisterBlocks();
		
		NetworkRegistry.instance().registerGuiHandler(MultiPeripheral.instance, new GuiHandler());
	}
	
	private void makeAndRegisterBlocks()
	{
		Blocks.multiPeripheralBlock = new MultiPeripheralBlock(501);
	}
}
