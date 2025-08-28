package moe.skjsjhb.fraise.mixin.com.mojang.brigadier.builder;

import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.builder.ArgumentBuilderExt;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.function.Predicate;

@Mixin(value = ArgumentBuilder.class, remap = false)
public class ArgumentBuilderMixin {
    @Shadow
    private Predicate<?> requirement;

    @Inject(method = "<init>", at = @At("RETURN"))
    private void refRequirement(CallbackInfo ci) {
        requirement = ArgumentBuilderExt.defaultRequirement();
    }
}
