package moe.skjsjhb.fraise.mixin.net.minecraft.network.chat;

import com.llamalad7.mixinextras.sugar.Local;
import io.papermc.paper.adventure.AdventureCodecs;
import io.papermc.paper.adventure.AdventureComponent;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.PlayerChatMessage;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(PlayerChatMessage.class)
public abstract class PlayerChatMessageMixin {
    @Shadow
    public abstract String signedContent();

    @ModifyVariable(method = "withUnsignedContent", at = @At(value = "STORE"), ordinal = 1)
    private Component adventureComponent(Component value, @Local(argsOnly = true) Component message) {
        if (message instanceof AdventureComponent ac) {
            return signedContent().equals(AdventureCodecs.tryCollapseToString(ac.adventure$component())) ? null : message;
        }

        return value;
    }
}
