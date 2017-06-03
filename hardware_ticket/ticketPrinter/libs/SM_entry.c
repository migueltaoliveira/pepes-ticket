enum ret_codes SM_entry_state(void){
  	  
      toPrint="Press any button";
  	  if (setupOK){
  	  	if(buttonStates > 0) return ok;
  	    //If any button is pressed...
        //if()
        else return repeat;
  	   }
  	   else{
  	   	toPrint="Problem During Setup...";
  	   	delay(4000);
  	   	return fail;
  	   }

}