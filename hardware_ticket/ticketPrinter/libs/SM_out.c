enum ret_codes out_state(void){
  	
  	//toPrint="Not implemented yet!";
  	waitUserACK=0;
  	toPrint=req.type;
    toPrint+="\r\n";
    toPrint+=req.host;
    toPrint+="\r\n";
    toPrint+=String(req.httpPort);
    toPrint+="\r\n";
    toPrint+=req.connection;
    toPrint+="\r\n";
    toPrint+=req.payload;
    toPrint+="\r\n\r\n";

    toPrint+=resp.httpStatus;
    toPrint+="\r\n";
    toPrint+=resp.payload;
   





	return repeat;
  };