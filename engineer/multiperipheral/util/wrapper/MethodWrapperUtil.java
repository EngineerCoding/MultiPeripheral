package engineer.multiperipheral.util.wrapper;

import java.util.ArrayList;
import java.util.Map;

import dan200.computercraft.api.lua.ILuaContext;
import dan200.computercraft.api.lua.ILuaObject;
import dan200.computercraft.api.peripheral.IComputerAccess;
import engineer.multiperipheral.api.LuaArg;

public class MethodWrapperUtil 
{	
	public static Object transformObject(Object obj, LuaArg arg, Class<?> methodParam) throws Exception
	{
		LuaArg objArg = getArg(obj.getClass());
		if(objArg != null && objArg.equals(arg))
		{
			if(methodParam != null && obj instanceof Number && objArg.equals(LuaArg.Number))
			{
				Number number = (Number) obj;
				if(methodParam == Float.class || methodParam == float.class)
					return number.floatValue();
				if(methodParam == Double.class || methodParam == double.class)
					return number.doubleValue();
				if(methodParam == Integer.class || methodParam == int.class)
					return number.intValue();
			}
			return obj;
		}
		else
		{
			throw new Exception(getErrorMessage(arg, (objArg == null ? LuaArg.Nil : objArg)));
		}
	}
	
	public static LuaArg[] filterInput(LuaArg[] input)
	{
		ArrayList<LuaArg> args = new ArrayList<LuaArg>();
		for(LuaArg arg : input)
			if(isInput(arg))
				args.add(arg);
		
		return args.toArray(new LuaArg[args.size()]);
	}
	
	public static boolean isInput(LuaArg arg)
	{
		if(arg != null)
			return !arg.equals(LuaArg.Nil)  && !arg.equals(LuaArg.Multiple) 
						&& !arg.equals(LuaArg.Object) && !arg.equals(LuaArg.LuaObject);
		return false;
	}
	
	public static boolean isOutput(LuaArg arg)
	{
		if(arg != null)
			return !arg.equals(LuaArg.ComputerAccess) && !arg.equals(LuaArg.LuaContext);
		return false;
	}
	
	public static String getErrorMessage(LuaArg c, LuaArg e)
	{
		return c.name() + " expected, got " + e.name().toLowerCase();
	}
	
	public static Class<?> getClass(LuaArg arg)
	{
		if(arg != null)
			switch(arg)
			{
				case ComputerAccess: return IComputerAccess.class;
				case LuaContext: return ILuaContext.class;
				case LuaObject: return ILuaObject.class;
				case Number: return double.class;
				case Boolean: return boolean.class;
				case String: return String.class;
				case Table: return Map.class;
				case Nil: return Void.TYPE;
				case Multiple: return Object[].class;
				case Object: return Object.class;
			}
		return null;
	}
	
	public static LuaArg getArg(Class<?> arg)
	{
		if(arg != null)
			if(IComputerAccess.class.isAssignableFrom(arg)) return LuaArg.ComputerAccess;
			if(ILuaContext.class.isAssignableFrom(arg)) return LuaArg.ComputerAccess;
			if(double.class.isAssignableFrom(arg)) return LuaArg.Number;
			if(Double.class.isAssignableFrom(arg)) return LuaArg.Number;
			if(int.class.isAssignableFrom(arg)) return LuaArg.Number;
			if(Integer.class.isAssignableFrom(arg)) return LuaArg.Number;
			if(float.class.isAssignableFrom(arg)) return LuaArg.Number;
			if(Float.class.isAssignableFrom(arg)) return LuaArg.Number;
			if(boolean.class.isAssignableFrom(arg)) return LuaArg.Boolean;
			if(Boolean.class.isAssignableFrom(arg)) return LuaArg.Boolean;
			if(String.class.isAssignableFrom(arg)) return LuaArg.String;
			if(Map.class.isAssignableFrom(arg)) return LuaArg.Table;
			if(ILuaObject.class.isAssignableFrom(arg)) return LuaArg.LuaObject;
			if(Object[].class.isAssignableFrom(arg)) return LuaArg.Multiple;
		return null;
	}
}
