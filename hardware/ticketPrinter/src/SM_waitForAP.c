
enum ret_codes SM_waitForAP_state(void){


	switch (waitForAP(AP.timeout,T0,&WiFi)){
		case -1:{
			toPrint+=".";
			break;
		}

		case 0:{
			toPrint+="Failed to Connect";
			waitUserACK=10;
			return fail;
			break;
		}

		case 1:{
			toPrint+="Connected to RX: ";
			toPrint+=AP.ssid;

			bufferFS=AP.ssid;
			bool a=writeFSField("/configFile.txt", "LAST_SSID", &bufferFS);
			bufferFS=AP.password;
			a=writeFSField("/configFile.txt", "LAST_PASS", &bufferFS);

			waitUserACK=10;
			return ok;
			break;
		}
	}
		delay(100);
		return repeat;
}