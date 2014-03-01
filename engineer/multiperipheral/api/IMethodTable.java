package engineer.multiperipheral.api;

/**
 * This interface let's the mod know that it takes {@literal @}{@link LuaMethod} annotations on methods.
 * It let's the mod know to check for those annotations, but you shouldn't use this to create a peripheral.
 * <p>You use {@link ILuaPeripheral} for that.</p>
 * <p>This interface is not here for nothing, you can use it. It is the equivalent of {@link ILuaObject}, only the difference is that
 * you dont have to define methods. You only define methods with the {@literal @}{@link LuaMethod} annotation.</p>
 * <p>After you have defined those methods, you can convert it to {@link ILuaObject} with {@link MultiPeripheralAPI#convertIFunctionTable(IFunctionTable)}
 * to return it in one of your other methods</p>
 * 
 * @see LuaMethod
 * @see LuaMethod.Check
 * @see LuaMethod.Describe
 * @see ILuaPeripheral
 * @see MultiPeripheralAPI
 * @author Wesley 'Engineer' Ameling
 */
public interface IMethodTable 
{
}
