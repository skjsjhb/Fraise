package moe.skjsjhb.fraise.mixin.net.minecraft.server.bossevents;

import net.minecraft.server.bossevents.CustomBossEvent;
import net.minecraft.server.bossevents.CustomBossEventExt;
import org.bukkit.boss.KeyedBossBar;
import org.bukkit.craftbukkit.boss.CraftKeyedBossbar;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(CustomBossEvent.class)
public class CustomBossEventMixin implements CustomBossEventExt {
    @Unique
    private KeyedBossBar bossBar;

    @Override
    public @NotNull KeyedBossBar getBukkitEntity() {
        if (bossBar == null) {
            bossBar = new CraftKeyedBossbar((CustomBossEvent) (Object) this);
        }
        return bossBar;
    }
}
