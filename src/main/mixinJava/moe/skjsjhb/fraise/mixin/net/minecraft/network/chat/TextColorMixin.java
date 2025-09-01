package moe.skjsjhb.fraise.mixin.net.minecraft.network.chat;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.TextColor;
import net.minecraft.network.chat.TextColorExt;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(TextColor.class)
public class TextColorMixin implements TextColorExt {
    @Unique
    public ChatFormatting format = null;

    @Override
    public @Nullable ChatFormatting format() {
        return format;
    }

    @Inject(method = "<init>(ILjava/lang/String;)V", at = @At("CTOR_HEAD"))
    private void extraInit(int value, String name, CallbackInfo ci) {
        if (TextColorExt.init$$format.hasValue()) {
            format = TextColorExt.init$$format.get();
        }
    }

    @Inject(method = "method_27722", at = @At(value = "INVOKE", target = "Lnet/minecraft/network/chat/TextColor;<init>(ILjava/lang/String;)V"))
    private static void onCreateTextColor(ChatFormatting chatFormatting, CallbackInfoReturnable<TextColor> cir) {
        // This call is missing a fallback cleanup, but luckily the CTOR has no super and does not throw :)
        TextColorExt.init$$format.set(chatFormatting);
    }
}
