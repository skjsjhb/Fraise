package moe.skjsjhb.fraise.mixin.net.minecraft.network.protocol.game;

import net.minecraft.network.Connection;
import net.minecraft.network.protocol.PacketExt;
import net.minecraft.network.protocol.game.ClientboundContainerSetContentPacket;
import net.minecraft.network.protocol.game.ClientboundContainerSetSlotPacket;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import java.util.List;

@Mixin(ClientboundContainerSetContentPacket.class)
public class ClientboundContainerSetContentPacketMixin implements PacketExt {
    @Shadow
    @Final
    private List<ItemStack> items;

    @Shadow
    @Final
    private int containerId;

    @Shadow
    @Final
    private int stateId;

    @Override
    public boolean hasLargePacketFallback() {
        return true;
    }

    @Override
    public boolean packetTooLarge(@NotNull Connection manager) {
        for (int i = 0; i < items.size(); i++) {
            manager.send(new ClientboundContainerSetSlotPacket(containerId, stateId, i, items.get(i)));
        }
        return true;
    }
}
