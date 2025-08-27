package org.bukkit.craftbukkit.entity;

import com.google.common.base.Preconditions;
import kotlin.NotImplementedError;
import net.minecraft.world.entity.monster.SpellcasterIllager;
import org.bukkit.craftbukkit.CraftServer;
import org.bukkit.entity.Spellcaster;

public class CraftSpellcaster extends CraftIllager implements Spellcaster {

    public CraftSpellcaster(CraftServer server, SpellcasterIllager entity) {
        super(server, entity);
    }

    @Override
    public SpellcasterIllager getHandle() {
        return (SpellcasterIllager) this.entity;
    }

    @Override
    public Spell getSpell() {
        // return CraftSpellcaster.toBukkitSpell(this.getHandle().getCurrentSpell());
        throw new NotImplementedError();
    }

    @Override
    public void setSpell(Spell spell) {
        Preconditions.checkArgument(spell != null, "Use Spell.NONE");

        throw new NotImplementedError();
        // this.getHandle().setIsCastingSpell(CraftSpellcaster.toNMSSpell(spell));
    }

    // throw new NotImplementedError();
    // public static Spell toBukkitSpell(SpellcasterIllager.IllagerSpell spell) {
    //     return Spell.valueOf(spell.name());
    // }

    // throw new NotImplementedError();
    // public static SpellcasterIllager.IllagerSpell toNMSSpell(Spell spell) {
    //     return SpellcasterIllager.IllagerSpell.byId(spell.ordinal());
    // }
}
