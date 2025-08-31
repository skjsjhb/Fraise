package moe.skjsjhb.fraise.mixin.net.minecraft.network.chat;

import io.papermc.paper.adventure.AdventureComponent;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.ComponentUtils;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(ComponentUtils.class)
public class ComponentUtilsMixin {
    @ModifyVariable(
        method = "updateForEntity(Lnet/minecraft/commands/CommandSourceStack;Lnet/minecraft/network/chat/Component;Lnet/minecraft/world/entity/Entity;I)Lnet/minecraft/network/chat/MutableComponent;",
        at = @At(value = "INVOKE", target = "Lnet/minecraft/network/chat/Component;getContents()Lnet/minecraft/network/chat/ComponentContents;"),
        argsOnly = true
    )
    private static Component passVanillaComponent(Component component) {
        if (component instanceof AdventureComponent ac) {
            return ac.deepConverted();
        }

        return component;
    }
}
