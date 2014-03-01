package engineer.multiperipheral;

import java.util.logging.Logger;

import org.apache.commons.lang3.exception.ExceptionUtils;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;
import engineer.multiperipheral.handler.provider.PeripheralProvider;
import engineer.multiperipheral.lib.ModInfo;

@Mod(modid = ModInfo.ID, name = ModInfo.NAME, version = ModInfo.VERSION, dependencies = ModInfo.DEPENDENCIES)
@NetworkMod(clientSideRequired = ModInfo.clientReq, serverSideRequired = ModInfo.serverReq)
public class MultiPeripheral 
{	
	public static Logger Log;
	
	@Mod.EventHandler
	public void preLoad(FMLPreInitializationEvent event)
	{
		PeripheralProvider.init(new PeripheralProvider());
		Log = event.getModLog();
	}
	
	public static void logThrowable(Throwable t)
	{
		if(t != null)
			Log.severe(ExceptionUtils.getStackTrace(t));
	}
}
