package dev.p0ke.chatshortcuts.command;

import java.util.Arrays;
import java.util.List;

import dev.p0ke.chatshortcuts.gui.ChatShortcutsGui;
import dev.p0ke.chatshortcuts.util.DelayedTask;
import net.minecraft.client.Minecraft;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;

public class ChatShortcutsCommand extends CommandBase {

	@Override
	public boolean canCommandSenderUseCommand(ICommandSender sender) {
		return true;
	}
	
	@Override
	public String getCommandName() {
		return "chatshortcuts";
	}
	
	@Override
	public List<String> getCommandAliases(){
		return  Arrays.asList(new String[] {"shortcuts", "cs"});
	}

	@Override
	public String getCommandUsage(ICommandSender sender) {
		return "Opens the ChatShortcuts menu";
	}

	@Override
	public void processCommand(ICommandSender sender, String[] args) throws CommandException {
		new DelayedTask(() -> Minecraft.getMinecraft().displayGuiScreen(new ChatShortcutsGui()), 1);
		
	}

}
