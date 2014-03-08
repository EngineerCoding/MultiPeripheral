package engineer.multiperipheral.block.tileentity;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;

public class TileMultiPeripheralBlock extends TileEntity implements IInventory
{
	private final ItemStack[] inventory = new ItemStack[6];
	
	@Override
	public String getInvName() 
	{
		return "inventory.multiperipheral";
	}
	
	@Override
	public int getSizeInventory() 
	{
		return this.inventory.length;
	}
	
	@Override
	public int getInventoryStackLimit() 
	{
		return 1;
	}
	
	@Override
	public boolean isUseableByPlayer(EntityPlayer player)
	{
		return worldObj.getBlockTileEntity(this.xCoord, this.yCoord, this.zCoord) == this &&
                player.getDistanceSq(this.xCoord + 0.5, this.yCoord + 0.5, this.zCoord + 0.5) < 64;
	}
	
	@Override
	public ItemStack decrStackSize(int slot, int amount) 
	{
		ItemStack stack = this.getStackInSlot(slot);
		if(stack != null)
		{
			if(stack.stackSize >= amount)
				stack.stackSize -= amount;
			
			if(stack.stackSize == 0)
				this.setInventorySlotContents(slot, null);
		}
		return (stack.stackSize == 0 ? null : stack);
	}

	@Override
	public ItemStack getStackInSlot(int slot) 
	{
		return this.inventory[slot];
	}

	@Override
	public ItemStack getStackInSlotOnClosing(int slot) 
	{
		ItemStack stack = this.getStackInSlot(slot);
		if(stack != null && stack.stackSize > 0)
			this.setInventorySlotContents(slot, stack);
		return stack;
	}

	@Override
	public boolean isInvNameLocalized()
	{
		return false;
	}

	@Override
	public boolean isItemValidForSlot(int slot, ItemStack stack) 
	{
		return (stack.getItem() instanceof ItemBlock);
	}

	@Override
	public void setInventorySlotContents(int slot, ItemStack stack)
	{
		if(stack != null && stack.stackSize > 0)
		{
			this.inventory[slot] = stack;
			stack.stackSize = this.getInventoryStackLimit();
		}
		else
		{
			this.inventory[slot] = null;
		}
	}
	
	  @Override
      public void readFromNBT(NBTTagCompound nbt) 
	  {
		  super.readFromNBT(nbt);
              
		  NBTTagList tagList = nbt.getTagList("Inventory");
		  for (int i = 0; i < tagList.tagCount(); i++)
		  {
			  NBTTagCompound tag = (NBTTagCompound) tagList.tagAt(i);
			  byte slot = tag.getByte("Slot");
			  if (slot >= 0 && slot < this.inventory.length)
				  this.inventory[slot] = ItemStack.loadItemStackFromNBT(tag);
		  }
      }

      @Override
      public void writeToNBT(NBTTagCompound nbt) 
      {
    	  super.writeToNBT(nbt);
                              
    	  NBTTagList itemList = new NBTTagList();
    	  for (int i = 0; i < this.inventory.length; i++)
    	  {
    		  ItemStack stack = this.inventory[i];
    		  if (stack != null) 
    		  {
    			  NBTTagCompound tag = new NBTTagCompound();
    			  tag.setByte("Slot", (byte) i);
    			  stack.writeToNBT(tag);
    			  itemList.appendTag(tag);
    		  }
    	  }
    	  nbt.setTag("Inventory", itemList);
      }
	
	@Override public void closeChest() {}
	@Override public void openChest() {}
}
