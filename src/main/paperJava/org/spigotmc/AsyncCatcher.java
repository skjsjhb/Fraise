package org.spigotmc;

import kotlin.NotImplementedError;

public class AsyncCatcher {

    public static void catchOp(String reason) {
        if (!ca.spottedleaf.moonrise.common.util.TickThread.isTickThread()) { // Paper - chunk system
            // MinecraftServer.LOGGER.error("Thread {} failed main thread check: {}", Thread.currentThread().getName(), reason, new Throwable()); // Paper
            // throw new IllegalStateException("Asynchronous " + reason + "!");
            throw new NotImplementedError();
        }
    }
}
