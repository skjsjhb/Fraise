package net.minecraft.network.protocol.game

interface ServerboundInteractPacketExt {
    fun getEntityId(): Int

    fun isAttack(): Boolean
}
