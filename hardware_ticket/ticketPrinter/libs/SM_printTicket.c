
 void printDots(int number){
 	for (int i=0;i<number;i++) toPrint+=".";
 }


 enum ret_codes SM_printTicket_state(void){

 	
 	toPrint=resp.payload;
 	toPrint+="\r\n\r\n";
    toPrint+="PRINTING TICKET";
    for (int i=0;i<5;i++){
    Services.activeServices[i]=false;
    }
    if (printingStatus>15){ 
    	printingStatus=0;
    	return ok;
    }
    printingStatus++;
    printDots(printingStatus);
    waitUserACK=2;
    return repeat;

 }


