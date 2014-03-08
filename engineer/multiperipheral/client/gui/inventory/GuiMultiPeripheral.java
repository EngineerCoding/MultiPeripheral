package engineer.multiperipheral.client.gui.inventory;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import engineer.multiperipheral.inventory.ContainerMultiPeripheral;

public class GuiMultiPeripheral extends GuiContainer
{
	public static final ResourceLocation resource = new ResourceLocation("multiperipheral", "/textures/gui/inventory/MultiPeripheral.png");
	public static final int resourceHeight = 242;
	public static final int resourceWidth = 176;
	
	public GuiMultiPeripheral(InventoryPlayer playerInv, IInventory inv) 
	{
		super(new ContainerMultiPeripheral(playerInv, inv));
		
		this.xSize = resourceWidth;
		this.ySize = resourceHeight;
		
		this.guiLeft = (this.width - this.xSize) / 2;
		this.guiTop = (this.height - this.ySize) / 2 - 45;
	}

	@Override
	public void drawGuiContainerBackgroundLayer(float f, int i, int j) 
	{
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		this.mc.renderEngine.bindTexture(resource);
		this.drawTexturedModalRect(this.guiLeft, this.guiTop, 0, 0, resourceWidth, resourceHeight);
	}
	
	
	@Override
	@SuppressWarnings("unchecked")
	public void initGui()
	{
		super.initGui();
		for(int i = 0; i < 6; i++)
		{
			this.buttonList.add(new GuiButton(i, 34 + this.guiLeft, this.guiTop + 22 + i * 19, this.xSize - 40, 19, "Open GUI"));
			((GuiButton)(this.buttonList.get(i))).enabled = this.inventorySlots.inventoryItemStacks.get(i) != null;
		}
	}
}
