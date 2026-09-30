#!/usr/bin/env bash
# Starts a dedicated Forge server with the mod (runServer), waits until it has loaded every mod
# and generated the spawn area, then stops it. Fails if the server crashes, a mod throws while
# loading, a recipe or loot table fails to parse, or it does not finish starting in time.
# Used by the GitHub Actions build.
set -u

LOG=${LOG:-build/server-check.log}
TIMEOUT_SECONDS=${TIMEOUT_SECONDS:-900}
mkdir -p "$(dirname "$LOG")"
: > "$LOG"

# Lines that mean the server did not start cleanly.
FAILURE_PATTERN='---- Minecraft Crash Report ----|Exception caught during firing event|Failed to create mod instance|Encountered an unexpected exception|Exception in server tick loop|Parsing error loading|Couldn.t parse (data file|loot table|element)|Couldn.t load (tag|loot table)'

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
pkill -f '[L]aunchTesting' || true
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
