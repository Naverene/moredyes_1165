#!/usr/bin/env bash
# Starts a dedicated Forge server with the mod (runServer), waits until it has loaded every mod
# and generated the spawn area, then stops it. Fails if the server crashes, a mod throws while
# loading, or it does not finish starting in time.
# Used by the GitHub Actions build.
set -u

LOG=${LOG:-build/server-check.log}
TIMEOUT_SECONDS=${TIMEOUT_SECONDS:-900}
mkdir -p "$(dirname "$LOG")"
: > "$LOG"

# Lines that mean the server did not start cleanly.
FAILURE_PATTERN='---- Minecraft Crash Report ----|LoaderExceptionModCrash|Encountered an unexpected exception|Exception in server tick loop|Fatal errors were detected|Parsing error loading recipe'

# The server asks for the Minecraft EULA unless it is already accepted. A fresh world each time,
# so world generation (dye trees and tulips) is exercised too.
mkdir -p run
echo "eula=true" > run/eula.txt
[ -f run/server.properties ] || echo "online-mode=false" > run/server.properties
rm -rf run/world

# On 1.12.2 the server crashes while Forge loads unless ForgeGradle has decompiled Minecraft, which
# it only does when something asks for the sources. The eclipse task does (importing the project
# into an IDE has the same effect).
if ! ./gradlew --no-daemon eclipse > "$LOG" 2>&1; then
    echo "Could not prepare the Minecraft sources. Last lines of the log:"
    tail -60 "$LOG"
    exit 1
fi

# runServer does not read commands from stdin under Gradle, so the server is stopped by ending
# its process once it has started.
./gradlew --no-daemon runServer > "$LOG" 2>&1 &
gradle_pid=$!
waited=0
until grep -qE 'Done \([0-9.]+s\)!|BUILD (FAILED|SUCCESSFUL)' "$LOG" || [ "$waited" -ge "$TIMEOUT_SECONDS" ]; do
    sleep 2
    waited=$((waited + 2))
done
pkill -f '[l]egacydev\.MainServer' || true
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
