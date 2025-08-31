package moe.skjsjhb.fraise.mixin.net.minecraft.resources;

import net.minecraft.core.HolderLookup;
import net.minecraft.resources.RegistryOps;
import net.minecraft.resources.RegistryOps$RegistryInfoLookupExt;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(RegistryOps.HolderLookupAdapter.class)
public class RegistryOps$HolderLookupAdapterMixin implements RegistryOps$RegistryInfoLookupExt {
    @Shadow
    @Final
    private HolderLookup.Provider lookupProvider;

    @Override
    public HolderLookup.@NotNull Provider lookupForValueCopyViaBuilders() {
        return lookupProvider;
    }
}
