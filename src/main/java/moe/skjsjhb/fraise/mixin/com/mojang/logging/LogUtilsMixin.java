package moe.skjsjhb.fraise.mixin.com.mojang.logging;

import com.mojang.logging.LogUtils;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(value = LogUtils.class, remap = false)
public interface LogUtilsMixin {
    @Accessor("STACK_WALKER")
    static StackWalker getStackWalker() {
        throw new AssertionError();
    }
}
