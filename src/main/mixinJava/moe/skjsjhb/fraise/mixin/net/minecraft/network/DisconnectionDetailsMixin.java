package moe.skjsjhb.fraise.mixin.net.minecraft.network;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import io.papermc.paper.connection.DisconnectionReason;
import net.minecraft.network.DisconnectionDetails;
import net.minecraft.network.DisconnectionDetailsExt;
import net.minecraft.network.chat.Component;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Objects;
import java.util.Optional;

@Mixin(DisconnectionDetails.class)
public class DisconnectionDetailsMixin implements DisconnectionDetailsExt {
    @Unique
    public Optional<Component> quitMessage = Optional.empty();

    @Unique
    public Optional<DisconnectionReason> disconnectionReason = Optional.empty();

    @Override
    public @NotNull Optional<Component> quitMessage() {
        return quitMessage;
    }

    @Override
    public @NotNull Optional<DisconnectionReason> disconnectionReason() {
        return disconnectionReason;
    }

    @Inject(
        method = "<init>(Lnet/minecraft/network/chat/Component;Ljava/util/Optional;Ljava/util/Optional;)V",
        at = @At("CTOR_HEAD")
    )
    private void extraInit(Component component, Optional<?> optional, Optional<?> optional2, CallbackInfo ci) {
        if (DisconnectionDetailsExt.init$$quitMessage.hasValue()) {
            quitMessage = DisconnectionDetailsExt.init$$quitMessage.get();
        }

        if (DisconnectionDetailsExt.init$$disconnectionReason.hasValue()) {
            disconnectionReason = DisconnectionDetailsExt.init$$disconnectionReason.get();
        }
    }

    @WrapMethod(method = "equals")
    private boolean extraEquals(Object object, Operation<Boolean> original) {
        if (!original.call(object)) return false;

        DisconnectionDetailsExt that = (DisconnectionDetailsExt) object;
        return Objects.equals(quitMessage, that.quitMessage()) &&
            Objects.equals(disconnectionReason, that.disconnectionReason());
    }

    @WrapMethod(method = "hashCode")
    private int extraHashCode(Operation<Integer> original) {
        int extra = Objects.hash(quitMessage, disconnectionReason);
        return original.call() * 31 + extra;
    }
}
