package org.bukkit.craftbukkit.util;

import kotlin.NotImplementedError;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.Level;

public final class CraftDimensionUtil {

    private CraftDimensionUtil() {
    }

    public static ResourceKey<Level> getMainDimensionKey(Level world) {
        throw new NotImplementedError();
        // ResourceKey<LevelStem> typeKey = world.getTypeKey();
        // if (typeKey == LevelStem.OVERWORLD) {
        //     return Level.OVERWORLD;
        // } else if (typeKey == LevelStem.NETHER) {
        //     return Level.NETHER;
        // } else if (typeKey == LevelStem.END) {
        //     return Level.END;
        // }
        //
        // return world.dimension();
    }
}
