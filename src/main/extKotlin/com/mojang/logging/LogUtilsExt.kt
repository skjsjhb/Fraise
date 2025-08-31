package com.mojang.logging

import moe.skjsjhb.fraise.mixin.com.mojang.logging.LogUtilsMixin
import org.slf4j.Logger
import org.slf4j.LoggerFactory

object LogUtilsExt {
    @JvmStatic
    fun getClassLogger(): Logger = LoggerFactory.getLogger(LogUtilsMixin.getStackWalker().callerClass.simpleName)
}
