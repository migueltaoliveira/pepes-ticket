
 enum ret_codes SM_seekCH_state(void){

 	waitUserACK=0; 
    toPrint="Input a Channel: ";


    //uint16_t anyChannelButton = btn_CH1 | btn_CH2 | btn_CH3 | btn_CH4 | btn_CH5;

	//Get User Input and Decide
    switch (buttonStates){
      case btn_CH1:{      		
      	
        readFSField("/configFile.txt", "CH1", &bufferFS);
        channel=bufferFS;
      	buttonStates=0;
        return ok;
        break;
      }

      case btn_CH2:{
      	
        readFSField("/configFile.txt", "CH2", &bufferFS);
        channel=bufferFS;
      	buttonStates=0;		
        return ok;
        break;
      }

      case btn_CH3:{
      	
        readFSField("/configFile.txt", "CH3", &bufferFS);
        channel=bufferFS;
      	buttonStates=0;
        return ok;
        break;
      }

      case btn_CH4:{
      	
        readFSField("/configFile.txt", "CH4", &bufferFS);
        channel=bufferFS;
      	buttonStates=0;
        return ok;
        break;
      }

      case btn_CH5:{
      	
        readFSField("/configFile.txt", "CH5", &bufferFS);
        channel=bufferFS;
      	buttonStates=0;
        return ok;
        break;
      }

      case btn_DOWN:{

        break;
      }

      case btn_UP:{
      	toPrint="RESUME OVER";
        waitUserACK=20;
        buttonStates=0;
        return fail;
        break;
      }
      default:
       break;
     }
     toPrint += channel;
     waitUserACK=0;
     buttonStates=0;
     return repeat;
 }