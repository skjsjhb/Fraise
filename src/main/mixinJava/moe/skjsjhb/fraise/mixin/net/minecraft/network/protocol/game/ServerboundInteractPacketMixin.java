package moe.skjsjhb.fraise.mixin.net.minecraft.network.protocol.game;

import net.minecraft.network.protocol.game.ServerboundInteractPacket;
import net.minecraft.network.protocol.game.ServerboundInteractPacketExt;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(ServerboundInteractPacket.class)
public class ServerboundInteractPacketMixin implements ServerboundInteractPacketExt {
    @Shadow
    @Final
    private int entityId;

    @Shadow
    @Final
    private ServerboundInteractPacket.Action action;

    @Override
    public int getEntityId() {
        return entityId;
    }

    @Override
    public boolean isAttack() {
        return action.getType() == ServerboundInteractPacket.ActionType.ATTACK;
    }
}
