package org.bukkit.craftbukkit.block;

import com.google.common.base.Preconditions;
import kotlin.NotImplementedError;
import net.minecraft.world.level.block.entity.StructureBlockEntity;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Structure;
import org.bukkit.block.structure.Mirror;
import org.bukkit.block.structure.StructureRotation;
import org.bukkit.block.structure.UsageMode;
import org.bukkit.craftbukkit.entity.CraftLivingEntity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.util.BlockVector;

public class CraftStructureBlock extends CraftBlockEntityState<StructureBlockEntity> implements Structure {

    private static final int MAX_SIZE = 48;

    public CraftStructureBlock(World world, StructureBlockEntity blockEntity) {
        super(world, blockEntity);
    }

    protected CraftStructureBlock(CraftStructureBlock state, Location location) {
        super(state, location);
    }

    @Override
    public String getStructureName() {
        return this.getSnapshot().getStructureName();
    }

    @Override
    public void setStructureName(String name) {
        Preconditions.checkArgument(name != null, "Structure name cannot be null");
        this.getSnapshot().setStructureName(name);
    }

    @Override
    public String getAuthor() {
        throw new NotImplementedError();
        // return this.getSnapshot().author;
    }

    @Override
    public void setAuthor(String author) {
        Preconditions.checkArgument(author != null, "Author name cannot be null");
        Preconditions.checkArgument(!author.isEmpty(), "Author name cannot be empty");
        throw new NotImplementedError();
        // this.getSnapshot().author = author;
    }

    @Override
    public void setAuthor(LivingEntity entity) {
        Preconditions.checkArgument(entity != null, "Structure Block author entity cannot be null");
        this.getSnapshot().createdBy(((CraftLivingEntity) entity).getHandle());
    }

    @Override
    public BlockVector getRelativePosition() {
        throw new NotImplementedError();
        // return CraftBlockVector.toBukkit(this.getSnapshot().structurePos);
    }

    @Override
    public void setRelativePosition(BlockVector vector) {
        Preconditions.checkArgument(CraftStructureBlock.isBetween(vector.getBlockX(), -CraftStructureBlock.MAX_SIZE, CraftStructureBlock.MAX_SIZE), "Structure Size (X) must be between -%s and %s but got %s", CraftStructureBlock.MAX_SIZE, CraftStructureBlock.MAX_SIZE, vector.getBlockX());
        Preconditions.checkArgument(CraftStructureBlock.isBetween(vector.getBlockY(), -CraftStructureBlock.MAX_SIZE, CraftStructureBlock.MAX_SIZE), "Structure Size (Y) must be between -%s and %s but got %s", CraftStructureBlock.MAX_SIZE, CraftStructureBlock.MAX_SIZE, vector.getBlockY());
        Preconditions.checkArgument(CraftStructureBlock.isBetween(vector.getBlockZ(), -CraftStructureBlock.MAX_SIZE, CraftStructureBlock.MAX_SIZE), "Structure Size (Z) must be between -%s and %s but got %s", CraftStructureBlock.MAX_SIZE, CraftStructureBlock.MAX_SIZE, vector.getBlockZ());
        throw new NotImplementedError();
        // this.getSnapshot().structurePos = CraftBlockVector.toBlockPosition(vector);
    }

    @Override
    public BlockVector getStructureSize() {
        throw new NotImplementedError();
        // return CraftBlockVector.toBukkit(this.getSnapshot().structureSize);
    }

    @Override
    public void setStructureSize(BlockVector vector) {
        Preconditions.checkArgument(CraftStructureBlock.isBetween(vector.getBlockX(), 0, CraftStructureBlock.MAX_SIZE), "Structure Size (X) must be between %s and %s but got %s", 0, CraftStructureBlock.MAX_SIZE, vector.getBlockX());
        Preconditions.checkArgument(CraftStructureBlock.isBetween(vector.getBlockY(), 0, CraftStructureBlock.MAX_SIZE), "Structure Size (Y) must be between %s and %s but got %s", 0, CraftStructureBlock.MAX_SIZE, vector.getBlockY());
        Preconditions.checkArgument(CraftStructureBlock.isBetween(vector.getBlockZ(), 0, CraftStructureBlock.MAX_SIZE), "Structure Size (Z) must be between %s and %s but got %s", 0, CraftStructureBlock.MAX_SIZE, vector.getBlockZ());
        throw new NotImplementedError();
        // this.getSnapshot().structureSize = CraftBlockVector.toBlockPosition(vector);
    }

