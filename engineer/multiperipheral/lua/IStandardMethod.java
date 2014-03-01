package engineer.multiperipheral.lua;

import dan200.computercraft.api.peripheral.IPeripheral;

public interface IStandardMethod 
{
	public String[] getTypes();
	
	public IPeripheral getPeripheral(String peripheral);
}
