
 enum ret_codes SM_seekServiceInput_state(void){

 	  waitUserACK=0;
    toPrint="Input a Service: ";



	//Get User Input and Decide

    switch (buttonStates){
      case BTN_1:{    
        Services.activeServices[0]=!Services.activeServices[0];
        toPrint+=Services.labels[0];	
      	
        break;
      }

      case BTN_2:{
      	Services.activeServices[1]=!Services.activeServices[1];  
        toPrint+=Services.labels[1];
       	
        break;
      }

      case BTN_3:{
      	Services.activeServices[2]=!Services.activeServices[2];
        toPrint+=Services.labels[2];
       	
        break;
      }

      case BTN_4:{
      	Services.activeServices[3]=!Services.activeServices[3];
        toPrint+=Services.labels[3];
       	
        break;
      }

      case BTN_5:{
      	Services.activeServices[4]=!Services.activeServices[4]; 
        toPrint+=Services.labels[4];
       	
        break;
      }

      case BTN_PRINT:{
        composeRequest(&req);
        toPrint=req.payload;
        waitUserACK=10;
        buttonStates=0;
        return ok;
      }

      case BTN_CANCEL:{
      	toPrint="RESUME OVER";
        waitUserACK=20;
        buttonStates=0;
        return fail;
      }
      default:
       break;
     }
     buttonStates=0;
     return repeat;
 }