 

 	enum ret_codes SM_postRequest_state(void){



 	toPrint="Sending Request to RX...";
    req.type="POST";
    req.url="/actionPOST";
    composeRequest(&ID,&channel,&action,&req);
    resp.httpStatus=0;
    sendRequest(&req, &resp, &client , 0);

    channel="";
    action="";
    //toPrint+= req.payload;
    
    if (resp.httpStatus!=200){
    	toPrint+="\r\nConnection Failed (status is not 200)";
    	waitUserACK=20;
    	return repeat;
      }
    else{ 
      waitUserACK=0;  
      return ok;
    }
}