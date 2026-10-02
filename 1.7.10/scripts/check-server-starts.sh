#!/usr/bin/env bash
# Starts a dedicated Forge server with the built mod jar, waits until it has loaded every
# mod and generated the spawn area, then stops it. Fails if the server crashes, a mod throws
# while loading, or it does not finish starting in time. Used by the GitHub Actions build.
set -u

LOG=${LOG:-build/server-check.log}
TIMEOUT_SECONDS=${TIMEOUT_SECONDS:-900}
mkdir -p "$(dirname "$LOG")"
: > "$LOG"

# Lines that mean the server did not start cleanly.
FAILURE_PATTERN='---- Minecraft Crash Report ----|Caught exception from|Encountered an unexpected exception|FML has detected|There was a critical exception|Exception in server tick loop'

# The run task asks about online mode and the Minecraft EULA unless these files already exist.
SERVER_DIR=${SERVER_DIR:-run/server}
mkdir -p "$SERVER_DIR"
[ -f "$SERVER_DIR/server.properties" ] || echo "online-mode=false" > "$SERVER_DIR/server.properties"
echo "eula=true" > "$SERVER_DIR/eula.txt"
# A fresh world each time, so world generation (diorite, dye trees, flowers) is exercised too.
rm -rf "$SERVER_DIR/world"

server_input() {
    local waited=0
    until grep -qE 'Done \([0-9.]+s\)!|BUILD (FAILED|SUCCESSFUL)' "$LOG" || [ "$waited" -ge "$TIMEOUT_SECONDS" ]; do
        sleep 2
        waited=$((waited + 2))
    done
    # Ask a running server to shut down cleanly.
    echo stop
    sleep 60
}

# Build the release (reobfuscated) jar and put it where this server looks for mods, so the check
# runs the same jar players download. It is removed again afterwards.
./gradlew --no-daemon -q prepareObfModsFolder > /dev/null
mkdir -p "$SERVER_DIR/mods"
JAR=$(ls -t run/obfuscated/mods/*.jar | head -1)
cp "$JAR" "$SERVER_DIR/mods/"
trap 'rm -f "$SERVER_DIR/mods/$(basename "$JAR")"' EXIT

server_input | ./gradlew --no-daemon runObfServer > "$LOG" 2>&1
status=$?

if grep -qE -e "$FAILURE_PATTERN" "$LOG"; then
    echo "The server reported an error while starting:"
    grep -nE -A 20 -e "$FAILURE_PATTERN" "$LOG" | head -80
    exit 1
fi
if ! grep -q 'Post Initialization Complete' "$LOG"; then
    echo "More Dyes did not finish loading (no 'Post Initialization Complete' in the log). Last lines:"
    tail -60 "$LOG"
    exit 1
fi
if ! grep -qE 'Done \([0-9.]+s\)!' "$LOG"; then
    echo "The server did not finish starting (Gradle exit code $status). Last lines of the log:"
    tail -60 "$LOG"
    exit 1
fi
echo "The server started with $(basename "$JAR") and shut down cleanly:"
grep -E 'Forge Mod Loader has successfully loaded' "$LOG"
grep -E 'Done \([0-9.]+s\)!' "$LOG"
