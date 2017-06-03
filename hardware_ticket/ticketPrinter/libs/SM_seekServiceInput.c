
 enum ret_codes SM_seekServiceInput_state(void){

 	  waitUserACK=0;
    toPrint="Input a Service: \r\n\r\n";
    for(int i=0;i<5;i++){
      toPrint+="\t";
      toPrint+=String(i+1);
      toPrint+=" - ";
      toPrint+=Services.labels[i];
      toPrint+="\r\n";
    }



	//Get User Input and Decide

    switch (buttonStates){
      case BTN_1:{    
        Services.activeServices[0]=!Services.activeServices[0];
        toPrint+=Services.labels[0];	
        if(Services.activeServices[3]) cur_ticket++;
        else cur_ticket--;
        break;
      }

      case BTN_2:{
      	Services.activeServices[1]=!Services.activeServices[1];  
        toPrint+=Services.labels[1];
       	if(Services.activeServices[1]) cur_ticket++;
        else cur_ticket--;
        break;
      }

      case BTN_3:{
      	Services.activeServices[2]=!Services.activeServices[2];
        if(Services.activeServices[2]) cur_ticket++;
        else cur_ticket--;
        break;
      }

      case BTN_4:{
      	Services.activeServices[3]=!Services.activeServices[3];
        toPrint+=Services.labels[3];
       	if(Services.activeServices[3]) cur_ticket++;
        else cur_ticket--;
        break;
      }

      case BTN_5:{
      	Services.activeServices[4]=!Services.activeServices[4]; 
        toPrint+=Services.labels[4];
       	if(Services.activeServices[4]) cur_ticket++;
        else cur_ticket--;
        
        break;
      }

      case BTN_PRINT:{
        composeRequest(&req);
        toPrint=req.payload;
        waitUserACK=10;
        cur_ticket=0;
        buttonStates=0;
        return ok;
      }

      case BTN_CANCEL:{
        buttonStates=0;
        if (cur_ticket>0){
        cur_ticket=0;
        for (int i=0;i<5;i++) Services.activeServices[i]=false;
      	toPrint="CANCEL TICKET";
        waitUserACK=20;
        return fail;
      }
      }
      default:
       break;
     }

     buttonStates=0;
     return repeat;
 }