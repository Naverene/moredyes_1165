#!/usr/bin/env bash
# Starts a dedicated NeoForge server with the mod (runServer) on a fresh world, waits until it has
# loaded and generated the spawn area, then stops it. Fails if the server crashes or logs an error
# while starting, such as a mixin that does not apply or a recipe, tag, loot table or worldgen file
# that does not load. Used by the GitHub Actions build.
set -u

LOG=${LOG:-build/server-check.log}
TIMEOUT_SECONDS=${TIMEOUT_SECONDS:-900}
mkdir -p "$(dirname "$LOG")"
: > "$LOG"

# Lines that mean the server did not start cleanly.
FAILURE_PATTERN='---- Minecraft Crash Report ----|Failed to start the minecraft server|/ERROR\]'

# The server asks for the Minecraft EULA unless it is already accepted. A fresh world each time,
# so world generation (dye trees and tulips) is exercised too.
mkdir -p run
echo "eula=true" > run/eula.txt
[ -f run/server.properties ] || echo "online-mode=false" > run/server.properties
rm -rf run/world

# runServer does not read commands from stdin under Gradle, so the server is stopped by ending
# its process once it has started.
./gradlew --no-daemon runServer > "$LOG" 2>&1 &
gradle_pid=$!
waited=0
until grep -qE 'Done \([0-9.]+s\)!|BUILD (FAILED|SUCCESSFUL)' "$LOG" || [ "$waited" -ge "$TIMEOUT_SECONDS" ]; do
    sleep 2
    waited=$((waited + 2))
done
pkill -f '[n]et.neoforged.devlaunch.Main' || true
wait "$gradle_pid"

if grep -qE -e "$FAILURE_PATTERN" "$LOG"; then
    echo "The server reported an error while starting:"
    grep -nE -A 20 -e "$FAILURE_PATTERN" "$LOG" | head -80
    exit 1
fi
if ! grep -qE 'Done \([0-9.]+s\)!' "$LOG"; then
    echo "The server did not finish starting within $TIMEOUT_SECONDS seconds. Last lines of the log:"
    tail -60 "$LOG"
    exit 1
fi
echo "The server started with More Dyes loaded:"
grep -E 'Done \([0-9.]+s\)!' "$LOG"
