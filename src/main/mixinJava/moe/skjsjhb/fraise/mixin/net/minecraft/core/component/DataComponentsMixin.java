package moe.skjsjhb.fraise.mixin.net.minecraft.core.component;

import com.llamalad7.mixinextras.sugar.Local;
import io.papermc.paper.util.sanitizer.OversizedItemComponentSanitizer;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

import java.util.function.UnaryOperator;

@Mixin(DataComponents.class)
public class DataComponentsMixin {
    @SuppressWarnings("unchecked")
    @ModifyVariable(method = "register", at = @At("HEAD"), argsOnly = true)
    private static <T> UnaryOperator<DataComponentType.Builder<T>> maybeWrapCodec(
        UnaryOperator<DataComponentType.Builder<T>> op,
        @Local(argsOnly = true) String name
    ) {
        return switch (name) {
            case "charged_projectiles" -> b ->
                op.apply(b).networkSynchronized(
                    (StreamCodec<? super RegistryFriendlyByteBuf, T>) OversizedItemComponentSanitizer.CHARGED_PROJECTILES
                );
            case "bundle_contents" -> b ->
                op.apply(b).networkSynchronized(
                    (StreamCodec<? super RegistryFriendlyByteBuf, T>) OversizedItemComponentSanitizer.BUNDLE_CONTENTS
                );
            case "container" -> b ->
                op.apply(b).networkSynchronized(
                    (StreamCodec<? super RegistryFriendlyByteBuf, T>) OversizedItemComponentSanitizer.CONTAINER
                );
            default -> op;
        };
    }
}
