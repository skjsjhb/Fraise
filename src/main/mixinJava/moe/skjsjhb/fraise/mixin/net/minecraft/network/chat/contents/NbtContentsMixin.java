package moe.skjsjhb.fraise.mixin.net.minecraft.network.chat.contents;

import net.minecraft.commands.CommandSourceStack;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.ComponentUtilsExt;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.contents.NbtContents;
import net.minecraft.world.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.Optional;

@Mixin(NbtContents.class)
public class NbtContentsMixin {
    // XXX: There exists another call to this method in a synthetic method, is Paper missing it?
    @Redirect(method = "resolve", at = @At(value = "INVOKE", target = "Lnet/minecraft/network/chat/ComponentUtils;updateForEntity(Lnet/minecraft/commands/CommandSourceStack;Ljava/util/Optional;Lnet/minecraft/world/entity/Entity;I)Ljava/util/Optional;"))
    private Optional<MutableComponent> redirectUpdateForEntity(
        CommandSourceStack source,
        Optional<Component> text,
        Entity sender,
        int recursionDepth
    ) {
        return ComponentUtilsExt.updateSeparatorForEntity(source, text, sender, recursionDepth);
    }
}
