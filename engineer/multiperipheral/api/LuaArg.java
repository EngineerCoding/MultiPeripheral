package engineer.multiperipheral.api;

import java.util.HashMap;

import dan200.computercraft.api.lua.ILuaContext;
import dan200.computercraft.api.lua.ILuaObject;
import dan200.computercraft.api.peripheral.IComputerAccess;

/**
 * This Enumeration is used for {@literal @}{@link LuaMethod}
 * @see LuaMethod
 * @see IComputerAccess
 * @see ILuaContext
 * @see ILuaObject
 * @author Wesley 'Engineer' Ameling
 */
public enum LuaArg 
{
	/**
	 * <p><strong>INPUT ONLY!</strong></p>
	 * Refers to {@link IComputerAccess}
	 */
	ComputerAccess, 
	
	/**
	 * <p><strong>INPUT ONLY!</strong></p>
	 * Refers to {@link ILuaContext}
	 */
	LuaContext,
	
	/**
	 * <p><strong>OUTPUT ONLY!</strong></p>
	 * Refers to {@link ILuaObject}
	 */
	LuaObject,
	
	/**
	 * Refers to the types:
	 * <ul><li>int</li><li>double</li><li>float</li></ul>
	 * Refers to the Objects:
	 * <ul><li>{@link Integer}</li><li>{@link Double}</li><li>{@link Float}</li></ul>
	 */
	Number,
	
	/**
	 * Refers to the type: boolean.
	 * Refers to the Object: {@link Boolean}
	 */
	Boolean,
	
	/**
	 * Refers to the Object: {@link String}
	 */
	String,
	
	/**
	 * Refers to the Object: {@link HashMap}
	 */
	Table,
	
	/**
	 * <p><strong>OUTPUT ONLY!</strong></p>
	 * Refers to: {@link Object}
	 */
	Object,
	
	/**
	 * <p><strong>OUTPUT ONLY!</strong></p>
	 * This says that it's a void method. (default value)
	 */
	Nil,
	
	/**
	 * <p><strong>OUTPUT ONLY!</strong></p>
	 * This refers to Object[]
	 * 
	 * The following line explains what happens when you use this output and invoke it with lua:
	 * <pre>
	 * local var1, var2 = peripheral.wrap(side).yourMethod();
	 * </pre>
	 * If you would translate the variables to Java, var1 would be Object[0], var2 would be Object[1] etc.
	 */
	Multiple;
}
