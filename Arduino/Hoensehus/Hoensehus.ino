#include <SPI.h>
#include <Ethernet.h>
#include <DHT.h>

#define dhtInsidePin 2
#define dhtOutsidePin 3

#define waterTankOutput 43
#define topTankSensor A14
#define middleTankSensor A13
#define deepestTankSensor A15

DHT dhtinside (dhtInsidePin, DHT22);
DHT dhtoutside (dhtOutsidePin, DHT22);

byte mac[] = {
  0x42, 0x39, 0x55, 0x75, 0x8E, 0xB1
};



EthernetServer server(80);
void setup() {

  pinMode(waterTankOutput,OUTPUT);
  pinMode(deepestTankSensor, INPUT); 
  pinMode(middleTankSensor, INPUT); 
  pinMode(topTankSensor, INPUT); 

  Ethernet.init(10);
  
  // put your setup code here, to run once:
   Serial.begin(9600);
   //while (!Serial) {


    //; // wait for serial port to connect. Needed for native USB port only


  //}
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

// get both data from the sensor and save it into the given array.
void getDHTTemperatureAndHumidity(float (& data)[2],  DHT dht){
  data[0] = dht.readTemperature();
  data[1] = dht.readHumidity();
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
        
        if(endpoint == "/dht"){
          
          client.println("HTTP/1.1 200 OK");
          client.println("Content-Type: application/json");
          client.println("Connection: close");
          client.println();
          String response = "[";
          
          response.concat("{\"location\": \"INSIDE\", \"temperature\":");
          float data[2]; 
          getDHTTemperatureAndHumidity(data,dhtinside); 
          response.concat(data[0]);
          response.concat(", \"humidity\": ");
          response.concat(data[1]);
          response.concat("");
          response.concat("},");
          response.concat("{\"location\": \"OUTSIDE\", \"temperature\":");
           
          getDHTTemperatureAndHumidity(data,dhtoutside); 
          response.concat(data[0]);
          response.concat(", \"humidity\": ");
          response.concat(data[1]);
          response.concat("");
          
          response.concat("}]");
          client.println(response);
          break; 
        }
        
        else if(endpoint == "/tank"){
          client.println("HTTP/1.1 200 OK");
          client.println("Content-Type: application/json");
          client.println("Connection: close");
          client.println();

          
          int data[2];

          digitalWrite(waterTankOutput,HIGH);
          delay(10);
                    
          if(analogRead(topTankSensor) > 0){
            data[0] = 95;
            data[1] = 100;
          }
          else if(analogRead(middleTankSensor) > 0){
            data[0] = 60;
            data[1] = 95;
          }
          else if(analogRead(deepestTankSensor) > 0){
            data[0] = 10;
            data[1] = 60;
          }
          else {
            data[0] = 0;
            data[1] = 10;
          }

          String response = "{\"min\": ";
          response.concat(data[0]);
          response.concat(",\"max\": ");
          response.concat(data[1]);
          response.concat("}");
          
          delay(10);
          digitalWrite(waterTankOutput,LOW);
          
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

//Decodes the path and returns the result.
String DecodeEndpointPath(String requestLine){
  if(requestLine.startsWith("GET")){
    int index = requestLine.indexOf(" ");
    int indexNext = requestLine.indexOf(" ",index +1);
    String subString = requestLine.substring(index+1, indexNext );
    return subString;
    
  }
  return "";
}
