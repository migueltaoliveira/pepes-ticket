
 enum ret_codes SM_seekAction_state(void){

 	waitUserACK=0; 
    toPrint="Input an Action: ";


    //Get User Input and Decide
    switch (buttonStates){
      case btn_CH1:{   
      buttonStates=0;   		
      	return fail;
        break;
      }

      case btn_CH2:{
        buttonStates=0;
      	return fail;
        break;
      }

      case btn_CH3:{
        buttonStates=0;
      	return fail;
        break;
      }

      case btn_CH4:{
        buttonStates=0;
      	return fail;
        break;
      }

      case btn_CH5:{
        buttonStates=0;
      	return fail;
        break;
      }

      case btn_DOWN:{
      	action = "DOWN";
      	buttonStates=0;
      	return ok;
        break;
      }

      case btn_UP:{
      	action = "UP";
      	buttonStates=0;
      	return ok;
        break;
      }
      default:
       break;
     }
     toPrint+=action;
     waitUserACK=0;
     buttonStates=0;
     return repeat;
 }