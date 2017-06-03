
void LEDblinkALL(int times,int period){

	for (int i=0;i<times;i++){
		for(int j=0;j<5;j++) mcp.digitalWrite(Services.serviceLED[j],HIGH);
		delay(period);
		for(int j=0;j<5;j++) mcp.digitalWrite(Services.serviceLED[j],LOW);
		delay(period);	
	}

}


void LEDblinkONE(int service,int times,int period){

	for (int i=0;i<times;i++){
		mcp.digitalWrite(Services.serviceLED[service],HIGH);
		delay(period);
		for(int j=0;j<5;j++) mcp.digitalWrite(Services.serviceLED[service],LOW);
		delay(period);	
	}

}