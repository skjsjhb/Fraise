package moe.skjsjhb.fraise.mixin.net.minecraft.network.chat;

import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.SignedMessageChain;
import net.minecraft.network.chat.SignedMessageChain$DecodeExceptionExt;
import org.bukkit.event.player.PlayerKickEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(SignedMessageChain.DecodeException.class)
public class SignedMessageChain$DecodeExceptionMixin implements SignedMessageChain$DecodeExceptionExt {
    @Unique
    public PlayerKickEvent.Cause kickCause;

    @Override
    public PlayerKickEvent.Cause kickCause() {
        return kickCause;
    }

    @Inject(method = "<init>", at = @At("CTOR_HEAD"))
    private void extraInit(Component component, CallbackInfo ci) {
        if (SignedMessageChain$DecodeExceptionExt.init$$cause.hasValue()) {
            kickCause = SignedMessageChain$DecodeExceptionExt.init$$cause.get();
        } else {
            kickCause = PlayerKickEvent.Cause.UNKNOWN;
        }
    }
}
