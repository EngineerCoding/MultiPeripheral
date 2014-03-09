package engineer.multiperipheral.wrapper.objects;

import dan200.computercraft.api.lua.ILuaContext;
import dan200.computercraft.api.lua.ILuaObject;
import engineer.multiperipheral.api.IMethodTable;
import engineer.multiperipheral.wrapper.MethodFinder;

public class IMethodTableWrapper extends MethodFinder implements ILuaObject, IMethodTable
{
	public IMethodTableWrapper(IMethodTable table) 
	{
		super(table);
	}
	
	@Override
	public final Object[] callMethod(ILuaContext context, int method, Object[] arguments) throws Exception 
	{
		return callMethod(null, context, method, arguments);
	}
}
