package engineer.multiperipheral.api;

import net.minecraft.world.World;

/**
 * Implement this interface to a random class (<strong>NOT A TILEENTITY!</strong>). You should use this class when you cannot
 * reach the TileEntity of a block. And don't be concerned if other mods add methods to the TileEntity in question, because this mod
 * is name MultiPeripheral for a reason!
 * <p>Implement methods which are accessible from the lua-side just like you should in {@link IMethodTable}
 * <p>Register this class to {@link MultiPeripheralAPI#registerIExternalPeripheral(IExternalPeripheral)} to make it actually work.</p>
 * 
 * @see IMethodTable
 * @see MultiPeripheralAPI
 * @author Wesley 'Engineer' Ameling
 */
public interface IHostedPeripheral extends ILuaPeripheral
{
	/**
	 * This method determines whether the peripheral is valid for that spot. If this method returns true, then the method is valid. Otherwise it isn't valid.
	 * <strong>Do a check if it's the right TileEntity, if I see your mod that doesn't use this method correctly, I might add a filter to this mod!</strong>
	 * 
	 * Also use this method to save the coordinates and world.
	 * @return bool Wether to have your peripheral included in that spot or not
	 */
	public boolean isValidPosition(World world, int x, int y, int z, int side);
}
