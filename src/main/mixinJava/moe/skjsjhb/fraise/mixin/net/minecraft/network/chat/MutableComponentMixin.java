package moe.skjsjhb.fraise.mixin.net.minecraft.network.chat;

import io.papermc.paper.adventure.AdventureComponent;
import net.minecraft.network.chat.MutableComponent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(MutableComponent.class)
public class MutableComponentMixin {
    @ModifyVariable(method = "equals", at = @At("HEAD"), argsOnly = true)
    private Object adventureEqual(Object other) {
        if (other instanceof AdventureComponent ac) {
            return ac.deepConverted();
        }

        return other;
    }
}
