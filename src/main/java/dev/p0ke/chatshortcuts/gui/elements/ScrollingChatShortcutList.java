package dev.p0ke.chatshortcuts.gui.elements;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.renderer.Tessellator;
import net.minecraftforge.fml.client.GuiScrollingList;

public class ScrollingChatShortcutList extends GuiScrollingList {
	
	private Minecraft mc;
	private ArrayList<ShortcutListEntry> shortcuts;
	
	public ScrollingChatShortcutList(Minecraft mc, int width, int height, int top, int bottom, int left, int entryHeight,
			int screenWidth, int screenHeight) {
		super(mc, width, height, top, bottom, left, entryHeight, screenWidth, screenHeight);
		this.shortcuts = new ArrayList<ShortcutListEntry>();
		this.mc = mc;
	}
	
	public void addShortcut(int id, GuiTextField key, GuiTextField message, GuiButton removeButton) {
		shortcuts.add(new ShortcutListEntry(id, key, message, removeButton));
	}
	
	public void removeShortcut(int id) {
		for(ShortcutListEntry e : shortcuts) {
			if(e.id == id) {
				shortcuts.remove(e);
				return;
			}
		}
	}
	
	public List<ShortcutListEntry> getShortcuts(){
		return shortcuts;
	}
	
	public void keyTyped(char typedChar, int keyCode) {
		for(ShortcutListEntry e : shortcuts) {
			e.key.textboxKeyTyped(typedChar, keyCode);
			e.message.textboxKeyTyped(typedChar, keyCode);
		}
	}
	
	public void updateScreen() {
		for(ShortcutListEntry e : shortcuts) {
			e.key.updateCursorCounter();
			e.message.updateCursorCounter();
		}
	}
	
	public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
		for(ShortcutListEntry e : shortcuts) {
			e.key.mouseClicked(mouseX, mouseY, mouseButton);
			e.message.mouseClicked(mouseX, mouseY, mouseButton);
		}
	}
	
	private void resetButtons() {
		for(ShortcutListEntry e : shortcuts) {
			e.removeButton.visible = false;
		}
	}

	@Override
	protected int getSize() {
		return shortcuts.size();
	}

	@Override
	protected void elementClicked(int index, boolean doubleClick) { }

	@Override
	protected boolean isSelected(int index) {
		return false;
	}

	@Override
	protected void drawBackground() { }
	
	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		resetButtons();
		super.drawScreen(mouseX, mouseY, partialTicks);
	}

	@Override
	protected void drawSlot(int slotIdx, int entryRight, int slotTop, int slotBuffer, Tessellator tess) {
		ShortcutListEntry shortcut = shortcuts.get(slotIdx);
		boolean visible = slotTop > top && slotTop + slotHeight < bottom;
		shortcut.removeButton.visible = visible;
		
		if(visible) {
			shortcut.key.yPosition = slotTop;
			shortcut.message.yPosition = slotTop;
			shortcut.removeButton.yPosition = slotTop;
			
			shortcut.key.drawTextBox();
			shortcut.message.drawTextBox();
			
			mc.fontRendererObj.drawString(":", shortcut.key.xPosition + shortcut.key.width + 10, slotTop + 5, 0xFFFFFF);
		}
		
	}
	
	@Override
	protected void drawGradientRect(int left, int top, int right, int bottom, int color1, int color2) { }
	
	public class ShortcutListEntry {
		
		private GuiTextField key;
		private GuiTextField message;
		private GuiButton removeButton;
		private int id;
		
		public ShortcutListEntry(int id, GuiTextField key, GuiTextField message, GuiButton removeButton) {
			this.id = id;
			this.key = key;
			this.message = message;
			this.removeButton = removeButton;
		}
		
		public String getKey() {
			return key.getText();
		}
		
		public String getMessage() {
			return message.getText();
		}
	}

}
