package moe.skjsjhb.fraise.mixin.com.mojang.brigadier.exceptions;

import com.mojang.brigadier.Message;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import io.papermc.paper.brigadier.PaperBrigadier;
import moe.skjsjhb.fraise.anno.MixinType;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.util.ComponentMessageThrowable;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@MixinType.Required
@Mixin(CommandSyntaxException.class)
public class CommandSyntaxExceptionMixin implements ComponentMessageThrowable {
    @Shadow
    @Final
    private Message message;

    @Override
    public @Nullable Component componentMessage() {
        // XXX: Replace with MessageComponentSerializer after Paper removes it
        return PaperBrigadier.componentFromMessage(message);
    }
}
