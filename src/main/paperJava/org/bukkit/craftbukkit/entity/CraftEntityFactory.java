package org.bukkit.craftbukkit.entity;

import com.mojang.logging.LogUtils;
import kotlin.NotImplementedError;
import org.bukkit.entity.EntityFactory;
import org.bukkit.entity.EntitySnapshot;
import org.slf4j.Logger;

public class CraftEntityFactory implements EntityFactory {

    private static final Logger LOGGER = LogUtils.getLogger();
    private static final CraftEntityFactory instance;

    static {
        instance = new CraftEntityFactory();
    }

    private CraftEntityFactory() {
    }

    @Override
    public EntitySnapshot createEntitySnapshot(String input) {
        throw new NotImplementedError();
        // Preconditions.checkArgument(input != null, "Input string cannot be null");
        //
        // CompoundTag tag;
        // try {
        //     tag = TagParser.parseCompoundFully(input);
        // } catch (CommandSyntaxException e) {
        //     throw new IllegalArgumentException("Could not parse Entity: " + input, e);
        // }
        //
        // final EntityType<?> type;
        // try (final ProblemReporter.ScopedCollector problemReporter = new ProblemReporter.ScopedCollector(
        //     () -> "createEntitySnapshot", LOGGER
        // )) {
        //     type = EntityType.by(TagValueInput.createGlobal(problemReporter, tag)).orElse(null);
        // }
        // if (type == null) {
        //     throw new IllegalArgumentException("Could not parse Entity: " + input);
        // }
        //
        // return CraftEntitySnapshot.create(tag, CraftEntityType.minecraftToBukkit(type));
    }

    public static CraftEntityFactory instance() {
        return CraftEntityFactory.instance;
    }
}
