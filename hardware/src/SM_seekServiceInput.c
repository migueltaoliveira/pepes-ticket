
 enum ret_codes SM_seekServiceInput_state(void){

 	
    toPrint="Input a Service: ";


    //uint16_t anyChannelButton = btn_CH1 | btn_CH2 | btn_CH3 | btn_CH4 | btn_CH5;

	//Get User Input and Decide
    switch (buttonStates){
      case btn_CH1:{      		
      	buttonStates=0;
        return ok;
        break;
      }

      case btn_CH2:{
      	
       	buttonStates=0;		
        return ok;
        break;
      }

      case btn_CH3:{
      	
       	buttonStates=0;
        return ok;
        break;
      }

      case btn_CH4:{
      	
       	buttonStates=0;
        return ok;
        break;
      }

      case btn_CH5:{
      	
       	buttonStates=0;
        return ok;
        break;
      }

      case btn_DOWN:{

        break;
      }

      case btn_UP:{
      	toPrint="RESUME OVER";
        
        buttonStates=0;
        return fail;
        break;
      }
      default:
       break;
     }
     
     buttonStates=0;
     return repeat;
 }