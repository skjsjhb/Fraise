package moe.skjsjhb.fraise.mixin.net.minecraft.advancements;

import net.minecraft.advancements.AdvancementHolder;
import net.minecraft.advancements.AdvancementHolderExt;
import org.bukkit.advancement.Advancement;
import org.bukkit.craftbukkit.advancement.CraftAdvancement;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(AdvancementHolder.class)
public class AdvancementHolderMixin implements AdvancementHolderExt {
    @Override
    public Advancement toBukkit() {
        return new CraftAdvancement((AdvancementHolder) (Object) this);
    }
}
