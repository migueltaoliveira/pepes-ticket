#include <Wire.h>
#include "Adafruit_MCP23017.h"

// Basic pin reading and pullup test for the MCP23017 I/O expander
// public domain!

// Connect pin #12 of the expander to Analog 5 (i2c clock)
// Connect pin #13 of the expander to Analog 4 (i2c data)
// Connect pins #15, 16 and 17 of the expander to ground (address selection)
// Connect pin #9 of the expander to 5V (power)
// Connect pin #10 of the expander to ground (common ground)
// Connect pin #18 through a ~10kohm resistor to 5V (reset pin, active low)

// Input #0 is on pin 21 so connect a button or switch from there to ground

Adafruit_MCP23017 mcp;
  
void setup() {  
  
 // initMCP();
  Serial.begin(115200);
  Serial.println("\n");
  mcp.begin();      // use default address 0
  delay(100);
  
  
 

  Serial.print("pinDir: ");
  uint8_t regVal = mcp.readRegister(MCP23017_IODIRA);
  Serial.println(regVal,BIN);
  

  
  delay(300);
  Serial.print("pullUP: ");
  regVal = mcp.readRegister(MCP23017_GPPUA);
  Serial.println(regVal,BIN);
  
  
  //for (int i=0;i<7;i++){
  //mcp.pinMode(i, INPUT);
  //mcp.pullUp(i, HIGH);  // turn on a 100K pullup internally
  //}
  //mcp.pinMode(1, OUTPUT);  // use the p13 LED as debugging

 
}


void loop() {
  // The LED will 'echo' the button

// 
//
//  
//  //Serial.println(a,BIN);
//  for (int i=0;i<7;i++){
//    bool raw=mcp.digitalRead(0);
//    
//    if ((millis()-T) > timer){
//      a=debouceButtons(raw);
//      T=millis();
//    }
    
//  //mcp.digitalWrite(1, !a);
  //  Serial.print("raw: ");Serial.print(raw);Serial.print("\t");
   // Serial.print("debounce: "); Serial.print(a);Serial.print("\t");

//    //delay(0);
//  }

  uint16_t gpioVal=mcp.readGPIOAB();
  Serial.println(gpioVal,BIN);
  delay(100);
  clearTerminal();
  //Serial.print("Number: ");Serial.println((sizeof(uint16_t)*2)-1);
  //delay(100);
  //clearTerminal();
  yield();
  
}

//bool debouceButtons(bool raw){
//State=(State<<1) | raw | 0xE000;
//if(State==0xFF00)return true;
//return false; 
//  }


void clearTerminal(){
  //while(Serial.available()>0) Serial.read();
    Serial.flush();
    Serial.write(27); // ESC
    Serial.print("[2J"); // clear screen
    Serial.write(27); // ESC
    Serial.print("[H"); // cursor to home
}

void initMCP(){
  pinMode(16,OUTPUT);
  digitalWrite(16,HIGH);
  delay(500);
  digitalWrite(16,LOW);
  delay(100);
  digitalWrite(16,HIGH);
  mcp.begin();      // use default address 0
  //Make all pins INPUT
  mcp.writeRegister(MCP23017_IODIRA,0xFF);
  mcp.writeRegister(MCP23017_IODIRB,0xFF);

  //Make all pins PULL_UP
  mcp.writeRegister(MCP23017_GPPUA,0xFF);
  mcp.writeRegister(MCP23017_GPPUB,0xFF);
}


