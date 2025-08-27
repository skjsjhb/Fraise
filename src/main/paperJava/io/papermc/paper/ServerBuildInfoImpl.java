package io.papermc.paper;

import moe.skjsjhb.fraise.anno.Ported;
import moe.skjsjhb.fraise.conf.FraiseConf;
import moe.skjsjhb.fraise.misc.BuildInfo;
import net.kyori.adventure.key.Key;
import net.minecraft.SharedConstants;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Optional;
import java.util.OptionalInt;

@Ported
public record ServerBuildInfoImpl(
    Key brandId,
    String brandName,
    String minecraftVersionId,
    String minecraftVersionName,
    OptionalInt buildNumber,
    Instant buildTime,
    Optional<String> gitBranch,
    Optional<String> gitCommit
) implements ServerBuildInfo {
    // TODO: Use a formal namespace
    private static final Key BRAND_FRAISE_ID = Key.key("fraise", "fraise");
    private static final String BRAND_PAPER_NAME = "Paper";
    private static final String BRAND_FRAISE_NAME = "Fraise";

    private static final String BUILD_DEV = "DEV";

    private static final BuildInfo buildInfo = BuildInfo.INSTANCE;
    private static final boolean shouldUsePaperBrand = FraiseConf.INSTANCE.getUsePaperBrand();

    public ServerBuildInfoImpl() {
        this(
            shouldUsePaperBrand ? BRAND_PAPER_ID : BRAND_FRAISE_ID,
            shouldUsePaperBrand ? BRAND_PAPER_NAME : BRAND_FRAISE_NAME,
            SharedConstants.getCurrentVersion().id(),
            SharedConstants.getCurrentVersion().name(),
            toOptionalInt(buildInfo.getBuildNumber()),
            buildInfo.getTime(),
            Optional.ofNullable(buildInfo.getBranch()),
            Optional.ofNullable(buildInfo.getCommit())
        );
    }

    private static OptionalInt toOptionalInt(@Nullable Integer it) {
        return it == null ? OptionalInt.empty() : OptionalInt.of(it);
    }

    @Override
    public boolean isBrandCompatible(final @NotNull Key brandId) {
        return brandId.equals(this.brandId);
    }

    @Override
    public @NotNull String asString(final @NotNull StringRepresentation representation) {
        final StringBuilder sb = new StringBuilder();
        sb.append(this.minecraftVersionId);
        sb.append('-');
        if (this.buildNumber.isPresent()) {
            sb.append(this.buildNumber.getAsInt());
        } else {
            sb.append(BUILD_DEV);
        }
        final boolean hasGitBranch = this.gitBranch.isPresent();
        final boolean hasGitCommit = this.gitCommit.isPresent();
        if (hasGitBranch || hasGitCommit) {
            sb.append('-');
        }
        if (hasGitBranch && representation == StringRepresentation.VERSION_FULL) {
            sb.append(this.gitBranch.get());
            if (hasGitCommit) {
                sb.append('@');
            }
        }
        if (hasGitCommit) {
            sb.append(this.gitCommit.get());
        }
        if (representation == StringRepresentation.VERSION_FULL) {
            sb.append(' ');
            sb.append('(');
            sb.append(this.buildTime.truncatedTo(ChronoUnit.SECONDS));
            sb.append(')');
        }
        return sb.toString();
    }
}
