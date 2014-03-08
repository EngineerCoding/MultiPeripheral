package engineer.multiperipheral.lua;

import engineer.multiperipheral.api.IMethodTable;
import engineer.multiperipheral.api.LuaArg;
import engineer.multiperipheral.api.LuaMethod;
import engineer.multiperipheral.wrapper.MethodWrapper;

public class DescriptionObject implements IMethodTable
{
	private final MethodWrapper[] methods;

	public DescriptionObject(MethodWrapper[] methods)
	{
		this.methods = methods;
	}
	
	@LuaMethod(name="getInput", input={LuaArg.String}, output=LuaArg.String)
	public String getInput(String method)
	{
		if(method != null)
			for(MethodWrapper wrapper : this.methods)
				if(wrapper.isDescribed)
					return wrapper.describe.input();
		return null;
	}
	
	@LuaMethod(name="getInput", input={LuaArg.String}, output=LuaArg.String)
	public String getOutput(String method)
	{
		if(method != null)
			for(MethodWrapper wrapper : this.methods)
				if(wrapper.isDescribed)
					return wrapper.describe.output();
		return null;
	}
	
	@LuaMethod(name="getMethod", input={LuaArg.String}, output=LuaArg.String)
	public String getMethod(String method)
	{
		if(method != null)
			for(MethodWrapper wrapper : this.methods)
				if(wrapper.isDescribed)
					return wrapper.describe.input();
		return null;
	}
}