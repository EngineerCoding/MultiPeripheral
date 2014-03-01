package engineer.multiperipheral.api;

import java.lang.reflect.Method;

import dan200.computercraft.api.lua.ILuaObject;

/**
 * @author Wesley 'Engineer' Ameling
 */
public class MultiPeripheralAPI 
{	
	/**
	 * Registers the {@link IHostedPeripheral}
	 * 
	 * @see ILuaPeripheral
	 * @return bool succes
	 */
	public static boolean registerIHostedPeripheral(IHostedPeripheral peripheral) {
		Object obj = invokeMethod(registerIHostedPeripheral, peripheral);
		if(obj != null && obj instanceof Boolean)
			return ((Boolean) obj).booleanValue();
		return false;
	}
	
	/**
	 * Registers the {@link INBTHostedPeripheral}
	 * 
	 * @see ILuaPeripheral
	 * @return bool succes
	 */
	public static boolean registerINBTHostedPeripheral(INBTHostedPeripheral peripheral) {
		return registerIHostedPeripheral(peripheral);
	}
	
	/**
	 * Converts {@link IMethodTable} to {@link ILuaObject}
	 */
	public static ILuaObject convertIMethodTable(IMethodTable table)
	{
		Object obj = invokeMethod(convertIMethodTable, table);
		if(obj instanceof ILuaObject)
			return (ILuaObject) obj;
		return null;
	}
	
	
	// *** INTERNAL HANDLING *** \\
	
	private static Object invokeMethod(Method method, Object ... params) {
		try {
			return method.invoke(null, params);
		} catch(Exception e) {
			return null;
		}
	}
	
	private static Method getMethod(Class<?> clazz, String methodName, Class<?>[] params) {
		try {
			return clazz.getMethod(methodName, params);
		} catch (Exception e) {
			return null;
		}
	}
	
	static {
		Class<?> clazz = null;
		try {
			clazz = Class.forName("engineer.multiperipheral.handler.api.Handler");
		} catch(Exception e) {
			// Couldnt find the class
		}
		
		isAvailable = (clazz != null);
		registerIHostedPeripheral = getMethod(clazz, "registerIHostedPeripheral", new Class<?>[] { IHostedPeripheral.class });
		convertIMethodTable = getMethod(clazz, "convertIMethodTable", new Class<?>[] { IMethodTable.class });
	}
	
	public static final boolean isAvailable;
	private static final Method registerIHostedPeripheral;
	private static final Method convertIMethodTable;
}
