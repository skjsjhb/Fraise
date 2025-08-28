package moe.skjsjhb.fraise.mixin.com.mojang.logging;

import com.mojang.logging.LogUtils;
import moe.skjsjhb.fraise.anno.MixinType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@MixinType.Required
@Mixin(value = LogUtils.class, remap = false)
public interface LogUtilsMixin {
    @Accessor("STACK_WALKER")
    static StackWalker getStackWalker() {
        throw new AssertionError();
    }
}
