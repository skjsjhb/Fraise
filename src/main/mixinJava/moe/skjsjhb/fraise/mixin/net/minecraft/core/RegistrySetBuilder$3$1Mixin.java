package moe.skjsjhb.fraise.mixin.net.minecraft.core;

import net.minecraft.core.HolderLookup;
import net.minecraft.resources.RegistryOps$RegistryInfoLookupExt;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(targets = "net.minecraft.core.RegistrySetBuilder$3$1")
public class RegistrySetBuilder$3$1Mixin implements RegistryOps$RegistryInfoLookupExt {
    @Override
    public HolderLookup.@NotNull Provider lookupForValueCopyViaBuilders() {
        // XXX: Update this when Paper implements it
        throw new UnsupportedOperationException("Not implemented");
    }
}
