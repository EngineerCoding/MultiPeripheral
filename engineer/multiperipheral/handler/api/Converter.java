package engineer.multiperipheral.handler.api;

import java.util.ArrayList;
import java.util.Iterator;

import net.minecraft.world.World;
import dan200.computercraft.api.lua.ILuaObject;
import dan200.computercraft.api.peripheral.IPeripheral;
import engineer.multiperipheral.api.IHostedPeripheral;
import engineer.multiperipheral.api.IMethodTable;
import engineer.multiperipheral.api.ILuaPeripheral;
import engineer.multiperipheral.api.INBTHostedPeripheral;
import engineer.multiperipheral.wrapper.MultiIPeripheral;
import engineer.multiperipheral.wrapper.objects.IFunctionTableWrapper;
import engineer.multiperipheral.wrapper.peripheral.IHostedPeripheralWrapper;
import engineer.multiperipheral.wrapper.peripheral.ILuaPeripheralWrapper;
import engineer.multiperipheral.wrapper.peripheral.INBTHostedPeripheralWrapper;

public class Converter 
{
	public static class IPeripheralList
	{
		private ArrayList<IPeripheral> list = new ArrayList<IPeripheral>();
			
		public boolean add(IPeripheral peripheral)
		{
			if(peripheral != null && !list.contains(peripheral))
				return list.add(peripheral);
			return false;
		}
		
		public boolean add(ILuaPeripheral peripheral)
		{
			if(peripheral != null)
			{
				ILuaPeripheralWrapper wrapper = new ILuaPeripheralWrapper(peripheral);
				if(wrapper.isPeripheral)
					return this.list.add(wrapper);
			}
			return false;
		}
		
		public boolean add(IHostedPeripheral peripheral, World world, int x, int y, int z, int side)
		{
			if(peripheral != null && world != null)
			{
				IHostedPeripheralWrapper wrapper = new IHostedPeripheralWrapper(peripheral, world, x, y, z, side);
				if(wrapper.isPeripheral && wrapper.isValid)
					return this.list.add(wrapper);
			}
			return false;
		}
		
		public boolean add(INBTHostedPeripheral peripheral, World world, int x, int y, int z, int side)
		{
			if(peripheral != null && world != null)
			{
				INBTHostedPeripheralWrapper wrapper = new INBTHostedPeripheralWrapper(peripheral, world, x, y, z, side);
				if(wrapper.isPeripheral && wrapper.isValid)
					return this.list.add(wrapper);
			}
			return false;
		}
		
		public Iterator<IPeripheral> iterator()
		{
			return this.list.iterator();
		}
		
		public int size()
		{
			return list.size();
		}
		
		public MultiIPeripheral getMultiIPeripheral()
		{
			if(list.size() > 0)
			{
				MultiIPeripheral peripheral = new MultiIPeripheral(list.toArray(new IPeripheral[list.size()]));
				if(peripheral.isPeripheral)
				{
					return peripheral;
				}
			}
			return null;
		}
	}
	
	protected static ILuaObject convertIMethodTable(IMethodTable table)
	{
		if(table != null)
		{
			IFunctionTableWrapper wrapper = new IFunctionTableWrapper(table);
			if(wrapper.hasMethods)
				return wrapper;
		}
		return null;
	}
}
