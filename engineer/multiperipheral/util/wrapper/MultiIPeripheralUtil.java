package engineer.multiperipheral.util.wrapper;

import dan200.computercraft.api.peripheral.IComputerAccess;
import dan200.computercraft.api.peripheral.IPeripheral;
import engineer.multiperipheral.MultiPeripheral;

public class MultiIPeripheralUtil 
{
	public static String getType(IPeripheral peripheral)
	{
		try 
		{
			if(peripheral != null)
				return peripheral.getType();
		} 
		catch(Exception e) 
		{
			MultiPeripheral.Log.severe("Couldn't call IPeripheral.getType on the following class: " + peripheral.getClass().getName());
			MultiPeripheral.logThrowable(e);
		}
		return null;
	}
	
	public static String[] getMethodNames(IPeripheral peripheral)
	{
		try 
		{
			if(peripheral != null)
				return peripheral.getMethodNames();
		}
		catch(Exception e) 
		{
			MultiPeripheral.Log.severe("Couldn't call IPeripheral.getMethodNames on the following class: " + peripheral.getClass().getName());
			MultiPeripheral.logThrowable(e);
		}
		return null;
	}
	
	public static void attach(IPeripheral peripheral, IComputerAccess pc)
	{
		try 
		{
			if(peripheral != null && pc != null)
				peripheral.attach(pc);
		}
		catch(Exception e) 
		{
			MultiPeripheral.Log.severe("Couldn't call IPeripheral.attach on the following class: " + peripheral.getClass().getName());
			MultiPeripheral.logThrowable(e);
		}
	}
	
	public static void detach(IPeripheral peripheral, IComputerAccess pc)
	{
		try 
		{
			if(peripheral != null && pc != null)
				peripheral.attach(pc);
		}
		catch(Exception e) 
		{
			MultiPeripheral.Log.severe("Couldn't call IPeripheral.detach on the following class: " + peripheral.getClass().getName());
			MultiPeripheral.logThrowable(e);
		}
	}
}
