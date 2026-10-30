# WonderFlix Android project

This is the first Android project for WonderFlix. It keeps a dark navy/blue streaming-app look and uses Android Media3 (ExoPlayer) for native MP4 playback.

## Important
- This is **source code**, not an APK installer yet.
- The Luca tile is a catalogue placeholder. A licensed stream or video file is required before Luca can play.
- The included Big Buck Bunny URL is a public Blender demo-film URL used only to test playback. If that host is unavailable, replace it with a direct MP4 URL for a film you own or are authorised to stream.
- A successful build does not guarantee that every streaming provider permits playback in every region or app.

## Build an APK using GitHub Actions (tablet-friendly, requires a GitHub account)
1. Extract this ZIP on your tablet.
2. In a browser, create a new empty repository on GitHub.
3. Upload the contents of this folder to the repository, keeping the `.github/workflows/build-apk.yml` file and folder structure.
4. Open the repository's **Actions** tab and choose **Build WonderFlix APK**. Press **Run workflow** (or upload/push to `main` to trigger it).
5. Wait for the workflow to finish successfully.
6. Open the completed workflow run, find **Artifacts**, and download `WonderFlix-debug-APK`.
7. Extract the artifact ZIP to find `app-debug.apk`. Android may ask you to allow installation from your browser/files app. Only install APKs you built and trust.

## Build requirements
- Android Gradle Plugin 8.7.3
- Gradle 8.9
- JDK 17
- Android SDK platform 35

## Playback
The app uses AndroidX Media3 ExoPlayer, not an embedded browser video tag. The player includes native play/pause, seek, and volume controls supplied by Android. Full-screen support can be added as a next development step.