    @Override
    public void setMirror(Mirror mirror) {
        Preconditions.checkArgument(mirror != null, "Mirror cannot be null");
        throw new NotImplementedError();
        // this.getSnapshot().mirror = net.minecraft.world.level.block.Mirror.valueOf(mirror.name());
    }

    @Override
    public Mirror getMirror() {
        throw new NotImplementedError();
        // return Mirror.valueOf(this.getSnapshot().mirror.name());
    }

    @Override
    public void setRotation(StructureRotation rotation) {
        Preconditions.checkArgument(rotation != null, "StructureRotation cannot be null");
        throw new NotImplementedError();
        // this.getSnapshot().rotation = Rotation.valueOf(rotation.name());
    }

    @Override
    public StructureRotation getRotation() {
        throw new NotImplementedError();
        // return StructureRotation.valueOf(this.getSnapshot().rotation.name());
    }

    @Override
    public void setUsageMode(UsageMode mode) {
        Preconditions.checkArgument(mode != null, "UsageMode cannot be null");
        throw new NotImplementedError();
        // this.getSnapshot().mode = StructureMode.valueOf(mode.name());
    }

    @Override
    public UsageMode getUsageMode() {
        return UsageMode.valueOf(this.getSnapshot().getMode().name());
    }

    @Override
    public void setIgnoreEntities(boolean flag) {
        throw new NotImplementedError();
        // this.getSnapshot().ignoreEntities = flag;
    }

    @Override
    public boolean isIgnoreEntities() {
        throw new NotImplementedError();
        // return this.getSnapshot().ignoreEntities;
    }

    @Override
    public void setShowAir(boolean showAir) {
        throw new NotImplementedError();
        // this.getSnapshot().showAir = showAir;
    }

    @Override
    public boolean isShowAir() {
        throw new NotImplementedError();
        // return this.getSnapshot().showAir;
    }

    @Override
    public void setBoundingBoxVisible(boolean showBoundingBox) {
        throw new NotImplementedError();
        // this.getSnapshot().showBoundingBox = showBoundingBox;
    }

    @Override
    public boolean isBoundingBoxVisible() {
        throw new NotImplementedError();
        // return this.getSnapshot().showBoundingBox;
    }

    @Override
    public void setIntegrity(float integrity) {
        Preconditions.checkArgument(CraftStructureBlock.isBetween(integrity, 0.0f, 1.0f), "Integrity must be between 0.0f and 1.0f but got %s", integrity);
        // this.getSnapshot().integrity = integrity;
        throw new NotImplementedError();
    }

    @Override
    public float getIntegrity() {
        throw new NotImplementedError();
        // return this.getSnapshot().integrity;
    }

    @Override
    public void setSeed(long seed) {
        throw new NotImplementedError();
        // this.getSnapshot().seed = seed;
    }

    @Override
    public long getSeed() {
        throw new NotImplementedError();
        // return this.getSnapshot().seed;
    }

    @Override
    public void setMetadata(String metadata) {
        throw new NotImplementedError();
        // Preconditions.checkArgument(metadata != null, "Structure metadata cannot be null");
        // if (this.getUsageMode() == UsageMode.DATA) {
        //     this.getSnapshot().metaData = metadata;
        // }
    }

    @Override
    public String getMetadata() {
        throw new NotImplementedError();
        // return this.getSnapshot().metaData;
    }

    @Override
    protected void applyTo(StructureBlockEntity blockEntity) {
        super.applyTo(blockEntity);
        net.minecraft.world.level.LevelAccessor access = this.getWorldHandle();

        // Ensure block type is correct
        if (access instanceof net.minecraft.world.level.Level) {
            blockEntity.setMode(blockEntity.getMode());
        } else if (access != null) {
            // Custom handle during world generation
            // From StructureBlockEntity#setMode(BlockPropertyStructureMode)
            net.minecraft.world.level.block.state.BlockState state = access.getBlockState(this.getPosition());
            if (state.is(net.minecraft.world.level.block.Blocks.STRUCTURE_BLOCK)) {
                access.setBlock(this.getPosition(), state.setValue(net.minecraft.world.level.block.StructureBlock.MODE, blockEntity.getMode()), 2);
            }
        }
    }

    @Override
    public CraftStructureBlock copy() {
        return new CraftStructureBlock(this, null);
    }

    @Override
    public CraftStructureBlock copy(Location location) {
        return new CraftStructureBlock(this, location);
    }

    private static boolean isBetween(int num, int min, int max) {
        return num >= min && num <= max;
    }

    private static boolean isBetween(float num, float min, float max) {
        return num >= min && num <= max;
    }
}
