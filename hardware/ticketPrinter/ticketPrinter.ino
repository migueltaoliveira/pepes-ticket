    
    
    //General ESP8266 Stuff
    #include <Arduino.h>
    extern "C"
    {
    #include "user_interface.h"
    }

   
    //WIFI functions
    #include <ESP8266WiFi.h>
    #include <WiFiClient.h>
    #include "src/httpVars.c"
    #include "src/connectAP.c"
    #include "src/sendRequest.c"
    #include "src/composeRequest.c"
    unsigned long T0;

    WiFiClient client;
    struct accessPoint AP;
    struct httpResp resp;
    struct httpReq req;
    
    
    
    //Button Functions
    #include <Wire.h>
    #include "Adafruit_MCP23017.h"
    Adafruit_MCP23017 mcp;
    bool refreshBTN;
    os_timer_t BTN_Timer;
    uint16_t buttonStatesRaw[16];
    uint16_t buttonStates=0;
    
    #include "src/btnTimerInit.c"
    #include "src/btnNames.c"
     
    #include "src/setupMcp.c"
    #include "src/debounceButtons.c"



    
    //State Machine (SM) Functions
    enum ret_codes { ok, fail, repeat};
    bool setupOK=false;

    #include "src/SM_entry.c"
//    #include "src/SM_scanWifi.c"
//    #include "src/SM_chooseAP.c"
//    #include "src/SM_choosePass.c"
//    #include "src/SM_connectAP.c"
//    #include "src/SM_waitForAP.c"
//    #include "src/SM_checkRxId.c"
//    #include "src/SM_seekCH.c"
//    #include "src/SM_seekAction.c"
//    #include "src/SM_postRequest.c"
//    #include "src/SM_foo.c"
    #include "src/SM_out.c"
//    #include "src/SM_Vars.c"
    
    #define EXIT_STATE out
    #define ENTRY_STATE SM_entry
    
    enum state_codes cur_state = ENTRY_STATE;
    enum ret_codes rc;
    enum ret_codes (* state_fun)(void);

    
    void setup() {
    
      //Setup WIFI
      AP.ssid="Landing.careers"
      AP.password="landing17";
      
      req.host = "192.168.10.5";
      req.httpPort = 8080;
      req.url= "/";

     
      //Setup Serial
      Serial.begin(115200);
      delay(200);
      uiTimerInit(200);

      
      //Setup Buttons
      while (!setupMcp(&mcp));
      delay(200);
      btnTimerInit(20);

            
      delay(100);
      setupOK=true;
    
    }
    
    void loop() {

   
        state_fun = state[cur_state];
        rc = state_fun();
        if (EXIT_STATE == cur_state) out_state();
        cur_state = state_transitions[cur_state][rc];
          
      if (refreshBTN){
        buttonStates = debouceButtons(buttonStatesRaw,5,&mcp);
        refreshBTN=false;
      }
      yield();
    }
    

    
    

