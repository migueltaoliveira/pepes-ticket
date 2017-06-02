
	void composeRequest(String *ID, String *channel, String *action, struct httpReq* req){
		
		req->payload="ID="+ID[0];
		req->payload+="&";

		req->payload+="CHANNEL="+channel[0];
		req->payload+="&";

		req->payload+="ACTION="+action[0];
			

	}