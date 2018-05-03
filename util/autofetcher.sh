#!/bin/bash

REPO_URL='https://github.com/keinproblem/rfiddetection'
ARTIFACT_NAME='rfid-detection-0.0.1-jar-with-dependencies.jar'
LATEST_RELEASE=$(curl -L -s -H 'Accept: application/json' $REPO_URL/releases/latest)
LATEST_VERSION=$(echo $LATEST_RELEASE | sed -e 's/.*"tag_name":"\([^"]*\)".*/\1/')
ARTIFACT_URL="$REPO_URL/releases/download/$LATEST_VERSION/$ARTIFACT_NAME"
curl -o $ARTIFACT_NAME -L $ARTIFACT_URL
