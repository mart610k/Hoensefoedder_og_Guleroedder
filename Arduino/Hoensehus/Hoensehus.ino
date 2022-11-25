#include <SPI.h>
#include <Ethernet.h>
#include <DHT.h>

#define dhtInsidePin 2
#define dhtOutsidePin 3
DHT dhtinside (dhtInsidePin, DHT22);
DHT dhtoutside (dhtOutsidePin, DHT22);


byte mac[] = {
  0x42, 0x39, 0x55, 0x75, 0x8E, 0xB1
};



EthernetServer server(80);
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

  dhtinside.begin();
  dhtoutside.begin();
}

float getDHTTemperature(DHT dht){
  return dht.readTemperature();
}



void loop() {
  // put your main code here, to run repeatedly:

  EthernetClient client = server.available(); // try to get client
  if (client) { // got client?
    boolean currentLineIsBlank = true;
    String input = String();
    String endpoint = "";
    while (client.connected()) {
      if (client.available()) { // client data available to read
      char c = client.read(); // read 1 byte (character) from client
      

      input = input + c;
      
      // last line of client request is blank and ends with \n
      // respond to client only after last line received
      if (c == '\n' && currentLineIsBlank) {
        Serial.println(endpoint);
        
        if(endpoint == "/tempreature"){
          
         client.println("HTTP/1.1 200 OK");
        client.println("Content-Type: application/json");
        client.println("Connection: close");
        client.println();
        String response = "[";
        
        response.concat("{\"location\": \"INSIDE\", \"value\":"); 
        response.concat(getDHTTemperature(dhtinside));
        response.concat("},");
        response.concat("{\"location\": \"OUTSIDE\", \"value\":"); 
        response.concat(getDHTTemperature(dhtoutside));
        response.concat("}]");
        client.println(response);
         break; 
        }
        else{
          
        
        // send a standard http response header
        client.println("HTTP/1.1 200 OK");
        client.println("Content-Type: text/html");
        client.println("Connection: close");
        client.println();
        // send web page
        client.println("<!DOCTYPE html>");
        client.println("<html>");
        client.println("<head>");
        client.println("<title>Arduino Web Page</title>");
        client.println("</head>");
        client.println("<body>");
        client.println("<h1>Hello from Arduino!</h1>");
        client.println("<p>A web page from the Arduino server</p>");
        client.println("</body>");
        client.println("</html>");
        break;
        }
      }
      // every line of text received from the client ends with \r\n
      if (c == '\n') {
        // last character on line of received text
        // starting new line with next character read
        currentLineIsBlank = true;
        String temp = DecodeEndpointPath(input);
        if(temp != ""){
          endpoint = temp;
        }
        
        input = "";

        
      }
      else if (c != '\r') {
        // a text character was received from client
        currentLineIsBlank = false;
      }
    } // end if (client.available())
  } // end while (client.connected())
  delay(1); // give the web browser time to receive the data
  client.stop(); // close the connection
  } // end if (client
}


String DecodeEndpointPath(String requestLine){
  if(requestLine.startsWith("GET")){
    int index = requestLine.indexOf(" ");
    int indexNext = requestLine.indexOf(" ",index +1);
    String subString = requestLine.substring(index+1, indexNext );
    return subString;
    
  }
  return "";
}
