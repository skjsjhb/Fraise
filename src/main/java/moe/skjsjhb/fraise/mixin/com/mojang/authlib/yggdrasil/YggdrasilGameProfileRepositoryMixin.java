package moe.skjsjhb.fraise.mixin.com.mojang.authlib.yggdrasil;

import com.mojang.authlib.yggdrasil.YggdrasilGameProfileRepository;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(value = YggdrasilGameProfileRepository.class, remap = false)
public abstract class YggdrasilGameProfileRepositoryMixin {
    @Shadow
    @Final
    private static int DELAY_BETWEEN_PAGES;

    @Unique
    public boolean hasRequested = false;

    @Redirect(method = "findProfilesByNames", at = @At(value = "INVOKE", target = "Ljava/lang/Thread;sleep(J)V"))
    private void maybeSkipSleep(long millis) throws InterruptedException {
        if (millis != DELAY_BETWEEN_PAGES) { // Only targeting the correct call
            Thread.sleep(millis);
            return;
        }

        if (!hasRequested) {
            hasRequested = true;
            return;
        }

        Thread.sleep(millis);
    }
}
