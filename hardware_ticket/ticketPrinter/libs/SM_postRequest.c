 

 	enum ret_codes SM_postRequest_state(void){


    //req.type="POST";
    //req.url="/actionPOST";
    resp.httpStatus=0;
    
    sendRequest(&req, &resp, &client , 0);

    for (int i=0;i<5;i++){
    Services.activeServices[i]=false;
    }
    if (resp.httpStatus!=200){
    	toPrint+="\r\nConnection Failed (status is not 200)";	
    	return repeat;
      }
    else{ 
      
      return ok;
    }

    
}

