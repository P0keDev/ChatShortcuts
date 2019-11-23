package dev.p0ke.chatshortcuts;

import dev.p0ke.chatshortcuts.command.ChatShortcutsCommand;
import dev.p0ke.chatshortcuts.util.ShortcutHandler;
import net.minecraftforge.client.ClientCommandHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = ChatShortcutsMod.MODID, version = ChatShortcutsMod.VERSION, name = ChatShortcutsMod.MOD_NAME, clientSideOnly = true)
public class ChatShortcutsMod {
	
    public static final String MODID = "chatshortcuts";
    public static final String MOD_NAME = "ChatShortcuts";
    public static final String VERSION = "1.0";
    
    private ShortcutHandler shortcutHandler;
    
    public static ChatShortcutsMod instance;
    
    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
    	shortcutHandler = new ShortcutHandler(event.getSuggestedConfigurationFile());
    	shortcutHandler.loadShortcuts();
    }
    
    @EventHandler
    public void init(FMLInitializationEvent event) {
		instance = this;
    }
    
    @EventHandler
    public void postInit(FMLPostInitializationEvent event) {
    	ClientCommandHandler.instance.registerCommand(new ChatShortcutsCommand());
    }
    
    public ShortcutHandler getShortcutHandler() {
    	return shortcutHandler;
    }
    
}
