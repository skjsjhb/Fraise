package io.papermc.paper.entity;

import kotlin.NotImplementedError;
import net.kyori.adventure.sound.Sound;
import net.minecraft.world.entity.Shearable;
import org.jetbrains.annotations.NotNull;

public interface PaperShearable extends io.papermc.paper.entity.Shearable {

    Shearable getHandle();

    @Override
    default boolean readyToBeSheared() {
        return this.getHandle().readyForShearing();
    }

    @Override
    default void shear(@NotNull Sound.Source source) {
        throw new NotImplementedError();
        // if (!(this.getHandle().level() instanceof final ServerLevel serverLevel)) return;
        // this.getHandle().shear(serverLevel, PaperAdventure.asVanilla(source), new ItemStack(Items.SHEARS));
    }
}
