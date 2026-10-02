# More Dyes

More Dyes adds more than 100 new dye colors to Minecraft, with stone, wood, wool, glass, chests, sheep and more in every color. Download it from [CurseForge](https://www.curseforge.com/minecraft/mc-mods/moredyes) or from this repository's releases.

This repository holds every version of the mod, one folder per Minecraft version:

| Folder | Minecraft | Loader | Java |
| --- | --- | --- | --- |
| [1.7.10](1.7.10) | 1.7.10 | Forge (GTNH buildscript) | 8 to play, 21 to build |
| [1.12.2](1.12.2) | 1.12.2 | Forge | 8 |
| [1.16.5](1.16.5) | 1.16.5 | Forge | 8 |
| [1.18.2](1.18.2) | 1.18.2 | Forge | 17 |
| [1.20.1](1.20.1) | 1.20.1 | Forge | 17 |
| [26.3](26.3) | 26.3 | NeoForge | 25 |
| [hytale](hytale) | Hytale | Asset pack (prototype) | none |

Each Minecraft folder is its own Gradle project with its own wrapper. Open the folder for the version you want in your IDE (not the repository root), and run `./gradlew build` inside it. The jar lands in that folder's `build/libs` as `moredyes-<mod version>-<Minecraft version>.jar`.

## Builds and releases

[`.github/workflows/build.yml`](.github/workflows/build.yml) builds each version on its own Java and checks that it loads on a server.

- Pull requests build only the versions they change.
- Every push to `main` builds all versions and replaces the **Development build** pre-release with the newest jar for each one.
- Pushing a tag such as `v1.2.0` builds every version as mod version 1.2.0, publishes a release with all the jars, and uploads each jar to CurseForge (project 252137) when the `CURSEFORGE_TOKEN` secret is set. Push the tag with git (`git tag v1.2.0 && git push origin v1.2.0`) or make the release on GitHub; both work.

## History

Each folder used to be its own repository. Their full histories were brought in here, so `git log -- 1.16.5` shows the 1.16.5 version's history. The old repositories are Naverene/MoreDyes_1_7_10, moredyes_1122, moredyes_1165, moredyes_1182, moredyes_1201, moredyes_263 and moredyes_hytale.
