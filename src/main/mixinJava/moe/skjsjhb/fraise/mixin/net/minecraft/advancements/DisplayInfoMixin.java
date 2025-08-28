package moe.skjsjhb.fraise.mixin.net.minecraft.advancements;

import io.papermc.paper.advancement.AdvancementDisplay;
import io.papermc.paper.advancement.PaperAdvancementDisplay;
import net.minecraft.advancements.DisplayInfo;
import net.minecraft.advancements.DisplayInfoExt;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(DisplayInfo.class)
public class DisplayInfoMixin implements DisplayInfoExt {
    @Unique
    private final AdvancementDisplay paper = new PaperAdvancementDisplay((DisplayInfo) (Object) this);

    @Override
    public AdvancementDisplay paper() {
        return paper;
    }
}
