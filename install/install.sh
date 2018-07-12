#!/bin/bash

ROOT_UID=0
JAVA_VERSION=1.8
RXTX_FOLDER=./util/rxtx
RXTX_SHARED_OBJECT_LOCATION=/usr/lib/jni/
INSTALL_PATH=/opt/RFID_detection/
LOCAL_INSTALL_FILE=rfid-detection-0.0.1-jar-with-dependencies.jar

#online repo
REPO_URL='https://github.com/keinproblem/rfiddetection'
ARTIFACT_NAME='rfid-detection-0.0.1-jar-with-dependencies.jar'

#check for correct inputs
if  [ "$#" != "0" ] && [ "$1" != "-o" ]; then
    echo "Unsupported input parameter!"
    echo "Allowed parameter:"
    echo "-o to use the JAR inside bin, othwise it will download the latest from git"
    echo "e.g. ./install.sh -o"
    exit
fi

#check for correct inputs
if  [ "$#" != "0" ] && [ "$1" != "-o" ]; then
    echo $1
    exit
fi

# check for root rights
if [ ${UID} != ${ROOT_UID} ]; then
    echo "You don't have sufficient privileges to run this script."
    exit 1
fi


# check if system is supported
unameOut="$(uname -s)"
case "${unameOut}" in
    Linux*)     machine=Linux;;
    Darwin*)    machine=Mac;;
    *)          echo "${unameOut} is an unsupported kernel"; exit
esac


# check if java is installed
if type -p java; then
    _java=java
elif [[ -n "${JAVA_HOME}" ]] && [[ -x "${JAVA_HOME}/bin/java" ]];  then     
    _java="${JAVA_HOME}/bin/java"
else
    echo "No Java version found in PATH or JAVA_HOME. Pleas install at least Java version ${JAVA_VERSION} or higher. If you have already installed Java please link it to PATH and JAVA_HOME."
    exit
fi

if [[ "${_java}" ]]; then
    version=$("$_java" -version 2>&1 | awk -F '"' '/version/ {print $2}')
    echo "Java version $version found."
    if [[ "${version}" < "${JAVA_VERSION}" ]]; then
        echo "A higher Java version is needed, please install at least Java version ${JAVA_VERSION} or higher."
        exit 
    fi
fi


# check if RXTXcomm Library is installed
case "${machine}" in
    Linux)
	txrxParallelSo=$(ls ${RXTX_SHARED_OBJECT_LOCATION}/librxtxParallel*.so 2> /dev/null | wc -l)
        txrxSerialSo=$(ls ${RXTX_SHARED_OBJECT_LOCATION}/librxtxSerial*.so 2> /dev/null | wc -l)

	if [[ $(uname -m) == "x86_64" ]]; then
	    rxtxLibraryFolder=${RXTX_FOLDER}/x86_64
    	else
            rxtxLibraryFolder=${RXTX_FOLDER}/x86
	    if [ "${txrxParallelSo}" == 0 ]; then
   	        cp ${rxtxLibraryFolder}/librxtxParallel.so ${RXTX_SHARED_OBJECT_LOCATION}
	    fi
    	fi

	if [ "${txrxSerialSo}" != 0 ]; then
	    cp ${rxtxLibraryFolder}/librxtxSerial.so ${RXTX_SHARED_OBJECT_LOCATION}
	fi
	;;

    Mac)
	txrxSerialSo=$(ls ${RXTX_SHARED_OBJECT_LOCATION}/librxtxSerial*.jnilib 2> /dev/null | wc -l)
	rxtxLibraryFolder=${RXTX_FOLDER}/mac

	if [ "${txrxSerialSo}" != 0 ]; then
	    cp ${rxtxLibraryFolder}/librxtxSerial.jnilib ${RXTX_SHARED_OBJECT_LOCATION}
	fi
	;;

    *) echo "${machine} isn't supported for RXTX Library installation!";;
esac


# change usb device name of Nordic ID Sampo S1 reader
if [[ -f /etc/udev/rules.d/99-usb-serial.rules ]]; then
    while read -r newUsbRule; do
        alreadyImplementedRule=0
        while read -r alreadySavedUsbRule; do
	    if [[ $alreadySavedUsbRule == $newUsbRule ]]; then
	        alreadyImplementedRule=1
	    fi
	done < "/etc/udev/rules.d/99-usb-serial.rules"
        if [[ $alreadyImplementedRule == 0 ]]; then
	    echo $newUsbRule >> /etc/udev/rules.d/99-usb-serial.rules
        fi
    done < "./util/99-usb-serial.rules"
else
    cp -f ./util/99-usb-serial.rules /etc/udev/rules.d/99-usb-serial.rules
fi

udevadm trigger

# install jar to INSTALL_PATH
if [ ! -d $INSTALL_PATH ]; then
    mkdir $INSTALL_PATH
fi

if [ "$1" == "-o" ]; then
    yes | cp -rf ./bin/$LOCAL_INSTALL_FILE $INSTALL_PATH
else
    installDir=$(pwd)
    cd $INSTALL_PATH
    latestRelease=$(curl -L -s -H 'Accept: application/json' $REPO_URL/releases/latest)
    latestVersion=$(echo $latestRelease | sed -e 's/.*"tag_name":"\([^"]*\)".*/\1/')
    artifactUrl="$REPO_URL/releases/download/$latestVersion/$ARTIFACT_NAME"
    curl -o $ARTIFACT_NAME -L $artifactUrl
    cd $installDir
fi

#copy start file
yes | cp -rf ./util/start_RFID_detection.sh $INSTALL_PATH

