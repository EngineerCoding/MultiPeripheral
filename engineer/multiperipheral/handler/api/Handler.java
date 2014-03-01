package engineer.multiperipheral.handler.api;

import dan200.computercraft.api.lua.ILuaObject;
import engineer.multiperipheral.api.IHostedPeripheral;
import engineer.multiperipheral.api.IMethodTable;

public class Handler
{	
	public static boolean registerIHostedPeripheral(IHostedPeripheral peripheral)
	{
		return Registry.registerIHostedPeripheral(peripheral);
	}
	
	public static ILuaObject convertIMethodTable(IMethodTable table)
	{
		return Converter.convertIMethodTable(table);
	}
}
