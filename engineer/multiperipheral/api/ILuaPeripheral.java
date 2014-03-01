package engineer.multiperipheral.api;

import dan200.computercraft.api.peripheral.IComputerAccess;
import dan200.computercraft.api.peripheral.IPeripheral;

/**
 * Implement this interface on your TileEntity to imitate {@link IPeripheral}. This really does the same as {@link IPeripheral}
 * only it is simpler to implement methods to the peripheral. You never have to use that stupid switch(method) in {@link IPeripheral#callMethod(IComputerAccess, ILuaContext, int, Object[])} again!
 * <p><strong>Implement LuaMethods like you do with {@link IMethodTable}!</strong></p>
 * @see IMethodTable
 * @see MultiPeripheralAPI
 * @author Wesley 'Engineer' Ameling
 */
public interface ILuaPeripheral extends IMethodTable
{	
	/**
	 * {@link IPeripheral#attach(IComputerAccess)}
	 */
    public void attach(IComputerAccess pc);
    
    /**
     * {@link IPeripheral#detach(IComputerAccess)}
     */
    public void detach(IComputerAccess pc);
	
	/**
	 * {@link IPeripheral#getType()}
	 */
    public String getType();
}
