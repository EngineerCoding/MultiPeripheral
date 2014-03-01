package engineer.multiperipheral.api;


/**
 * Implement this interface directly on your TileEntity if you want to reject {@link IHostedPeripheral}'s on your TileEntity to be mount.
 * @see IHostedPeripheral
 * @author Wesley 'Engineer' Ameling
 */
public interface IRejectPeripherals 
{
	/**
	 * @param peripheral
	 * @return boolean wether to include this peripheral or not
	 */
	public boolean allowPeripheralMount(IHostedPeripheral peripheral);
}
