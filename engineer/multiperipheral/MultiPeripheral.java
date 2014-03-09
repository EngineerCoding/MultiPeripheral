package engineer.multiperipheral;

import java.util.logging.Logger;

import org.apache.commons.lang3.exception.ExceptionUtils;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;
import engineer.multiperipheral.handler.provider.PeripheralProvider;

@Mod(modid = ModInfo.ID, name = ModInfo.NAME, version = ModInfo.VERSION, dependencies = ModInfo.DEPENDENCIES)
@NetworkMod(clientSideRequired = ModInfo.clientReq, serverSideRequired = ModInfo.serverReq)
public class MultiPeripheral 
{	
	@Mod.EventHandler
	public void preLoad(FMLPreInitializationEvent event)
	{
		Log = event.getModLog();
		PeripheralProvider.init();
	}

	public static Logger Log;
	public static void logThrowable(Throwable t)
	{
		if(t != null)
			Log.severe(ExceptionUtils.getStackTrace(t));
	}
}
