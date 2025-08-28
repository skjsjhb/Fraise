package moe.skjsjhb.fraise.mixin.net.minecraft.advancements.critereon;

import com.llamalad7.mixinextras.expression.Definition;
import com.llamalad7.mixinextras.expression.Expression;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Local;
import io.papermc.paper.configuration.GlobalConfiguration;
import net.minecraft.advancements.critereon.LocationPredicate;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import org.bukkit.craftbukkit.util.CraftDimensionUtil;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(LocationPredicate.class)
public abstract class LocationPredicateMixin {
    @Definition(id = "level", local = @Local(type = ServerLevel.class, argsOnly = true))
    @Definition(id = "dimension", method = "Lnet/minecraft/server/level/ServerLevel;dimension()Lnet/minecraft/resources/ResourceKey;")
    @Expression("level.dimension()")
    @ModifyExpressionValue(method = "matches", at = @At("MIXINEXTRAS:EXPRESSION"))
    private ResourceKey<Level> applyConfig(ResourceKey<Level> original, @Local(argsOnly = true) ServerLevel level) {
        if (GlobalConfiguration.get().misc.strictAdvancementDimensionCheck) {
            return original;
        } else {
            return CraftDimensionUtil.getMainDimensionKey(level);
        }
    }
}
