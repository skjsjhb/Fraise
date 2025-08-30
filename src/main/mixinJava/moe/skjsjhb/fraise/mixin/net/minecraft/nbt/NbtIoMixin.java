package moe.skjsjhb.fraise.mixin.net.minecraft.nbt;

import com.llamalad7.mixinextras.sugar.Local;
import io.netty.buffer.ByteBufInputStream;
import net.minecraft.nbt.NbtAccounter;
import net.minecraft.nbt.NbtIo;
import org.spigotmc.LimitStream;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

import java.io.DataInput;
import java.io.DataInputStream;

@Mixin(NbtIo.class)
public class NbtIoMixin {
    @ModifyVariable(
        method = "read(Ljava/io/DataInput;Lnet/minecraft/nbt/NbtAccounter;)Lnet/minecraft/nbt/CompoundTag;",
        at = @At("HEAD"),
        argsOnly = true
    )
    private static DataInput maybeAlterInputStream(DataInput input, @Local(argsOnly = true) NbtAccounter accounter) {
        if (input instanceof ByteBufInputStream bis) {
            return new DataInputStream(new LimitStream(bis, accounter));
        }
        return input;
    }
}
