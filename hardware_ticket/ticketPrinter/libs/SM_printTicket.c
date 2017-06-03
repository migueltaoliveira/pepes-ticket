
 void printDots(int number){
 	for (int i=0;i<number;i++) toPrint+=".";
 }


 enum ret_codes SM_printTicket_state(void){

 	
 	toPrint=resp.payload;
 	toPrint+="\r\n\r\n";
    toPrint+="PRINTING TICKET";
    
    if (printingStatus>15){ 
    	printingStatus=0;
    	return ok;
    }
    printingStatus++;
    printDots(printingStatus);
    waitUserACK=2;
    return repeat;

 }


