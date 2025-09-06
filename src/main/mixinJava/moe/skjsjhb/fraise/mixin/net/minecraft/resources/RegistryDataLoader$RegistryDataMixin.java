package moe.skjsjhb.fraise.mixin.net.minecraft.resources;

import com.llamalad7.mixinextras.expression.Definition;
import com.llamalad7.mixinextras.expression.Expression;
import com.llamalad7.mixinextras.sugar.Local;
import com.mojang.serialization.Lifecycle;
import io.papermc.paper.registry.PaperRegistryAccess;
import net.minecraft.core.WritableRegistry;
import net.minecraft.resources.RegistryDataLoader;
import net.minecraft.resources.ResourceKey;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Map;

@Mixin(RegistryDataLoader.RegistryData.class)
public class RegistryDataLoader$RegistryDataMixin {
    @SuppressWarnings("LocalMayBeArgsOnly") // False positive
    @Definition(id = "writableRegistry", local = @Local(type = WritableRegistry.class))
    @Expression("writableRegistry = ?")
    @Inject(method = "create", at = @At(value = "MIXINEXTRAS:EXPRESSION", shift = At.Shift.AFTER))
    private void onCreate(
        Lifecycle registryLifecycle,
        Map<ResourceKey<?>, Exception> loadingErrors,
        CallbackInfoReturnable<RegistryDataLoader.Loader<?>> cir,
        @Local WritableRegistry<?> writableRegistry
    ) {
        PaperRegistryAccess.instance().registerRegistry(writableRegistry);
    }
}
