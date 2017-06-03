enum ret_codes SM_error_state(void){
  	  
    toPrint="Well some error occured!";
    onoff_count++;
    waitUserACK=10;
    if(onoff_count>10){
     onoff_count=0;		
     return ok;
    }
    return repeat;

}