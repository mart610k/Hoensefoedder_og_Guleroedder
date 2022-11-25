#include <SPI.h>
#include <Ethernet.h>

byte mac[] = {
  0x42, 0x39, 0x55, 0x75, 0x8E, 0xB1
};


void setup() {

Ethernet.init(10);
  
  // put your setup code here, to run once:
   Serial.begin(9600);
   while (!Serial) {


    ; // wait for serial port to connect. Needed for native USB port only


  }
Serial.println("Initialize Ethernet with DHCP:");


  if (Ethernet.begin(mac) == 0) {


    Serial.println("Failed to configure Ethernet using DHCP");


    if (Ethernet.hardwareStatus() == EthernetNoHardware) {


      Serial.println("Ethernet shield was not found.  Sorry, can't run without hardware. :(");


    } else if (Ethernet.linkStatus() == LinkOFF) {


      Serial.println("Ethernet cable is not connected.");


    }


    // no point in carrying on, so do nothing forevermore:


    while (true) {


      delay(1);


    }


  }


  // print your local IP address:


  Serial.print("My IP address: ");


  Serial.println(Ethernet.localIP());
}

void loop() {
  // put your main code here, to run repeatedly:

}
