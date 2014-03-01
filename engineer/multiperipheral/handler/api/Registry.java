package engineer.multiperipheral.handler.api;

import java.util.ArrayList;

import net.minecraft.world.World;
import engineer.multiperipheral.api.IHostedPeripheral;
import engineer.multiperipheral.api.INBTHostedPeripheral;
import engineer.multiperipheral.handler.api.Converter.IPeripheralList;
import engineer.multiperipheral.util.ReflectionUtil;
import engineer.multiperipheral.wrapper.single.IExternalPeripheralWrapper;

public class Registry 
{
	private static ArrayList<Class<?>> registeredExternalPeripherals = new ArrayList<Class<?>>(); 
	
	protected static boolean registerIHostedPeripheral(IHostedPeripheral peripheral)
	{
		if(peripheral != null)
		{
			Class<?> clazz = peripheral.getClass();
			IHostedPeripheral testInstance = (IHostedPeripheral) ReflectionUtil.newInstance(clazz);
			if(testInstance != null && !registeredExternalPeripherals.contains(clazz))
			{
				IExternalPeripheralWrapper wrapper = new IExternalPeripheralWrapper(testInstance, null, 0, 0, 0, 0);
				if(wrapper.isValid)
				{	
					return registeredExternalPeripherals.add(clazz);
				}
			}
		}
		return false;
	}
	
	public static IPeripheralList getList(World world, int x, int y, int z, int side)
	{
		IPeripheralList list = new IPeripheralList();
		for(Class<?> clazz : registeredExternalPeripherals)
		{
			if(INBTHostedPeripheral.class.isAssignableFrom(clazz))
				list.add((INBTHostedPeripheral) ReflectionUtil.newInstance(clazz), world, x, y, z, side);
			else
				list.add((IHostedPeripheral) ReflectionUtil.newInstance(clazz), world, x, y, z, side);
		}
		return list;
	}
}
