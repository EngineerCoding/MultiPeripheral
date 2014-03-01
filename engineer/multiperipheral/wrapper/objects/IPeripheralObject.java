package engineer.multiperipheral.wrapper.objects;

import dan200.computercraft.api.lua.ILuaContext;
import dan200.computercraft.api.lua.ILuaObject;
import dan200.computercraft.api.peripheral.IComputerAccess;
import dan200.computercraft.api.peripheral.IPeripheral;
import engineer.multiperipheral.util.wrapper.MultiIPeripheralUtil;

public class IPeripheralObject implements ILuaObject
{
	private final IPeripheral handler;
	private final IComputerAccess pc;
	
	public IPeripheralObject(IPeripheral handler, IComputerAccess pc)
	{
		this.handler = handler;
		this.pc = pc;
	}

	@Override
	public String[] getMethodNames()
	{
		return MultiIPeripheralUtil.getMethodNames(this.handler);
	}

	@Override
	public Object[] callMethod(ILuaContext context, int method, Object[] arguments) throws Exception 
	{
		if(this.handler != null)
			return this.handler.callMethod(this.pc, context, method, arguments);
		return null;
	}
}
