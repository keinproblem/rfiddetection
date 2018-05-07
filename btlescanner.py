import threading
import time
from bluepy.btle import Scanner, DefaultDelegate
#
# Thread, der nach BTLE Geraeten scannt. Der Algorithmus arbeitet in einem Thread, aus dem vom Klienten
# zyklisch die aktuelle Liste der sichbaren Mac-Adressen erfragt werden kann.
#
# Die MAC Adressen werden als Array von Dictionaries bereitgestellt. Eine Suche nach einer MAC Adresse ist damit
# schnell und effizient
#
# "<MAC>" : {
#           addr = "<MAC>",
#           rssi = <Signalstaerke> in DB
#           <weitere Informationen>
#           }
#
# Installation fuer Raspberry PI Stretch:
#
# sudo apt-get update
# sudo apt-get upgrade
# sudo rpi-update
# sudo apt-get install python3-pip python3-dev python3-dbus libglib2.0-dev
# sudo apt-get install bluetooth libbluetooth-dev
# sudo pip3 install bluepy
#
#
# Die Datenstruktur ist fuer spaetere Versionen erweiterbar.
#
class BTLE_Scanner(threading.Thread):
    #
    # Dictionary der ermittelten MAC Adressen
    #
    mac_adressen = {}
    #
    # Scanner Objekt
    #
    scanner = None
    #
    # Timeout in Sekunden fuer den Geraetescan
    #
    timeout = None
    #
    # Wartezeit in Sekunden fuer die Endlosschleife
    #
    waiting_period = None

    #
    # Konstruktor des Threads
    #
    def __init__(self, timeout=5.0, waiting_period=6.0, *args, **kwargs):
        #
        # unbedingt Konstruktor der Oberklasse starten!
        #
        threading.Thread.__init__(self)
        #
        # initialisiere den BTLE Scanner
        #
        self.timeout = timeout
        self.waiting_period = waiting_period
        self.scanner = Scanner()

    #
    # Liste der MAC-Adressen der Tags
    #
    def tags(self):
        return self.mac_adressen.keys()


    #
    # Endlosschleife des Scanners
    #
    def run(self):
        while True:
            try:
                #
                # Scanne mit Timeout nach Geraeten, im passiv-Modus
                #
                devices = self.scanner.scan(timeout=self.timeout, passive=True)
                #
                # Erzeuge temporaere Liste
                #
                mac_adressen = {}
                #
                # Durchlaufe den Scan: speichere MAC Adresse (addr), Sendeleistung in DB (rssi) und alle weiteren
                # verfuegbaren Informationen
                #
                for dev in devices:
#                    print("SCAN: ", str(dev.addr))
                    #
                    # Adresse, Sendeleistung in DB
                    #
                    info = {
                        "addr": str(dev.addr),
                        "rssi": int(dev.rssi)
                    }
                    #
                    # Speichere alle Zusatzinformationen
                    #
                    for (adtype, desc, value) in dev.getScanData():
                        info[desc] = value
                    #
                    # Speichere das Geraet in der Liste
                    #
                    mac_adressen[info["addr"]] = info
                #
                # Tausche die Liste der MAC-Addressen aus
                #
                self.mac_adressen = mac_adressen
                #
                # Warte auf die naechste Runde
                #
                time.sleep(self.waiting_period)
            except Exception as e:
                print("Error: ", str(e))
                time.sleep(self.waiting_period)

