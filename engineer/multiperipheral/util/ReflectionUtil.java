package engineer.multiperipheral.util;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import engineer.multiperipheral.MultiPeripheral;

public class ReflectionUtil 
{
	public static Class<?> getClassForName(String name)
	{
		try
		{
			return Class.forName(name);
		}
		catch(Exception e)
		{
			MultiPeripheral.logThrowable(e);
			return null;
		}
	}
	
	public static Field getDeclaredField(Class<?> clazz, String name)
	{
		if(clazz != null && name != null)
		{
			try
			{
				return clazz.getDeclaredField(name);
			}
			catch(Exception e) 
			{
				MultiPeripheral.logThrowable(e);
			}
		}
		return null;
	}
	
	public static Object getFieldValue(Field field, Object instance)
	{
		if(field != null)
		{
			try
			{
				field.setAccessible(true);
				return field.get(instance);
			}
			catch(Exception e)
			{
				MultiPeripheral.logThrowable(e);
			}
		}
		return null;
	}
	
	public static boolean setFieldValue(Field field, Object instance, Object value)
	{
		if(field != null)
		{
			try
			{
				field.setAccessible(true);
				field.set(instance, value);
				return true;
			}
			catch (Exception e)
			{
				MultiPeripheral.logThrowable(e);
			}
		}
		return false;
	}
	
	public static Method findMethod(Class<?> instance, String method, Class<?> ... parameters)
	{
		if(instance != null && method != null)
		{
			try
			{
				return instance.getMethod(method, parameters);
			}
			catch(Exception e)
			{
				MultiPeripheral.logThrowable(e);
			}
		}
		return null;
	}
	
	public static Object invokeMethod(Object instance, Method method, Object ... parameters)
	{
		if(method != null)
		{
			try
			{
				return method.invoke(instance, parameters);
			}
			catch(Exception e)
			{
				MultiPeripheral.logThrowable(e);
			}
		}
		return null;
	}
	
	public static Object newInstance(Class<?> clazz)
	{
		try
		{
			return clazz.newInstance();
		}
		catch (Exception e)
		{
			MultiPeripheral.logThrowable(e);
			return null;
		}
	}
}
