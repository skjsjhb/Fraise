package moe.skjsjhb.fraise.mixin.net.minecraft.network.chat.contents;

import net.minecraft.commands.CommandSourceStack;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.ComponentUtilsExt;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.contents.SelectorContents;
import net.minecraft.world.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.Optional;

@Mixin(SelectorContents.class)
public class SelectorContentsMixin {
    @Redirect(method = "resolve", at = @At(value = "INVOKE", target = "Lnet/minecraft/network/chat/ComponentUtils;updateForEntity(Lnet/minecraft/commands/CommandSourceStack;Ljava/util/Optional;Lnet/minecraft/world/entity/Entity;I)Ljava/util/Optional;"))
    private Optional<MutableComponent> redirectUpdateForEntity(
        CommandSourceStack css,
        Optional<Component> comp,
        Entity entity,
        int recursionDepth
    ) {
        return ComponentUtilsExt.updateSeparatorForEntity(css, comp, entity, recursionDepth);
    }
}
