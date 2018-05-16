#!/bin/bash

ROOT_UID=0

if [ $UID != $ROOT_UID ]; then
    echo "You don't have sufficient privileges to run this script."
    exit 1
fi

# get latest release from github
./util/autofetcher.sh
# change usb device name of Nordic ID Sampo S1
cp -f ./util/99-usb-serial.rules /etc/udev/rules.d/99-usb-serial.rules
udevadm trigger
