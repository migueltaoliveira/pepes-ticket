
    //General ESP8266 Stuff
    #include <Arduino.h>
    extern "C"
    {
    #include "user_interface.h"
    }

    #include "libs/services.c"
    struct services Services;

    
    //WIFI functions
    #include <ESP8266WiFi.h>
    #include <WiFiClient.h>
    #include "libs/httpVars.c"
    #include "libs/connectAP.c"
    #include "libs/sendRequest.c"
    #include "libs/composeRequest.c"
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
    #include "libs/btnTimerInit.c"
    #include "libs/btnNames.c"
    #include "libs/setupMcp.c"
    #include "libs/debounceButtons.c"




    //UI Functions
    #define USE_SERIAL (Serial)
    bool refreshUI;
    int waitUserACK=0;
    int waitUserACK_count=0;
    int cursorPos = 0;
    String buf;
    os_timer_t UI_Timer;
    String toPrint="";
    int printingStatus=0;
    #include "libs/uiTimerInit.c"
    #include "libs/clearTerminal.c"
    
    
    //State Machine (SM) Functions
    enum ret_codes { ok, fail, repeat};
    bool setupOK=false;

    #include "libs/SM_entry.c"
    #include "libs/SM_connectAP.c"
    #include "libs/SM_waitForAP.c"
    #include "libs/SM_seekServiceInput.c"
    #include "libs/SM_postRequest.c"
    #include "libs/SM_printTicket.c"
    #include "libs/SM_out.c"
    #include "libs/SM_error.c"
    #include "libs/SM_Vars.c"

    
    #define EXIT_STATE out
    #define ENTRY_STATE SM_entry
    
    enum state_codes cur_state = ENTRY_STATE;
    enum ret_codes rc;
    enum ret_codes (* state_fun)(void);


    

    void setup() {
   
      //Setup UI
      USE_SERIAL.begin(115200);
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

   
        if (waitUserACK_count < waitUserACK){
        delay(100);
        waitUserACK_count++;
      }
      else{
        state_fun = state[cur_state];
        rc = state_fun();
        if (EXIT_STATE == cur_state) out_state();
        cur_state = state_transitions[cur_state][rc];
        waitUserACK_count=0;
      }


      if (refreshUI){        
        clearTerminal();
        USE_SERIAL.print(toPrint);
        refreshUI=false;
      }

          
      if (refreshBTN){
        buttonStates = debouceButtons(buttonStatesRaw,5,&mcp);
        refreshBTN=false;
      }

      
      yield();
    }
    

    
    

