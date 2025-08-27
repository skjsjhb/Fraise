package org.bukkit.craftbukkit.map;

import kotlin.NotImplementedError;
import net.minecraft.world.level.saveddata.maps.MapItemSavedData;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.craftbukkit.entity.CraftPlayer;
import org.bukkit.map.MapRenderer;
import org.bukkit.map.MapView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.WeakHashMap;
import java.util.logging.Level;

public final class CraftMapView implements MapView {

    private final Map<CraftPlayer, RenderData> renderCache = new WeakHashMap<>();
    private final List<MapRenderer> renderers = new ArrayList<>();
    private final Map<MapRenderer, Map<CraftPlayer, CraftMapCanvas>> canvases = new HashMap<>();
    final MapItemSavedData worldMap;

    public CraftMapView(MapItemSavedData worldMap) {
        this.worldMap = worldMap;
        this.addRenderer(new CraftMapRenderer(worldMap));
    }

    @Override
    public int getId() {
        // return this.worldMap.id.id();
        throw new NotImplementedError();
    }

    @Override
    public boolean isVirtual() {
        return !this.renderers.isEmpty() && !(this.renderers.get(0) instanceof CraftMapRenderer);
    }

    @Override
    public Scale getScale() {
        return Scale.valueOf(this.worldMap.scale);
    }

    @Override
    public void setScale(Scale scale) {
        // this.worldMap.scale = scale.getValue();
        throw new NotImplementedError();
    }

    @Override
    public World getWorld() {
        throw new NotImplementedError();
        // ResourceKey<net.minecraft.world.level.Level> dimension = this.worldMap.dimension;
        // ServerLevel world = MinecraftServer.getServer().getLevel(dimension);
        //
        // if (world != null) {
        //     return world.getWorld();
        // }
        //
        // if (this.worldMap.uniqueId != null) {
        //     return Bukkit.getServer().getWorld(this.worldMap.uniqueId);
        // }
        // return null;
    }

    @Override
    public void setWorld(World world) {
        throw new NotImplementedError();
        // this.worldMap.dimension = ((CraftWorld) world).getHandle().dimension();
        // this.worldMap.uniqueId = world.getUID();
    }

    @Override
    public int getCenterX() {
        return this.worldMap.centerX;
    }

    @Override
    public int getCenterZ() {
        return this.worldMap.centerZ;
    }

    @Override
    public void setCenterX(int x) {
        // this.worldMap.centerX = x;
        throw new NotImplementedError();
    }

    @Override
    public void setCenterZ(int z) {
        // this.worldMap.centerZ = z;
        throw new NotImplementedError();
    }

    @Override
    public List<MapRenderer> getRenderers() {
        return new ArrayList<MapRenderer>(this.renderers);
    }

    @Override
    public void addRenderer(MapRenderer renderer) {
        if (!this.renderers.contains(renderer)) {
            this.renderers.add(renderer);
            this.canvases.put(renderer, new WeakHashMap<>());
            renderer.initialize(this);
        }
    }

    @Override
    public boolean removeRenderer(MapRenderer renderer) {
        if (this.renderers.contains(renderer)) {
            this.renderers.remove(renderer);
            for (Map.Entry<CraftPlayer, CraftMapCanvas> entry : this.canvases.get(renderer).entrySet()) {
                for (int x = 0; x < 128; ++x) {
                    for (int y = 0; y < 128; ++y) {
                        entry.getValue().setPixel(x, y, (byte) -1);
                    }
                }
            }
            this.canvases.remove(renderer);
            return true;
        } else {
            return false;
        }
    }

    private boolean isContextual() {
        for (MapRenderer renderer : this.renderers) {
            if (renderer.isContextual()) return true;
        }
        return false;
    }

    public RenderData render(CraftPlayer player) {
        boolean context = this.isContextual();
        RenderData render = this.renderCache.get(context ? player : null);

        if (render == null) {
            render = new RenderData();
            this.renderCache.put(context ? player : null, render);
        }

        if (context) {
            this.renderCache.remove(null);
        }

        Arrays.fill(render.buffer, (byte) 0);
        render.cursors.clear();

        for (MapRenderer renderer : this.renderers) {
            CraftMapCanvas canvas = this.canvases.get(renderer).get(renderer.isContextual() ? player : null);
            if (canvas == null) {
                canvas = new CraftMapCanvas(this);
                this.canvases.get(renderer).put(renderer.isContextual() ? player : null, canvas);
            }

            canvas.setBase(render.buffer);
            try {
                renderer.render(this, canvas, player);
            } catch (Throwable ex) {
                Bukkit.getLogger().log(Level.SEVERE, "Could not render map using renderer " + renderer.getClass().getName(), ex);
            }

            byte[] buf = canvas.getBuffer();
            for (int i = 0; i < buf.length; ++i) {
                byte color = buf[i];
                // There are 248 valid color id's, 0 -> 127 and -128 -> -9
                if (color >= 0 || color <= -9) render.buffer[i] = color;
            }

            for (int i = 0; i < canvas.getCursors().size(); ++i) {
                render.cursors.add(canvas.getCursors().getCursor(i));
            }
        }

        return render;
    }

    @Override
    public boolean isTrackingPosition() {
        // return this.worldMap.trackingPosition;
        throw new NotImplementedError();
    }

    @Override
    public void setTrackingPosition(boolean trackingPosition) {
        // this.worldMap.trackingPosition = trackingPosition;
        throw new NotImplementedError();
    }

    @Override
    public boolean isUnlimitedTracking() {
        // return this.worldMap.unlimitedTracking;
        throw new NotImplementedError();
    }

    @Override
    public void setUnlimitedTracking(boolean unlimited) {
        // this.worldMap.unlimitedTracking = unlimited;
        throw new NotImplementedError();
    }

    @Override
    public boolean isLocked() {
        return this.worldMap.locked;
    }

    @Override
    public void setLocked(boolean locked) {
        // this.worldMap.locked = locked;
        throw new NotImplementedError();
    }
}
