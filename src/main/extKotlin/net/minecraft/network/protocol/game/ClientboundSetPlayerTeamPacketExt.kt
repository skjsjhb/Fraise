package net.minecraft.network.protocol.game

import net.minecraft.world.scores.PlayerTeam
import java.util.*

object ClientboundSetPlayerTeamPacketExt {
    @JvmStatic
    fun createMultiplePlayerPacket(
        team: PlayerTeam,
        players: Collection<String>,
        operation: ClientboundSetPlayerTeamPacket.Action
    ) = ClientboundSetPlayerTeamPacket(
        team.name,
        if (operation === ClientboundSetPlayerTeamPacket.Action.ADD) 3 else 4,
        Optional.empty(),
        players
    )
}
