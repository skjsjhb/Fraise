package moe.skjsjhb.fraise.mixin.net.minecraft.server.commands;

import com.llamalad7.mixinextras.expression.Definition;
import com.llamalad7.mixinextras.expression.Expression;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.minecraft.server.commands.SetWorldSpawnCommand;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(SetWorldSpawnCommand.class)
public class SetWorldSpawnCommandMixin {
    @Definition(id = "OVERWORLD", field = "Lnet/minecraft/world/level/Level;OVERWORLD:Lnet/minecraft/resources/ResourceKey;")
    @Expression("? != OVERWORLD")
    @ModifyExpressionValue(method = "setSpawn", at = @At("MIXINEXTRAS:EXPRESSION"))
    private static boolean alwaysAllow(boolean original) {
        return false;
    }
}
