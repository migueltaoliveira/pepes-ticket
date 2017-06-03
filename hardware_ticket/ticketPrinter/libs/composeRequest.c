
	void composeRequest(struct httpReq* req){
		

		int count=0;
		for (int i=0;i<5;i++){
			if (Services.activeServices[i]) count++;
		}

		String buf="{services:[";
		for (int i=0;i<5;i++){
			if(Services.activeServices[i]){
			buf+="\"";
			buf+=Services.labels[i];
			buf+="\"";
			if (count>1){ 
				buf+=",";
				count--;
			}
			}
		}
		buf+="]}";
		req->payload=buf;


	}