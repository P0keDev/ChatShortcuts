package dev.p0ke.chatshortcuts.gui;

import java.io.IOException;
import java.util.Map.Entry;

import dev.p0ke.chatshortcuts.ChatShortcutsMod;
import dev.p0ke.chatshortcuts.gui.elements.CleanButton;
import dev.p0ke.chatshortcuts.gui.elements.ScrollingChatShortcutList;
import dev.p0ke.chatshortcuts.gui.elements.ScrollingChatShortcutList.ShortcutListEntry;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;

public class ChatShortcutsGui extends GuiScreen {
	
	ScrollingChatShortcutList scrollingChatShortcutList;
	private int id;
	
	@Override
	public void initGui() {
		id = 0;
		scrollingChatShortcutList = new ScrollingChatShortcutList(mc, width, height - 80, 20, height - 60, 0, 25, width, height);
		
		buttonList.clear();
		
		for(Entry<String, String> e : ChatShortcutsMod.instance.getShortcutHandler().getShortcuts().entrySet()) {
			addShortcut(e.getKey(), e.getValue());
		}
		
		buttonList.add(new CleanButton(9000, width/2 - 220, height - 40, "Save & Exit"));
		buttonList.add(new CleanButton(9001, width/2 + 20, height - 40, "Add Shortcut"));
	}
	
	@Override
	protected void actionPerformed(GuiButton button) {
		if(button.id < 1000) {
			scrollingChatShortcutList.removeShortcut(button.id);
			buttonList.remove(button);
		}
		
		if(button.id == 9000) {
			mc.thePlayer.closeScreen();
		}
		if(button.id == 9001) {
			addShortcut();
		}
	}
	
	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		this.drawDefaultBackground();
		scrollingChatShortcutList.drawScreen(mouseX, mouseY, partialTicks);
		super.drawScreen(mouseX, mouseY, partialTicks);
	}
	
	@Override
	protected void keyTyped(char typedChar, int keyCode) throws IOException {
		super.keyTyped(typedChar, keyCode);
		scrollingChatShortcutList.keyTyped(typedChar, keyCode);
	}
	
	@Override
	public void updateScreen() {
		super.updateScreen();
		scrollingChatShortcutList.updateScreen();
	}
	
	@Override
	protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
		super.mouseClicked(mouseX, mouseY, mouseButton);
		scrollingChatShortcutList.mouseClicked(mouseX, mouseY, mouseButton);
	}
	
	@Override
	public boolean doesGuiPauseGame() {
		return false;
	}
	
	@Override
	public void onGuiClosed() {
		ChatShortcutsMod.instance.getShortcutHandler().clearShortcuts();
		for(ShortcutListEntry e : scrollingChatShortcutList.getShortcuts()) {
			if(!e.getKey().isEmpty())
				ChatShortcutsMod.instance.getShortcutHandler().addShortcut(e.getKey(), e.getMessage());
		}
	}
	
	private void addShortcut() {
		addShortcut("", "");
	}
	
	private void addShortcut(String key, String message) {
		GuiTextField keyField = new GuiTextField(1000 + id, fontRendererObj, width/2 - 220, 0, 100, 20);
		keyField.setText(key);
		GuiTextField messageField = new GuiTextField(2000 + id, fontRendererObj, width/2 - 100, 0, 270, 20);
		messageField.setMaxStringLength(255);
		messageField.setText(message);
		GuiButton removeButton = new CleanButton(id, width/2 + 175, 0, 50, 20, "Remove");
		
		buttonList.add(removeButton);
		scrollingChatShortcutList.addShortcut(id, keyField, messageField, removeButton);
		
		id++;
	}

}
