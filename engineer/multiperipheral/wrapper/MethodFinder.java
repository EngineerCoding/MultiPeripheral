package engineer.multiperipheral.wrapper;

import java.lang.reflect.Method;
import java.util.ArrayList;

import dan200.computercraft.api.lua.ILuaContext;
import dan200.computercraft.api.peripheral.IComputerAccess;
import engineer.multiperipheral.api.IMethodTable;

public abstract class MethodFinder
{
	public final MethodWrapper[] methods;
	public final boolean hasMethods;
	
	public MethodFinder(IMethodTable periph)
	{
		if(periph != null || this instanceof IMethodTable)
		{
			IMethodTable handler = (periph != null ? periph : (IMethodTable) this);
			ArrayList<MethodWrapper> methods = new ArrayList<MethodWrapper>();
			
			for(Method method : handler.getClass().getMethods())
			{
				MethodWrapper luamethod = new MethodWrapper(handler, method);
				if(luamethod.isLuaMethod)
					methods.add(luamethod);
			}
			
			if(methods.size() > 0)
			{
				this.methods = methods.toArray(new MethodWrapper[methods.size()]);
				this.hasMethods = true;
				return;
			}
		}

		this.methods = new MethodWrapper[] {};
		this.hasMethods = false;
	}

	public String[] getMethodNames() 
	{
		String[] names = new String[this.methods.length];
		for(int i = 0; i < names.length; i++)
			if(this.methods[i] != null)
				names[i] = this.methods[i].methodName;

		return names;
	}
	
	public Object[] callMethod(IComputerAccess computer, ILuaContext context, int method, Object[] arguments) throws Exception 
	{
		if(this.methods[method] != null)
			return this.methods[method].invoke(computer, context, arguments);
		return null;
	}
}
