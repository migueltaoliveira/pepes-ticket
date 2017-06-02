


/* array and enum below must be in sync! */
enum ret_codes (* state[])(void) = {SM_entry_state,	 SM_scanWifi_state, SM_chooseAP_state,  SM_choosePass_state,  	SM_connectAP_state,  SM_waitForAP_state ,SM_checkRxId_state, SM_seekCH_state, SM_seekAction_state,  SM_postRequest_state ,foo_state ,out_state};
enum state_codes        		       {SM_entry,		     SM_scanWifi,       SM_chooseAP,        SM_choosePass , 			SM_connectAP,        SM_waitForAP,		 SM_checkRxId  , 	 SM_seekCH,		   SM_seekAction, 		SM_postRequest,		  foo,       out};


/* transitions from end state aren't needed */
enum state_codes state_transitions[][3] = {
//  ok                  fail             repeat
  {SM_connectAP,    SM_entry,      		  SM_entry},     		  //SM_entry
  {SM_chooseAP,     SM_scanWifi,      	SM_scanWifi},     	//SM_scanWifi
  {SM_choosePass,   SM_scanWifi,      	SM_chooseAP},     	//SM_chooseAP
  {SM_connectAP,    SM_scanWifi,        SM_choosePass},   	//SM_choosePass
  {SM_waitForAP,    SM_scanWifi,        SM_choosePass},    	//SM_connectAP
  {SM_checkRxId,    SM_scanWifi,        SM_waitForAP},    	//SM_waitForAP
  {SM_seekCH,       SM_scanWifi,      	SM_checkRxId},     	//SM_checkRxId
  {SM_seekAction,   SM_entry,      	    SM_seekCH},     	  //SM_seekCH
  {SM_postRequest,  SM_seekCH,     		  SM_seekAction},     //SM_seekAction
  {SM_seekCH,   	  foo,	     		      SM_postRequest},	  //SM_postRequest
  {foo,             foo,                foo},                    //foo
  {out,             out,              	out},               //out
  };

