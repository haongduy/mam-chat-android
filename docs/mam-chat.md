# MÂM Chat for Android

MÂM Chat is a rebranded fork of Element X Android for the existing Matrix homeserver `https://chat.mam.info.vn` (server name `chat.mam.info.vn`). User IDs stay in that namespace, for example `@duy:chat.mam.info.vn`. This repository does not create accounts, does not change the server, and does not contain passwords.

It is a native Element X client (Compose UI and the Matrix Rust SDK), not a WebView wrapper of Element Web.

## Upstream base

| | |
| --- | --- |
| Upstream project | [element-hq/element-x-android](https://github.com/element-hq/element-x-android) |
| Stable release | `v26.11.0` |
| Tagged commit | `5d1af53a6c8dd6234cbb51e44282d5902b6d4ff3` (`Merge branch 'release/26.11.0'`) |
| Fork base | `develop` at `010fe5a25d55214a3c9c764406a67598ad4e6f5f` |

`develop` is nine commits after that tag. Those commits are the changelog for 26.11.0 plus two merged pull requests (`#8094` migration runner, `#8092` session cache directories). They are finished upstream work, so this fork keeps them instead of dropping back to the tag. The version **name** is `26.11.0-mam.1` so a MÂM Chat build is not labeled as official Element X 26.11.0. The numeric version code stays on Element's CalVer scheme (`20261100`, plus an ABI digit on split APKs; the universal APK uses digit `0`).

Minimum SDK is 24 (Android 7.0). Target and compile SDK are 37.

## Application id

`vn.info.mam.chat`

That is the reverse-DNS form of `mam.info.vn`, plus `.chat`. Each segment (`vn`, `info`, `mam`, `chat`) is a valid Java identifier, so it is a valid Android application id. Release builds do not add a suffix. Debug builds still use `.debug`, which keeps them installable next to the release build and next to official Element (`io.element.android.x`).

## Brand

- Name: **MÂM Chat**
- Logo: the supplied blue/white circular mark. Launcher and in-app art are scaled from `docs/branding/mam-icon-1024.png`. The logo is not recolored. Adaptive-icon padding is white, matching the artwork's own canvas. The status-bar notification icon is a separate single-color "M" mask, because Android tints that icon and cannot show the full-color logo there.
- UI pink, matching the web client:
  - primary `#BE185D`
  - hover `#9D174D`
  - pressed `#831843`
  - pale `#FFF1F7`
  - selection `#FCE7F3`
- Light theme text uses `#2A1520`. Dark theme keeps the same button colors and uses a lighter pink for accent text on dark surfaces.
- Onboarding shows the logo, the title "MÂM Chat", and **Sign in to chat.mam.info.vn**. "Change account provider" is still available.

Authoritative originals live in `docs/branding/`:

- `mam-logo-authoritative-original.jpg` sha256 `bb61c31704c333e74f367c3fe4a5e89499dd88730cd9bafb9366cf2e4144ca56`
- `mam-logo.png` sha256 `29a55c835b2426f9f8176ca4a12edc95ff24431bcdf5047242db4c74e14993b3`

## Sign-in

The homeserver is standalone Synapse 1.164.2:

- Password login (`m.login.password`) is the supported path. Element X already selects password login when the server does not advertise OAuth.
- There is no Matrix Authentication Service (MAS) and no OAuth. QR sign-in is hidden because it depends on that. The code remains in the tree; the onboarding button does not.
- Public registration is disabled. The app does not offer "Create account".
- No account credentials are in the source, and this build was not logged in against the live server.

## What is not configured

Do not treat these as working:

| Area | Status |
| --- | --- |
| Firebase / FCM push | **Off.** Element's `vector-alpha` Firebase project and API keys are not packaged. There is no MÂM Firebase project. |
| Pusher app id | `vn.info.mam.chat` (not Element's `im.vector.app.android`). |
| UnifiedPush | Still compiled, using the public UnifiedPush gateway from upstream. No MÂM push gateway is deployed, and delivery was not tested. |
| Calls | No custom TURN, LiveKit, or MatrixRTC backend. Element Call / native call UI is still in the app and is **not** a promise that calls connect. |
| QR / MAS login | Not available on this server. |
| Analytics | PostHog is only enabled for official Element application ids, so this id does not send data to Element's PostHog. No Sentry DSN is set in this build. |
| App Links | `app.element.io` and `mobile.element.io` are not claimed. `https://chat.mam.info.vn` App Links are not set up (that needs the future signing certificate in `assetlinks.json`). |

Matrix messaging, encryption, and the upstream license stay intact.

## Build an unsigned release APK

Requirements: JDK 21, Android SDK platform 37 (package `platforms;android-37.0`) and build-tools `37.0.0`. This environment used a symlink `platforms/android-37` → `platforms/android-37.0` because the SDK package directory is versioned.

```bash
echo "sdk.dir=$ANDROID_HOME" > local.properties
./gradlew :app:assembleGplayRelease
```

`local.properties` is gitignored. Do not commit an SDK path.

The release build type has **no signing config**. It must not be signed with the debug keystore and this repo must not contain a release keystore or password. The APK is not installable until it is signed privately.

Outputs (paths are under `app/build/outputs/`):

- Universal APK: `apk/gplay/release/app-gplay-universal-release.apk` (distribute this one)
- ABI splits: `armeabi-v7a`, `arm64-v8a`, `x86`, `x86_64`
- R8 mapping: `mapping/gplayRelease/mapping.txt`
- License report also embedded in the APK as `assets/licensee-artifacts.json`, and written by the licensee task under `app/build/reports/licensee/`

Useful checks after the build:

```bash
$ANDROID_HOME/build-tools/37.0.0/aapt dump badging app/build/outputs/apk/gplay/release/app-gplay-universal-release.apk | head
$ANDROID_HOME/build-tools/37.0.0/apksigner verify --print-certs app/build/outputs/apk/gplay/release/app-gplay-universal-release.apk
sha256sum app/build/outputs/apk/gplay/release/app-gplay-universal-release.apk
```

`apksigner verify` should fail or report that the APK is unsigned. That is expected.

Targeted tests used while branding:

```bash
./gradlew \
  :libraries:matrix:api:test \
  :features:login:impl:testDebugUnitTest \
  :features:enterprise:impl-foss:testDebugUnitTest \
  :app:ktlintCheck
```

The full upstream screenshot suite was not re-recorded. Those images still show Element and must not be published as MÂM Chat store screenshots. Fastlane phone screenshots are the upstream Element images; only the store icon and the English listing text were updated. Do not upload this listing until real MÂM screenshots exist.

## Signing and distribution (not done here)

1. Sign the universal release APK with a private keystore that is not committed.
2. Publish the signed APK and its sha256 only after that. Do not publish this unsigned file, a debug APK, or a GitHub/Play release from this change.
3. Keep `mapping.txt` with the signed build so crashes can be deobfuscated.
4. Offer the corresponding source (this git revision) with the binary. The license is the upstream dual license: AGPL-3.0-only OR Element commercial, in `LICENSE` and `LICENSE-COMMERCIAL`.
5. Runtime sign-in to `https://chat.mam.info.vn` was not exercised. An earlier automated attempt hit Cloudflare 1010; this work does not bypass that. Confirm password login on a device after signing.
6. Push, calls, and QR login still need their own backends before they can be claimed.

## License

Copyright for Element X Android remains with Element Creations Ltd and New Vector Ltd. MÂM Chat modifications are distributed under the same license terms. Do not strip upstream attribution.
