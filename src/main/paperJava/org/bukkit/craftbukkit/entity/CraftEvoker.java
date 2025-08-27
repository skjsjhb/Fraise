package org.bukkit.craftbukkit.entity;

import kotlin.NotImplementedError;
import org.bukkit.craftbukkit.CraftServer;
import org.bukkit.entity.Evoker;

public class CraftEvoker extends CraftSpellcaster implements Evoker {

    public CraftEvoker(CraftServer server, net.minecraft.world.entity.monster.Evoker entity) {
        super(server, entity);
    }

    @Override
    public net.minecraft.world.entity.monster.Evoker getHandle() {
        return (net.minecraft.world.entity.monster.Evoker) this.entity;
    }

    @Override
    public Evoker.Spell getCurrentSpell() {
        throw new NotImplementedError();
        // return Evoker.Spell.values()[this.getHandle().getCurrentSpell().ordinal()];
    }

    @Override
    public void setCurrentSpell(Evoker.Spell spell) {
        throw new NotImplementedError();
        // this.getHandle().setIsCastingSpell(spell == null ? SpellcasterIllager.IllagerSpell.NONE : SpellcasterIllager.IllagerSpell.byId(spell.ordinal()));
    }

    @Override
    public org.bukkit.entity.Sheep getWololoTarget() {
        throw new NotImplementedError();
        // Sheep sheep = this.getHandle().getWololoTarget();
        // return sheep == null ? null : (org.bukkit.entity.Sheep) sheep.getBukkitEntity();
    }

    @Override
    public void setWololoTarget(org.bukkit.entity.Sheep sheep) {
        throw new NotImplementedError();
        // this.getHandle().setWololoTarget(sheep == null ? null : ((org.bukkit.craftbukkit.entity.CraftSheep) sheep).getHandle());
    }
}
