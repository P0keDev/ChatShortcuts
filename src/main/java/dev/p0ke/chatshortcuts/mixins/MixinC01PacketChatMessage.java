package dev.p0ke.chatshortcuts.mixins;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import dev.p0ke.chatshortcuts.util.ChatPacket;
import net.minecraft.network.play.client.C01PacketChatMessage;

@Mixin(C01PacketChatMessage.class)
public abstract class MixinC01PacketChatMessage implements ChatPacket {
	
	@Shadow
	private String message;
	
	@Override
	public void setMessage(String msg) {
		this.message = msg;
	}
	
	@Override
	public String getMessage() {
		return this.message;
	}

}
