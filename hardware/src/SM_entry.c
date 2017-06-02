enum ret_codes SM_entry_state(void){

      	     
      toPrint="STAGECOM TX MODULE";
      toPrint+="\r\n\r\n\r\n";
      toPrint+="Press any button to start...";
  	  
  	  if (setupOK){
  	  	if(buttonStates > 0) return ok;
  	    //If any button is pressed...
        //if()
        else return repeat;
  	   }
  	   else{
  	   	Serial.println("Problem During Setup...");
  	   	delay(4000);
  	   	return fail;
  	   }

}