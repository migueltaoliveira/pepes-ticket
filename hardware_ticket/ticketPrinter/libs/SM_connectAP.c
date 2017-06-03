//Connect to AP
enum ret_codes SM_connectAP_state(void){

    toPrint="Connecting to ssid: ";
    toPrint+= AP.ssid;
    toPrint+= "\tpass: ";
    toPrint+=AP.password;
    toPrint+="\r\n";
    
    connectAP(&AP.ssid[0],&AP.password[0],T0,&WiFi);
  	// LED_count=0;
    
    return ok;
    
}