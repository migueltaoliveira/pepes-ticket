


/* array and enum below must be in sync! */
enum ret_codes (* state[])(void) = {SM_entry_state , 	SM_connectAP_state,  SM_waitForAP_state ,  SM_seekServiceInput_state  , SM_postRequest_state , SM_printTicket_state ,out_state , SM_error_state};
enum state_codes        		   {SM_entry,           SM_connectAP,        SM_waitForAP ,        SM_seekServiceInput,        SM_postRequest,		   SM_printTicket,	     out,		 SM_error};


/* transitions from end state aren't needed */
enum state_codes state_transitions[][3] = {
//  ok                  		fail             			repeat
  {SM_connectAP,    			SM_entry,      				SM_entry},     		  	//SM_entry
  {SM_waitForAP,    			SM_error,      				SM_connectAP},    	  	//SM_connectAP
  {SM_seekServiceInput,         SM_error,           		SM_waitForAP},    	  	//SM_waitForAP
  {SM_postRequest,             	SM_seekServiceInput,        SM_seekServiceInput}, 	//SM_seekServiceInput
  {SM_printTicket,         		SM_error,	     			SM_seekServiceInput},	//SM_postRequest
  {SM_seekServiceInput,   	 	SM_seekServiceInput,	    SM_printTicket},	  	//SM_printTicket
  {out,             			SM_error,           		out},                 	//out
  {SM_error,        			SM_error,          			SM_error},            	//SM_error
  };

