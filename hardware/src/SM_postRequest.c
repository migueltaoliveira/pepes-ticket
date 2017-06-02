 

 	enum ret_codes SM_postRequest_state(void){



 	toPrint="Sending Request to RX...";
    req.type="POST";
    req.url="/actionPOST";
    //composeRequest(&ID,&channel,&action,&req);
    resp.httpStatus=0;
    //sendRequest(&req, &resp, &client , 0);

    
    if (resp.httpStatus!=200){
    	toPrint+="\r\nConnection Failed (status is not 200)";
    	
    	return repeat;
      }
    else{ 
      
      return ok;
    }
}