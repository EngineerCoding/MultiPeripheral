package engineer.multiperipheral.inventory;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerMultiPeripheral extends Container
{
	private final IInventory tile;
	
	public ContainerMultiPeripheral(InventoryPlayer playerInv, IInventory tile)
	{
		this.tile = tile;
		
		// Add standard inventory
		for(int i = 0; i < 6; i++)
			this.addSlotToContainer(new Slot(tile, i, 8, 24 + i * 19));
		
		// Add the playerInventory
		for(int i = 0; i < 3; i++)
		{
			for(int j = 0; j < 9; j++)
				this.addSlotToContainer(new Slot(playerInv, j + i * 9 + 9, 8 + j * 18, 158 + i * 18));
		}
		
		for(int i = 0; i < 9; i++)
			this.addSlotToContainer(new Slot(playerInv, i, 8 + i * 18, 216));
	}
	
	@Override
	public ItemStack transferStackInSlot(EntityPlayer player, int slot)
	{
		ItemStack stack = null;
        Slot slotObject = (Slot) inventorySlots.get(slot);

        if (slotObject != null && slotObject.getHasStack()) 
        {
        	ItemStack stackInSlot = slotObject.getStack();
        	stack = stackInSlot.copy();

        	//merges the item into player inventory since its in the tileEntity
        	if (slot < 6) 
        	{
        		if (!this.mergeItemStack(stackInSlot, 0, 35, true))
        			return null;
        	}
        	
        	//places it into the tileEntity is possible since its in the player inventory
        	else if(!this.mergeItemStack(stackInSlot, 0, 6, false))
        		return null;
        	
        	if (stackInSlot.stackSize == 0)
        		slotObject.putStack(null);
        	else
        		slotObject.onSlotChanged();

        	if(stackInSlot.stackSize == stack.stackSize)
        		return null;
       
        	slotObject.onPickupFromSlot(player, stackInSlot);
        }
        return stack;
	}
	
	@Override
	public boolean canInteractWith(EntityPlayer player)
	{
		return this.tile.isUseableByPlayer(player);
	}
}
