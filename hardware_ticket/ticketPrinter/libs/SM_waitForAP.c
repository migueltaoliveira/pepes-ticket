
enum ret_codes SM_waitForAP_state(void){


	switch (waitForAP(AP.timeout,T0,&WiFi)){
		case -1:{
			
			// if (onoff_count % 2 == 0){
			// 	LED_count++;
			// }
			// Services.activeServices[LED_count]=!Services.activeServices[LED_count];
			// if (LED_count<4) onoff_count++;
			// else{
			//  onoff_count=0;
			//  LED_count=0;
			// }
			toPrint+=".";
			break;
		}

		case 0:{
			toPrint+="Failed to Connect";
			waitUserACK=20;
			return fail;
			break;
		}

		case 1:{
			toPrint+="Connected to RX: ";
			toPrint+=AP.ssid;
			// onoff_count=0;
			// LED_count=0;
			// for (int i=0;i<5;i++) Services.activeServices[i]=false;
			blinkALL(3);
			waitUserACK=20;
			return ok;
			break;
		}
	}
		delay(200);
		return repeat;
}