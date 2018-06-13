#!/bin/bash

ROOT_UID=0
JAVA_VERSION=1.8
RXTX_SOFTLINK_LOCATION=/usr/lib/jni

# check for root rights
if [ $UID != $ROOT_UID ]; then
    echo "You don't have sufficient privileges to run this script."
    exit 1
fi


# check if java is installed
if type -p java; then
    _java=java
elif [[ -n "$JAVA_HOME" ]] && [[ -x "$JAVA_HOME/bin/java" ]];  then     
    _java="$JAVA_HOME/bin/java"
else
    echo "No Java version found in PATH or JAVA_HOME. Pleas install at least Java version $JAVA_VERSION or higher. If you have already installed Java please link it to PATH and JAVA_HOME."
    exit
fi

if [[ "$_java" ]]; then
    version=$("$_java" -version 2>&1 | awk -F '"' '/version/ {print $2}')
    echo "Java version $version found."
    if [[ "$version" < "$JAVA_VERSION" ]]; then
        echo "A higher Java version is needed, please install at least Java version $JAVA_VERSION or higher."
        exit 
    fi
fi


# check if RXTXcomm Library is installed
java=$(readlink -f $(which $_java))
JAVA_HOME=${java%\/bin\/java}
rxtxVersion=0
rxtxMatchingSoftlinks=0

for rxtx in $JAVA_HOME/jre/lib/ext/RXTXcomm*.jar; do
    rxtxVersion=$(echo $rxtx | sed 's/.*RXTXcomm\(.*\)\.jar/\1/')
    if [[ -f $RXTX_SOFTLINK_LOCATION/librxtxParallel$rxtxVersion.so ]] && [[ -f $RXTX_SOFTLINK_LOCATION/librxtxSerial$rxtxVersion.so ]]; then
	rxtxMatchingSoftlinks=1
        break
    fi
done

install_RXTXcomm(){
    cp ./util/rxtx/RXTXcomm.jar $JAVA_HOME/jre/lib/ext/

    if [[ $(arch) == "x86_64" ]]; then
	cp ./util/rxtx/x86_64/* /usr/lib/jni/
    else
        cp cp ./util/rxtx/x86/* /usr/lib/jni/
    fi
}

if [[ $rxtxVersion != 0 ]] && [[ $rxtxMatchingSoftlinks != 0 ]]; then
    echo "Found RXTXcomm$rxtxVersion Library."
elif [[ $rxtxVersion != 0 ]] && [[ $rxtxMatchingSoftlinks == 0 ]]; then
    echo "Found RXTXcomm$rxtxVersion Library,  but no matching softlinks in /usr/lib/jni!"
    echo "Do you wish to install the included Library?"
    select yn in "Yes" "No"; do
        case $yn in
            Yes ) install_RXTXcomm; break;;
            No ) echo "Please save librxtxParallel$rxtxVersion.so and librxtxSerial$rxtxVersion.so to the folder /usr/lib/jni and restart this program.";exit;;
        esac
    done
else
    echo "Installing RXTXcomm Library."
    install_RXTXcomm
fi

# change usb device name of Nordic ID Sampo S1 reader
if [[ -f /etc/udev/rules.d/99-usb-serial.rules ]]; then
    while read -r newUsbRule; do
        alreadyImplementedRule=0
        while read -r alreadySavedUsbRule; do
	    if [[ $alreadySavedUsbRule == $newUsbRule ]]
	        alreadyImplementedRule=1
	    fi
	done < "/etc/udev/rules.d/99-usb-serial.rules"
        if [[ $alreadyImplementedRule == 0 ]]
	    echo $newUsbRule >> /etc/udev/rules.d/99-usb-serial.rules
        fi
    done < "./util/99-usb-serial.rules"
else
    cp -f ./util/99-usb-serial.rules /etc/udev/rules.d/99-usb-serial.rules
fi

udevadm trigger

# get latest release from github
./util/autofetcher.sh

