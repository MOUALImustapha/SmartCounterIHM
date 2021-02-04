#include <SPIFFSReadServer.h>
#include <ESP8266WiFi.h>
#include <ESP8266HTTPClient.h>
#include <FS.h>
#ifndef STASSID
#define STASSID "inwi Home 4G"
#define STAPSK  "11021998mouali"
#endif


//====================================================================================
//                 Print a SPIFFS directory list (root directory)
//====================================================================================
void listFiles(void) {
  Serial.println("SPIFFS files found:");
  fs::Dir dir = SPIFFS.openDir("/"); // Root directory
  String  line = "=====================================";
  Serial.println(line);
  Serial.println("  File name               Size");
  Serial.println(line);

  while (dir.next()) {
    String fileName = dir.fileName();
    Serial.print(fileName);
    int spaces = 25 - fileName.length();
    while (spaces--) Serial.print(" ");
    fs::File f = dir.openFile("r");
    Serial.print(f.size()); Serial.println(" bytes");    }
    
  Serial.println(line);
  Serial.println();
  delay(1000);
}
//====================================================================================



void setup() {
  Serial.begin(115200);
  WiFi.begin(STASSID, STAPSK);

  while (WiFi.status() != WL_CONNECTED) {
    delay(500);
    Serial.print(".");    }
    
  Serial.print("Connected! IP address: ");
  Serial.println(WiFi.localIP());

  if (!SPIFFS.begin()) {
    Serial.println("SPIFFS initialisation failed!");
    while (1) yield(); 
  }
  Serial.println("\r\n SPIFFS Initialisation done."); 
  // Lists the files so you can see what is in the SPIFFS
  listFiles();
}

void loop() {
  // wait for WiFi connection
  if ((WiFi.status() == WL_CONNECTED)) {
       WiFiClient client;
       HTTPClient http;
      // Open the named file
      fs::Dir dir = SPIFFS.openDir("/"); // Root directory
      const char *filename = "/capture4.jpg";
      fs::File jpgFile = SPIFFS.open( filename, "r");    // File handle reference for SPIFFS
    
      if ( !jpgFile ) {
        Serial.print("ERROR: File \""); Serial.print(filename); Serial.println ("\" not found!");
        delay(10000);
        return; }
    
      uint8_t data;
      byte line_len = 0;
      String Data="";
      while ( jpgFile.available()) {
            data = jpgFile.read();
            Serial.print(data, HEX); Serial.print(",");// Add value and comma
            Data += data + "," ;
            line_len++;
            if ( line_len >= 32) {
              line_len = 0;
              Serial.println("");
            }    }
      Serial.println("};\r\n");
      jpgFile.close();
      //  --------------------------------------------------------
    
        Serial.print("[HTTP] begin...\n");
        
        http.begin(client, "http://projetindustriel.000webhostapp.com/post-esp-data.php"); //HTTP
        http.addHeader("Content-Type", "multipart/form-data");
        Serial.print("[HTTP] POST...\n");
        // start connection and send HTTP header and body
        String request ="api_key=hhTT66KKbbLLoo88&sensor=Camera&location=FBS&value=24.3&photo=" +  Data;
        request +="";
        int httpCode = http.POST(request);
    
        // httpCode will be negative on error
        if (httpCode > 0) {
          // HTTP header has been send and Server response header has been handled
          Serial.printf("[HTTP] POST... code: %d\n", httpCode);
    
          // file found at server
          if (httpCode == HTTP_CODE_OK) {
            const String& payload = http.getString();
            Serial.println("received payload:\n<<");
            Serial.println(payload);
            Serial.println(">>");
          }
        } else {
          Serial.printf("[HTTP] POST... failed, error: %s\n", http.errorToString(httpCode).c_str());
        }
        http.end(); }
  delay(10000);}
