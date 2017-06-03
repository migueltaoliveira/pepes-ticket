 

 	enum ret_codes SM_postRequest_state(void){


    //req.type="POST";
    //req.url="/actionPOST";
    resp.httpStatus=0;
    
    sendRequest(&req, &resp, &client , 0);

    
    if (resp.httpStatus!=200){
    	toPrint+="\r\nConnection Failed (status is not 200)";	
    	return repeat;
      }
    else{ 
      
      return ok;
    }

    
}

