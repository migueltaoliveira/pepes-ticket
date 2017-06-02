


/* array and enum below must be in sync! */
enum ret_codes (* state[])(void) = {SM_entry_state , 	SM_connectAP_state,  SM_waitForAP_state ,     SM_postRequest_state , out_state};
enum state_codes        		       {SM_entry,         SM_connectAP,        SM_waitForAP ,           SM_postRequest,		     out};


/* transitions from end state aren't needed */
enum state_codes state_transitions[][3] = {
//  ok                  fail             repeat
  {SM_connectAP,    SM_entry,      		  SM_entry},     		  //SM_entry
  {SM_waitForAP,    SM_entry,           SM_entry},    	    //SM_connectAP
  {out,             out,                SM_waitForAP},    	//SM_waitForAP
  {out,   	        out,	     		      SM_postRequest},	  //SM_postRequest
  {out,             out,              	out},               //out
  };

