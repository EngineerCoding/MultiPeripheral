package engineer.multiperipheral;

import java.util.logging.Logger;

import org.apache.commons.lang3.exception.ExceptionUtils;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;
import cpw.mods.fml.common.registry.GameRegistry;
import engineer.multiperipheral.CommonProxy.Blocks;
import engineer.multiperipheral.block.tileentity.TileMultiPeripheralBlock;
import engineer.multiperipheral.handler.provider.PeripheralProvider;
import engineer.multiperipheral.lib.ModInfo;

@Mod(modid = ModInfo.ID, name = ModInfo.NAME, version = ModInfo.VERSION, dependencies = ModInfo.DEPENDENCIES)
@NetworkMod(clientSideRequired = ModInfo.clientReq, serverSideRequired = ModInfo.serverReq)
public class MultiPeripheral 
{	
	@SidedProxy(clientSide = ModInfo.PROXY_client, serverSide = ModInfo.PROXY_server)
	public static CommonProxy proxy;
	
	@Mod.Instance(ModInfo.ID)
	public static MultiPeripheral instance;
	
	public static Logger Log;
	
	@Mod.EventHandler
	public void preLoad(FMLPreInitializationEvent event)
	{
		proxy.init();
		Log = event.getModLog();
		PeripheralProvider.init(new PeripheralProvider());	
	}
	
	@Mod.EventHandler
	public void load(FMLInitializationEvent event)
	{
		GameRegistry.registerTileEntity(TileMultiPeripheralBlock.class, "peripheralTile");
		GameRegistry.registerBlock(Blocks.multiPeripheralBlock, "peripheralBlock");
	}
	
	public static void logThrowable(Throwable t)
	{
		if(t != null)
			Log.severe(ExceptionUtils.getStackTrace(t));
	}
}
