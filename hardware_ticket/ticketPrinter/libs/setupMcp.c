

bool setupMcp(Adafruit_MCP23017* mcp){

  //Reset MCP
  pinMode(16,OUTPUT);
  digitalWrite(16,HIGH);
  delay(500);
  digitalWrite(16,LOW);
  delay(100);
  digitalWrite(16,HIGH);

  //Configure MCP
  mcp->begin();      // use default address 0

  //Configure Pins
  
    //Make all pins INPUT
  mcp->writeRegister(MCP23017_IODIRA,0xFF);

  //mcp->writeRegister(MCP23017_IODIRB,0x00);

  //Make all pins PULL_UP
  mcp->writeRegister(MCP23017_GPPUA,0xFF);
  //mcp->writeRegister(MCP23017_GPPUB,0xFF);


  for(int i=0;i<5;i++){
   mcp->pinMode(Services.serviceLED[i],OUTPUT); 
   mcp->digitalWrite(Services.serviceLED[i],LOW);  
 }
  return true;
}