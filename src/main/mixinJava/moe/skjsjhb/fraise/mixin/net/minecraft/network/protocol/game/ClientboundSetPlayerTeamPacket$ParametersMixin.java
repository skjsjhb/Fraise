package moe.skjsjhb.fraise.mixin.net.minecraft.network.protocol.game;

import io.papermc.paper.configuration.GlobalConfiguration;
import net.minecraft.network.protocol.game.ClientboundSetPlayerTeamPacket;
import net.minecraft.world.scores.Team;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(ClientboundSetPlayerTeamPacket.Parameters.class)
public class ClientboundSetPlayerTeamPacket$ParametersMixin {
    @ModifyArg(
        method = "write",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/network/codec/StreamCodec;encode(Ljava/lang/Object;Ljava/lang/Object;)V"
        ),
        index = 1
    )
    private Object setCollisionRule(Object obj) {
        if (obj instanceof Team.CollisionRule && !GlobalConfiguration.get().collisions.enablePlayerCollisions) {
            return Team.CollisionRule.NEVER;
        }

        return obj;
    }
}
