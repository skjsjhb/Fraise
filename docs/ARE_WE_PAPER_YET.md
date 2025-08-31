# Are we Paper yet?

What Fraise does can be simplified as "getting (a subset of) Paper to run on Fabric". To do that, we grab patches and
Java sources from Paper, then try to convert them so that they can be deployed to Fabric. Here's our current progress:

## Patches Ported

![.](https://img.shields.io/badge/dynamic/json?url=https%3A%2F%2Fskjsjhb.github.io%2FFraise%2Fprogress.json&query=%24.patches&label=Patches&suffix=%25&color=hotpink)

Paper edits Minecraft sources directly, while we must do it in a mod-compatible way. We mainly use mixins, companions,
extensions and duck interfaces to achieve similar output, and will also reach to Javassist/ASM for extra help when
mixins are not sufficient.

Note that Fraise discards most optimizations and many bug fixes in favor of mod compatibility. These discarded patches
are also counted as "ported" since they won't block our progress.

## Sources Ported

![.](https://img.shields.io/badge/dynamic/json?url=https%3A%2F%2Fskjsjhb.github.io%2FFraise%2Fprogress.json&query=%24.sources&label=Sources&suffix=%25&color=hotpink)

Getting sources of Paper server implementation to run is the ultimate goal of Fraise. We first grab all Paper sources
and get them to compile by commenting out lots of code, then gradually restore/rewrite them. This task also heavily
depends on the patches ported, as a considerable amount of code rely on those modifications.

One important task of Fraise is to verify the compatibility with mods, thus a class won't be counted as "ported" until
we've done such verification, even it has been functioning correctly in the plugin world.

