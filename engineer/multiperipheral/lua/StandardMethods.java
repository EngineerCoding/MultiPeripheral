package engineer.multiperipheral.lua;

import java.util.HashMap;

import net.minecraft.world.World;
import dan200.computercraft.api.lua.ILuaObject;
import dan200.computercraft.api.peripheral.IComputerAccess;
import dan200.computercraft.api.peripheral.IPeripheral;
import engineer.multiperipheral.api.IHostedPeripheral;
import engineer.multiperipheral.api.LuaArg;
import engineer.multiperipheral.api.LuaMethod;
import engineer.multiperipheral.api.LuaMethod.Check;
import engineer.multiperipheral.api.LuaMethod.Describe;
import engineer.multiperipheral.handler.api.Handler;
import engineer.multiperipheral.wrapper.MethodFinder;
import engineer.multiperipheral.wrapper.MethodWrapper;
import engineer.multiperipheral.wrapper.objects.IPeripheralObject;

public class StandardMethods implements IHostedPeripheral
{
	private final IStandardMethod handler;
	
	public StandardMethods(IStandardMethod handler)
	{
		this.handler = handler;
	}
	
	@Describe
	(
		description = "This method returns a tables with all types wrapped on this MultiPeripheral", 
		input = "No input parameters.", 
		output = "A table which keys are types of the MultiPeripheral, and the values are 'peripheral.wrap' tables of that peripheral type."
	)
	@LuaMethod(name="getTypes", input={LuaArg.ComputerAccess}, output=LuaArg.Table)
	public HashMap<String, ILuaObject> getTypes(IComputerAccess pc)
	{
		HashMap<String, ILuaObject> table = new HashMap<String, ILuaObject>();
		for(String peripheral : this.handler.getTypes())
		{
			if(!peripheral.equals(this.getType()))
				table.put(peripheral, getPeripheral(pc, peripheral));
		}
		return table;
	}
	
	@Describe
	(
		description = "This method checks if this MultiPeripheral contains a specific type of peripheral", 
		input = "The string of the peripheral type", 
		output = "Boolean: when true it is the peripheral type"
	)
	@LuaMethod(name="isPeripheral", input={LuaArg.String}, output=LuaArg.Boolean)
	public boolean isPeripheral(String peripheral)
	{
		if(peripheral != null)
			for(String type : this.handler.getTypes())
				if(type != null && type.equals(peripheral))
					return true;
		return false;
	}
	
	@Describe
	(
		description = "This method returns a 'peripheral.wrap' table like it was the specific type.",
		input = "A string naming the specific peripheral type",
		output = "A 'peripheral.wrap' table like it was the specific type, or nil when the MultiPeripheral doesn't contain tht peripheral type."
	)
	@LuaMethod(name="getPeripheral", input={LuaArg.ComputerAccess, LuaArg.String}, output=LuaArg.LuaObject)
	public ILuaObject getPeripheral(IComputerAccess pc, String peripheral)
	{
		if(this.isPeripheral(peripheral))
			return new IPeripheralObject(this.handler.getPeripheral(peripheral), pc);
		return null;
	}
	
	@Describe
	(
		description = "This method gives a table of description for a specific peripheral type. This method can only be called on peripherals which depends on the mod 'MultiPeripheral'",
		input = "The string of a specific peripheral type. It will check if that peripheral is mounted on this 'MultiPeripheral' and depends on the mod 'MultiPeripheral'.",
		output = "Nil when the peripheral type does not support this or a table with the following methods: getInput, getDescription and getOutput. All of those accept a string method, and will check if that specific method is described. It will return nil if not described, otherwise a string with the description"
	)
	@Check("checkGetDescription")
	@LuaMethod(name="getDescription", input={LuaArg.String}, output=LuaArg.LuaObject)
	public ILuaObject getDescription(String peripheral)
	{
		IPeripheral p = this.handler.getPeripheral(peripheral);
		if(p instanceof MethodFinder)
		{
			MethodFinder finder = (MethodFinder) p;
			return Handler.convertIMethodTable(new DescriptionObject(finder.methods));
		}
		return null;
	}
	
	public boolean checkGetDescription()
	{
		for(String periphs : this.handler.getTypes())
		{
			IPeripheral peripheral = this.handler.getPeripheral(periphs);
			if(peripheral instanceof MethodFinder)
			{
				MethodFinder finder = (MethodFinder) peripheral;
				for(MethodWrapper wrapper : finder.methods)
					if(wrapper.isDescribed)
						return true;
			}
		}
		return false;
	}

	@Override public boolean isValidPosition(World world, int x, int y, int z, int side) { return true; }
	@Override public String getType() { return "standard"; }
	@Override public void attach(IComputerAccess pc) {}
	@Override public void detach(IComputerAccess pc) {}
}
