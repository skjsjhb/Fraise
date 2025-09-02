package moe.skjsjhb.fraise.mixin.net.minecraft.network.protocol.game;

import net.minecraft.network.protocol.game.ClientboundSectionBlocksUpdatePacket;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.LevelChunkSection;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(ClientboundSectionBlocksUpdatePacket.class)
public class ClientboundSectionBlocksUpdatePacketMixin {
    // XXX: This might be too invasive
    @Redirect(
        method = "<init>(Lnet/minecraft/core/SectionPos;Lit/unimi/dsi/fastutil/shorts/ShortSet;Lnet/minecraft/world/level/chunk/LevelChunkSection;)V",
        at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/chunk/LevelChunkSection;getBlockState(III)Lnet/minecraft/world/level/block/state/BlockState;")
    )
    private BlockState nullableSection(LevelChunkSection instance, int x, int y, int z) {
        if (instance == null) {
            return Blocks.AIR.defaultBlockState();
        }
        return instance.getBlockState(x, y, z);
    }
}
