package com.destroystokyo.paper.network;

import com.destroystokyo.paper.event.server.PaperServerListPingEvent;
import kotlin.NotImplementedError;
import net.kyori.adventure.text.Component;
import net.minecraft.server.MinecraftServer;
import org.bukkit.entity.Player;
import org.bukkit.util.CachedServerIcon;

import javax.annotation.Nullable;

class PaperServerListPingEventImpl extends PaperServerListPingEvent {

    private final MinecraftServer server;

    PaperServerListPingEventImpl(MinecraftServer server, StatusClient client, int protocolVersion, @Nullable CachedServerIcon icon) {
        super(client,
            Component.text().build(), // throw new NotImplementedError();
            // server.motd(),
            server.getPlayerCount(), server.getMaxPlayers(),
            server.getServerModName() + ' ' + server.getServerVersion(), protocolVersion, icon);
        this.server = server;
    }

    @Override
    protected final Object[] getOnlinePlayers() {
        throw new NotImplementedError();
        // return this.server.getPlayerList().players.toArray();
    }

    @Override
    protected final Player getBukkitPlayer(Object player) {
        throw new NotImplementedError();
        // return ((ServerPlayer) player).getBukkitEntity();
    }

}
