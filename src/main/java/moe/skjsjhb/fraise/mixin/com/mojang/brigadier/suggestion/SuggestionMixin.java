package moe.skjsjhb.fraise.mixin.com.mojang.brigadier.suggestion;

import com.mojang.brigadier.suggestion.IntegerSuggestion;
import com.mojang.brigadier.suggestion.Suggestion;
import moe.skjsjhb.fraise.anno.MixinType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Comparator;

@MixinType.Fix
@Mixin(Suggestion.class)
public class SuggestionMixin {
    @Unique
    private static int compare0(final Suggestion lhs, final Suggestion rhs, final java.util.Comparator<String> textComparator) {
        if (lhs instanceof final IntegerSuggestion lis && rhs instanceof final IntegerSuggestion ris) {
            return Integer.compare(lis.getValue(), ris.getValue());
        } else if (lhs instanceof IntegerSuggestion) {
            return -1;
        } else if (rhs instanceof IntegerSuggestion) {
            return 1;
        } else {
            // XXX: Why does Paper use direct property here...?
            return textComparator.compare(lhs.getText(), rhs.getText());
        }
    }

    @Inject(method = "compareTo(Lcom/mojang/brigadier/suggestion/Suggestion;)I", at = @At("HEAD"), cancellable = true)
    private void fixCompareTo(Suggestion o, CallbackInfoReturnable<Integer> cir) {
        cir.setReturnValue(compare0((Suggestion) (Object) this, o, Comparator.naturalOrder()));
    }

    @Inject(method = "compareToIgnoreCase", at = @At("HEAD"), cancellable = true)
    private void fixCompareToIgnoreCase(Suggestion b, CallbackInfoReturnable<Integer> cir) {
        cir.setReturnValue(compare0((Suggestion) (Object) this, b, String.CASE_INSENSITIVE_ORDER));
    }
}
