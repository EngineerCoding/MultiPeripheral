package engineer.multiperipheral.nbt;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.DimensionManager;
import engineer.multiperipheral.MultiPeripheral;
import engineer.multiperipheral.lib.ModInfo;

public class FileManager 
{
	protected FileManager() {}
	
	private File getFile(int dimension)
	{
		File folder = new File(DimensionManager.getCurrentSaveRootDirectory().getAbsolutePath() + File.separator + ModInfo.ID);
		if(!folder.exists())
			folder.mkdir();
		File file = new File(folder.getAbsolutePath() + File.separator + String.format("MultiPeripheral_%d.dat", dimension));
		return file;
	}
	
	private NBTTagCompound getCompound(int dimension) throws IOException
	{
		File file = getFile(dimension);
		if(file.createNewFile())
		{
			NBTTagCompound tag = new NBTTagCompound("MultiPeripheral");
			CompressedStreamTools.write(tag, file);
			return tag;
		}
		else if(file.exists())
		{
			FileInputStream stream = new FileInputStream(file);
			DataInputStream dataStream = new DataInputStream(stream);
			NBTTagCompound nbt = CompressedStreamTools.read(dataStream);
			
			stream.close();
			dataStream.close();
			return nbt;
		}
		return null;
	}
	
	public NBTTagCompound getNBT(int dimensionId)
	{
		try {
			return getCompound(dimensionId);
		} catch(Exception e) {
			MultiPeripheral.logThrowable(e);
		}
		return null;
	}
	
	public boolean writeCompound(NBTTagCompound nbt, int dimensionId)
	{
		if(nbt != null)
		{
			File file = getFile(dimensionId);
			try {
				CompressedStreamTools.write(nbt, file);
				return true;
			} catch(IOException e) {
				
			}
		}
		return false;
	}
}
