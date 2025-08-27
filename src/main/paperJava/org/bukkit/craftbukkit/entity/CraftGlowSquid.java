package org.bukkit.craftbukkit.entity;

import com.google.common.base.Preconditions;
import kotlin.NotImplementedError;
import org.bukkit.craftbukkit.CraftServer;
import org.bukkit.entity.GlowSquid;

public class CraftGlowSquid extends CraftSquid implements GlowSquid {

    public CraftGlowSquid(CraftServer server, net.minecraft.world.entity.GlowSquid entity) {
        super(server, entity);
    }

    @Override
    public net.minecraft.world.entity.GlowSquid getHandle() {
        return (net.minecraft.world.entity.GlowSquid) this.entity;
    }

    @Override
    public int getDarkTicksRemaining() {
        return this.getHandle().getDarkTicksRemaining();
    }

    @Override
    public void setDarkTicksRemaining(int darkTicksRemaining) {
        Preconditions.checkArgument(darkTicksRemaining >= 0, "darkTicksRemaining must be >= 0");
        throw new NotImplementedError();
        // this.getHandle().setDarkTicks(darkTicksRemaining);
    }
}
