package dev.p0ke.chatshortcuts.mixins;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import dev.p0ke.chatshortcuts.ChatShortcutsMod;
import dev.p0ke.chatshortcuts.util.ChatPacket;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C01PacketChatMessage;

@Mixin(NetHandlerPlayClient.class)
public abstract class MixinNetHandlerPlayClient {
	
	@SuppressWarnings("rawtypes")
	@Inject(method = "addToSendQueue", at = @At(value = "HEAD"))
	public void onAddToSendQueue(Packet packet, CallbackInfo ci) {
		if(packet instanceof C01PacketChatMessage) {
			ChatPacket chatPacket = (ChatPacket) packet;
			String message = ChatShortcutsMod.instance.getShortcutHandler().transformMessage(chatPacket.getMessage());
			chatPacket.setMessage(message);
		}
	}
	

}
