 
/* Tries to connect to AP with ssid and password settings. 
	INPUT: ssid (String), password (String) and the timeout value (long)
	OUTPU: true if connects ok; false if timeout espires

*/

	
 void connectAP(const char* ssid,const char* password,unsigned long &T0,ESP8266WiFiClass* WiFi){

 	WiFi->mode(WIFI_STA);
 	WiFi->disconnect();
 	delay(100);
 	WiFi->begin(ssid, password);
 	T0=millis();
	
}

/* Wait for AP to connect

*/

int waitForAP(long timeout,unsigned long &T0,ESP8266WiFiClass* WiFi){
	
	if (WiFi->status() == WL_CONNECTED) return 1;
	else{
		if(millis()-T0 > timeout){
			return 0;
		}
	}
	return -1;
}