package moe.skjsjhb.fraise.mixin.com.mojang.brigadier.suggestion;

import com.mojang.brigadier.context.StringRange;
import com.mojang.brigadier.suggestion.IntegerSuggestion;
import com.mojang.brigadier.suggestion.Suggestion;
import moe.skjsjhb.fraise.anno.MixinType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@MixinType.Fix
@Mixin(value = IntegerSuggestion.class, remap = false)
public abstract class IntegerSuggestionMixin extends Suggestion {
    public IntegerSuggestionMixin(StringRange range, String text) {
        super(range, text);
    }

    @Inject(method = "compareTo(Lcom/mojang/brigadier/suggestion/Suggestion;)I", at = @At("HEAD"), cancellable = true)
    private void denyConcreteCompare(Suggestion o, CallbackInfoReturnable<Integer> cir) {
        cir.setReturnValue(super.compareTo(o));
    }

    @Inject(method = "compareToIgnoreCase", at = @At("HEAD"), cancellable = true)
    private void denyConcreteCompareIgnoreCase(Suggestion b, CallbackInfoReturnable<Integer> cir) {
        cir.setReturnValue(super.compareToIgnoreCase(b));
    }
}
