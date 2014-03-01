package engineer.multiperipheral.wrapper;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import dan200.computercraft.api.lua.ILuaContext;
import dan200.computercraft.api.peripheral.IComputerAccess;
import engineer.multiperipheral.MultiPeripheral;
import engineer.multiperipheral.api.LuaArg;
import engineer.multiperipheral.api.LuaMethod;
import engineer.multiperipheral.api.LuaMethod.Check;
import engineer.multiperipheral.api.LuaMethod.Describe;
import engineer.multiperipheral.util.ArrayUtil.ArrayIterator;
import engineer.multiperipheral.util.wrapper.MethodWrapperUtil;
import engineer.multiperipheral.util.ReflectionUtil;

public class MethodWrapper
{
	private final Object instance;
	private final Method method;
	
	private final LuaMethod luamethod;
	public final boolean isLuaMethod;
	public final String methodName;
	
	public final Describe describe;
	public final boolean isDescribed;
	
	private final Check check;
	private final boolean needsChecked;
	
	public MethodWrapper(Object instance, Method method)
	{
		this.instance = instance;
		this.method = method;
		
		if(method.isAnnotationPresent(LuaMethod.class))
		{
			this.luamethod = method.getAnnotation(LuaMethod.class);
			this.methodName = this.luamethod.name().split(" ")[0];
			
			if(handleLuaMethod())
			{
				if(method.isAnnotationPresent(Describe.class))
				{
					this.describe = method.getAnnotation(Describe.class);
					this.isDescribed = true;
				}
				else
				{
					this.describe = null;
					this.isDescribed = false;
				}
				
				if(method.isAnnotationPresent(Check.class))
				{
					this.check = method.getAnnotation(Check.class);
					this.needsChecked = handleCheck();
				}
				else
				{
					this.check = null;
					this.needsChecked = false;
				}
				this.isLuaMethod = (this.needsChecked ? invokeCheck() : true);
				return;
			}
		}
		else
		{
			this.luamethod  = null;
			this.methodName = null;
		}
		
		this.isLuaMethod = false;
		this.isDescribed = false;
		this.needsChecked= false;
			
		this.describe  = null;
		this.check     = null;	
	}
	
	private boolean handleLuaMethod()
	{
		LuaArg returnType = MethodWrapperUtil.getArg(this.method.getReturnType());
		if(returnType != null && returnType.equals(this.luamethod.output()) && MethodWrapperUtil.isOutput(returnType))
		{
			Class<?>[] parameters = this.method.getParameterTypes();
			LuaArg[] luaMethodArgs = MethodWrapperUtil.filterInput(this.luamethod.input());
			if(parameters.length == luaMethodArgs.length)
			{
				for(int i = 0; i < parameters.length; i++)
				{
					LuaArg arg = MethodWrapperUtil.getArg(parameters[i]);
					if(arg == null || !MethodWrapperUtil.isInput(arg) || !arg.equals(luaMethodArgs[i]))
						return false;
				}
				return true;
			}
		}
		return false;
	}
	
	private boolean handleCheck()
	{
		Method method = ReflectionUtil.findMethod(this.instance.getClass(), this.check.value(), new Class<?>[0]);
		if(method != null)
		{
			Class<?> clazz = method.getReturnType();
			if(clazz == boolean.class || clazz == Boolean.class)
				return true;
		}

		return false;
	}
	
	private boolean invokeCheck()
	{
		if(this.needsChecked)
		{
			Method method = ReflectionUtil.findMethod(this.instance.getClass(), this.check.value(), new Class<?>[0]);
			Object value = ReflectionUtil.invokeMethod(this.instance, method, new Object[0]);
			
			if(value != null)
				return ((Boolean) value).booleanValue();
		}
		return false;
	}
	
	public Object[] invoke(IComputerAccess pc, ILuaContext context, Object ... parameters) throws Exception
	{
		if(this.isLuaMethod)
		{
			Class<?>[] methodParams = this.method.getParameterTypes();
			
			LuaArg[] args = this.luamethod.input();
			Object[] input = new Object[args.length];
			ArrayIterator<Object> it = new ArrayIterator<Object>(parameters);
			
			for(int i = 0; i < args.length; i++)
			{
				if(args[i].equals(LuaArg.ComputerAccess))
					input[i] = pc;
				else if(args[i].equals(LuaArg.LuaContext))
					input[i] = context;
				else
					input[i] = MethodWrapperUtil.transformObject(it.next(), args[i], methodParams[i]);
			}
			
			return execute(this, input);
		}
		return null;
	}
	
	public boolean multipleOutput()
	{	
		if(this.isLuaMethod)
			return this.luamethod.output() == LuaArg.Multiple;
		return false;
	}

	public LuaArg[] getInput()
	{
		return this.luamethod.input();
	}
	
	@Override
	public boolean equals(Object e)
	{
		if(e instanceof MethodWrapper)
		{
			MethodWrapper m = (MethodWrapper) e;
			if(this.method.equals(m.method) && m.isLuaMethod == this.isLuaMethod)
				return true;
		}
		return false;
	}
	
	private static Object[] execute(MethodWrapper luamethod, Object ... args) throws Exception
	{
		Object[] output = null;
		
		try 
		{
			if(luamethod.multipleOutput())
				output = (Object[]) luamethod.method.invoke(luamethod.instance, args);
			else
				output = new Object[] { luamethod.method.invoke(luamethod.instance, args) };
		} 
		catch (IllegalAccessException e) 
		{
			MultiPeripheral.Log.severe("No access while executing.");
			MultiPeripheral.logThrowable(e);

		}
		catch (IllegalArgumentException e)
		{
			MultiPeripheral.Log.severe("IllegalArgumentException while executing.");
			MultiPeripheral.logThrowable(e);

		}
		catch (InvocationTargetException e) 
		{
			String message = null;
			Throwable cause = e.getCause();
			
			while(message == null && cause != null)
			{
				message = cause.getMessage();
				cause = cause.getCause();
			}
			
			throw new Exception(message);
		}
		
		for(int i = 0; i < output.length; i++)
		{
			if(output[i] instanceof Boolean)
				output[i] = ((Boolean) output[i]).booleanValue();
			else if(output[i] instanceof Float)
				output[i] = ((Float) output[i]).floatValue();
			else if(output[i] instanceof Double)
				output[i] = ((Double) output[i]).doubleValue();
			else if(output[i] instanceof Integer)
				output[i] = ((Integer) output[i]).intValue();
		}
		
		return output;
	}
}