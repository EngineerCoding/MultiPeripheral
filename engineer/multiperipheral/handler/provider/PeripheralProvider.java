package engineer.multiperipheral.handler.provider;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Iterator;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import dan200.computercraft.api.peripheral.IPeripheral;
import dan200.computercraft.api.peripheral.IPeripheralProvider;
import engineer.multiperipheral.api.ILuaPeripheral;
import engineer.multiperipheral.api.IRejectPeripherals;
import engineer.multiperipheral.handler.api.Converter.IPeripheralList;
import engineer.multiperipheral.handler.api.Registry;
import engineer.multiperipheral.nbt.NBTManager;
import engineer.multiperipheral.util.ReflectionUtil;
import engineer.multiperipheral.wrapper.MultiIPeripheral;
import engineer.multiperipheral.wrapper.single.IHostedPeripheralWrapper;
import engineer.multiperipheral.wrapper.single.ILuaPeripheralWrapper;

public class PeripheralProvider implements IPeripheralProvider
{	
	private static boolean replacedCCfield = false;
	private static ArrayList<IPeripheralProvider> PROVIDER;
	
	private final NBTManager nbtManager;
	
	public PeripheralProvider()
	{
		this.nbtManager = new NBTManager();
	}
	
	@Override
	public IPeripheral getPeripheral(World world, int x, int y, int z, int side) 
	{
		if(world != null)
		{
			IPeripheralList list = Registry.getList(world, x, y, z, side);
			
			TileEntity tile = world.getBlockTileEntity(x, y, z);
			if(tile instanceof ILuaPeripheral)
			{
				ILuaPeripheralWrapper wrapper = new ILuaPeripheralWrapper((ILuaPeripheral) tile);
				if(wrapper.isPeripheral)
					list.add(wrapper);
			}
			else if(tile instanceof IPeripheral)
			{
				list.add((IPeripheral) tile);
			}
			
			if(tile instanceof IRejectPeripherals)
			{
				IRejectPeripherals rejector = (IRejectPeripherals) tile;
				Iterator<IPeripheral> it = list.iterator();
				while(it.hasNext())
				{
					IPeripheral peripheral = it.next();
					if(peripheral instanceof IHostedPeripheralWrapper)
					{
						IHostedPeripheralWrapper externalPeripheral = (IHostedPeripheralWrapper) peripheral;
						try  {
							if(!rejector.allowPeripheralMount(externalPeripheral.instance))
								it.remove();
						} catch(Exception e) {
							
						}
					}
				}
			}
			
			MultiIPeripheral multiPeripheral = list.getMultiIPeripheral();
			if(multiPeripheral != null)
			{
				this.nbtManager.addMultiIPeripheral(multiPeripheral, world, x, y, z);
				return multiPeripheral;
			}
		}
		return null;
	}
	
	public static void init(IPeripheralProvider _provider)
	{
		if(!replacedCCfield)
		{
			Class<?> clazz = ReflectionUtil.getClassForName("dan200.computercraft.ComputerCraft");
			if(clazz != null)
			{
				Field providerField = ReflectionUtil.getDeclaredField(clazz, "peripheralProviders");
				
				@SuppressWarnings("unchecked")
				ArrayList<IPeripheralProvider> CCprovider = (ArrayList<IPeripheralProvider>) ReflectionUtil.getFieldValue(providerField, null);
				if(CCprovider != null)
				{
					ListImitator<IPeripheralProvider> provider = new ListImitator<IPeripheralProvider>(_provider, CCprovider);
					PROVIDER = provider.peripheralProvider;
					
					if(PROVIDER != null && ReflectionUtil.setFieldValue(providerField, null, provider))
					{
						replacedCCfield = true;
					}
				}
			}
		}
	}
}
