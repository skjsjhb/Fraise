package moe.skjsjhb.fraise.mixin.net.minecraft.network.chat;

import net.minecraft.network.chat.*;
import net.minecraft.server.level.ServerPlayer;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(OutgoingChatMessage.Player.class)
public class OutgoingChatMessage$PlayerMixin implements OutgoingChatMessageExt {
    @Shadow
    @Final
    private PlayerChatMessage message;

    @Override
    public void sendToPlayer(ServerPlayer sender, boolean filterMaskEnabled, ChatType.Bound boundType, Component unsigned) {
        // This is a full body clone, but that's fine, since we're inside a record, which is final
        PlayerChatMessage playerChatMessage = message.filter(filterMaskEnabled);
        playerChatMessage = unsigned != null ? playerChatMessage.withUnsignedContent(unsigned) : playerChatMessage;

        if (!playerChatMessage.isFullyFiltered()) {
            sender.connection.sendPlayerChatMessage(playerChatMessage, boundType);
        }
    }
}
