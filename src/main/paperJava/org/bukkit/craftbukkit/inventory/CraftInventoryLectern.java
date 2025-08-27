package org.bukkit.craftbukkit.inventory;

import kotlin.NotImplementedError;
import net.minecraft.world.Container;
import net.minecraft.world.MenuProvider;
import org.bukkit.block.Lectern;
import org.bukkit.inventory.LecternInventory;

public class CraftInventoryLectern extends CraftInventory implements LecternInventory {

    public MenuProvider provider;

    public CraftInventoryLectern(Container inventory) {
        super(inventory);
        throw new NotImplementedError();
        // if (inventory instanceof LecternBlockEntity.LecternInventory) {
        //     this.provider = ((LecternBlockEntity.LecternInventory) inventory).getLectern();
        // }
    }

    @Override
    public Lectern getHolder() {
        // return (Lectern) this.inventory.getOwner();
        throw new NotImplementedError();
    }
}
