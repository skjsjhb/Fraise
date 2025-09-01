package moe.skjsjhb.fraise.mixin.net.minecraft.network.chat;

import net.minecraft.network.chat.ChatType;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.OutgoingChatMessage;
import net.minecraft.network.chat.OutgoingChatMessageExt;
import net.minecraft.server.level.ServerPlayer;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(OutgoingChatMessage.Disguised.class)
public class OutgoingChatMessage$DisguisedMixin implements OutgoingChatMessageExt {
    @Shadow
    @Final
    private Component content;

    @Override
    public void sendToPlayer(ServerPlayer sender, boolean filterMaskEnabled, ChatType.Bound boundType, Component unsigned) {
        sender.connection.sendDisguisedChatMessage(unsigned != null ? unsigned : content, boundType);
    }
}
