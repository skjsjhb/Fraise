package moe.skjsjhb.fraise.mixin.net.minecraft.server.commands;

import com.llamalad7.mixinextras.expression.Definition;
import com.llamalad7.mixinextras.expression.Expression;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import io.papermc.paper.configuration.GlobalConfiguration;
import net.minecraft.server.commands.RideCommand;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(RideCommand.class)
public class RideCommandMixin {
    @Definition(id = "PLAYER", field = "Lnet/minecraft/world/entity/EntityType;PLAYER:Lnet/minecraft/world/entity/EntityType;")
    @Definition(id = "getType", method = "Lnet/minecraft/world/entity/Entity;getType()Lnet/minecraft/world/entity/EntityType;")
    @Expression("?.getType() == PLAYER")
    @ModifyExpressionValue(method = "mount", at = @At("MIXINEXTRAS:EXPRESSION"))
    private static boolean allowPlayerVehicle(boolean original) {
        return original && !GlobalConfiguration.get().commands.rideCommandAllowPlayerAsVehicle;
    }
}
