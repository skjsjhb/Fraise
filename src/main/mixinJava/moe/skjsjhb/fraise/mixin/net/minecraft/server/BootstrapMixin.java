package moe.skjsjhb.fraise.mixin.net.minecraft.server;

import io.papermc.paper.plugin.entrypoint.LaunchEntryPointHandler;
import net.minecraft.server.Bootstrap;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Bootstrap.class)
public class BootstrapMixin {
    @Shadow
    private static volatile boolean isBootstrapped;

    @Inject(method = "bootStrap", at = @At("HEAD"))
    private static void onBootStrap(CallbackInfo ci) {
        if (!isBootstrapped) {
            LaunchEntryPointHandler.enterBootstrappers();
        }
    }
}
