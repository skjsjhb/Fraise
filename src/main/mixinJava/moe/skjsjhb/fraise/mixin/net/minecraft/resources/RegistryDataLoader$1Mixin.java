package moe.skjsjhb.fraise.mixin.net.minecraft.resources;

import net.minecraft.core.HolderLookup;
import net.minecraft.resources.RegistryDataLoader$1Ext;
import net.minecraft.resources.RegistryOps$RegistryInfoLookupExt;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Map;

@Mixin(targets = "net.minecraft.resources.RegistryDataLoader$1")
public class RegistryDataLoader$1Mixin implements RegistryOps$RegistryInfoLookupExt {
    @Unique
    private HolderLookup.Provider providerForBuilders;

    @Inject(method = "<init>", at = @At("CTOR_HEAD"))
    private void loadExtras(Map<?, ?> map, CallbackInfo ci) {
        // XXX: This field seems to be required, maybe enforce it?
        if (RegistryDataLoader$1Ext.init$$providerForBuilders.hasValue()) {
            providerForBuilders = RegistryDataLoader$1Ext.init$$providerForBuilders.get();
        }
    }

    @Override
    public HolderLookup.Provider lookupForValueCopyViaBuilders() {
        return providerForBuilders;
    }
}
