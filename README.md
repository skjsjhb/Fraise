# Fraise

An experiment with plugins and Fabric.

## Features

> [!NOTE]
> This project is working in progress. To not raise expectations that we might not eventually be able to fulfill, we've
> omitted most parts related to the features. This section will be updated once we've reached the first release.

## What about (Neo)Forge?

We do have a strong wish of making Fraise available on all major mod platforms. In fact, we've already made several
attempts, yet none of them has succeeded. Eventually, we've dropped support of (Neo)Forge, in order not having to
sacrifice all useful features in our design goals.

<details>
<summary>Why?</summary>

First, we want to be clear, the whole architecture of (Neo)Forge is very, very unsuitable for building frameworks (like
Paper) on top of it. This is not to say that it's not a good mod loader — we have tons of high-quality mods built with
it. It's just that when it comes to a place where — unavoidable — deep reflections, privileged accesses and hacks come
into play, the loader is not open/extendable enough to accept them.

As Fraise is a free software, anyone can port it to (Neo)Forge, and we're also willing to help. However, to avoid
fruitless efforts (which we've already made, in vain), we'll list a few major unresolved problems we've encountered:

### Module Disasters

(Neo)Forge heavily relies on JPMS and put each archive (a mod, a JiJ, a library of the loader itself, etc.) in the
module path. This causes JPMS to create automatic modules for them, if they don't declare their own ones. JPMS enforces
single provider, which is not true for a lot of libraries. When a mod is carrying a library that (Neo)Forge already
provides, the game crashes. When two archives share the same package, the game crashes. When a library is shadowed in
a mod and provided as an archive by another mod, the game also crashes. Despite having written complex and dirty build
scripts with package-level workarounds, we were still unable to get things like Maven resolver to work, and our patience
had drained out. Eventually, we gave up with a conclusion that making everything modules isn't the correct way, at least
not for every use case.

We also tried to use custom class loaders where everything is merged into our mod module, but then noticed how it
would make mixins ultra complex since Paper symbols are no longer visible to them. After some struggles with SPIs and
reflections, we decided this was definitely impractical — if not impossible.

Relocating is also not an option, since some split packages provide public types as part of the plugin API. What's
more, as mentioned above, some split packages are provided by (Neo)Forge libraries — which we can't change.
Technically, it's still possible to add the relocated classes to our reflection patcher, but we still don't know
whether all the dependencies can be safely repackaged. Plus, the reflection patcher itself is not designed for stable
APIs.

### Mixin Failures

Mixins on (Neo)Forge are not applied to certain target classes (e.g. authlib, brigadier, logging). Important APIs
like advanced commands rely on these patches, making them not simply ignorable. Using other instrumentation libraries
to patch classes on-the-fly is potentially possible, but is heavily limited by module encapsulation (which removes the
ability of performing most privileged actions). What's more, such instrumentation must be done before loading any mod,
making it out of the scope of what Fraise tries to achieve.

Another choice would be reimplementing those libraries with the required modifications and delegate calls to the
actual library, possibly with some type conversions, then make the modified version visible to the plugin world. This
requires using a custom class loader, which has already been ruled out above. Maintaining a proxy of these libraries
is also far out of the scope of a plugin runtime.

Coremods may be another viable option, but is out of the scope of this project. We also don't have a strong belief on
the possibility of porting all the complex patches using manual bytecode instrumentation.

### Loader Limitations

Mod classes are not loaded by an extendable class loader on (Neo)Forge. In fact, neither (Neo)Forge nor Fabric
provides public ways to extend the class path. However, there are private methods to do so in Fabric and can be easily
implemented (in a pretty safe way), while the same is much harder for (Neo)Forge since it uses a module-based class
loader, and if that does not sound tricky enough, the class loader doesn't even open its module to mods for reflections,
leaving us no choice but to use `Unsafe`, which we've been trying to avoid.

Without an extendable class loader, providing multiple API variants (another core feature of Fraise) is almost
impossible, as there is no way to define a duplicated class with modified content if it's already in the class path. The
only solution is to use a custom class loader, bringing us back to its impracticality.

### Other Thoughts

It's worth mentioning that the above limitations are not crucial for getting plugins to run on (Neo)Forge. There are
ways to address them with higher privileges, or by trading off some convenience:

- Release Fraise as a server executable and takes over full control of how mods and plugins are loaded at the startup
  stage.
- Use A/B archives. Every time the mod list changes, re-patch plugin classes and emit a new mod jar, then prompt the
  user to restart the game.
- Just embrace `Unsafe`.
- Use a fork of (Neo)Forge that bypasses the limitations.

These may be viable for a project that builds a hybrid server, yet Fraise is a mod, not a server software, therefore
it's not designed to do anything outside the scope of what a mod can do, like manipulating class paths.

---

However, despite all these known obstacles, we must also mention that as the developers of Fraise, we want it to support
major mod platforms more than anyone else. We would be glad to know that we were wrong and those limitations can
actually be addressed in a fairly easy way. Thus, if you have a good idea, or a workaround, or just some evidence to
disprove our points about (Neo)Forge, please do let us know!

</details>

## About the Name

*Fraise* means *strawberry* in French. But why did we name it this way? Use the `/nya` command to find out :cat:

## License

Fraise is licensed separately as it uses sources from upstream projects:

- Code and resources under `paper-api`, `src/main/paperJava`, `src/main/paperResources`, `src/porting` inherit the
  license from Paper, which is (currently) the
  [GNU General Public License v3.0](https://www.gnu.org/licenses/gpl-3.0.html), but may subject to change in the future.
  Note that the Paper team did not explicitly claim the allowance of using a later GPL version, so this license is
  listed as `GPL-3.0-only` in the mod metadata.
- All other parts in this repository are licensed under the
  [GNU Affero General Public License v3.0](https://www.gnu.org/licenses/agpl-3.0.html). You may also pick any later
  version of this license at your option. This license is listed as `AGPL-3.0-or-later` in the mod metadata.
