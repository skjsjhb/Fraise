package moe.skjsjhb.fraise.mixin.net.minecraft.util;

import net.minecraft.util.TickThrottler;
import net.minecraft.util.TickThrottlerExt;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.concurrent.atomic.AtomicInteger;

@Mixin(TickThrottler.class)
public class TickThrottlerMixin implements TickThrottlerExt {
    @Shadow
    @Final
    private int incrementStep;
    @Shadow
    @Final
    private int threshold;
    @Unique
    private final AtomicInteger atomicCount = new AtomicInteger();

    @Inject(method = "increment", at = @At("HEAD"))
    private void onIncrement(CallbackInfo ci) {
        atomicCount.addAndGet(incrementStep);
    }

    @Inject(method = "tick", at = @At("HEAD"))
    private void onTick(CallbackInfo ci) {
        int val;
        do {
            val = atomicCount.get();
        } while (val > 0 && !atomicCount.compareAndSet(val, val - 1));
    }

    @Inject(method = "isUnderThreshold", at = @At("RETURN"), cancellable = true)
    private void isUnderThreshold(CallbackInfoReturnable<Boolean> cir) {
        cir.setReturnValue(atomicCount.get() < threshold);
    }

    @Override
    public boolean isIncrementAndUnderThreshold() {
        return isIncrementAndUnderThreshold(incrementStep, threshold);
    }

    @Override
    public boolean isIncrementAndUnderThreshold(int step, int th) {
        return atomicCount.addAndGet(step) < th;
    }
}
