
void sendRequest(struct httpReq* req, struct httpResp* resp, WiFiClient * client,bool showHTTP){
	
  // Serial.println();
  // Serial.print("connecting to ");
  // Serial.println(req->host);
  
  if (!client->connect(req->host, req->httpPort)) {
    Serial.println("connection failed");
    return;
  }
  
  // We now create a URI for the request
  
  // Serial.print("Requesting URL: ");
  // Serial.println(req->url);
  String toSend;
  // This will send the request to the server
  if(req->type == "GET"){
  
    toSend = "GET " + req->url + " HTTP/1.1\r\n" +
               "Host: " + req->host + "\r\n" + 
               "Connection: close\r\n\r\n";
}
  else if(req->type == "POST"){

    toSend="POST " + req->url + " HTTP/1.1\r\n" +
               "Host: " + req->host + "\r\n" + 
               "Connection: close\r\n" +
               "Content-Type: application/json\r\n"+
               "Content-Length: "+String(req->payload.length())+"\r\n" + 
               "\r\n" +
               req->payload.c_str()+"\r\n";  
}
  if (showHTTP){
  // Serial.println("\n\n");
  // Serial.print(toSend);
}
  client->print(toSend);
  
  unsigned long timeout = millis();

  while (client->available() == 0) {
    if (millis() - timeout > 5000) {
      Serial.println(">>> Client Timeout !");
      client->stop();
      return;
    }
  }
  
  // Read all the lines of the reply from server and print them to Serial
  String line;
  String buf;
  int idx;


  while(client->available()){
    line = client->readStringUntil('\r\n');
  
    if (line.startsWith("HTTP/1.1 ")){
      idx = (sizeof("HTTP/1.1 ")/sizeof(char))-1;
      buf = line.substring(idx,idx+3);
      resp->httpStatus=buf.toInt();
      buf="";
    }

    else if (line.startsWith("content-type: ")){
      idx = (sizeof("content-type: ")/sizeof(char))-1;
      buf = line.substring(idx,line.indexOf(";"));
      resp->contentType=buf;
      buf="";
    }  

    else if (line.startsWith("content-length: ")){
      idx = (sizeof("content-length: ")/sizeof(char))-1;
      buf = line.substring(idx,idx+2);
      resp->contentLength=buf.toInt();
      buf="";
    } 
    else if (line.startsWith("etag: ")){
      idx = (sizeof("etag: ")/sizeof(char))-1;
      buf = line.substring(idx,idx+2);
      resp->etag=buf;
      buf="";
    }
    else if (line.startsWith("Date: ")){
      idx = (sizeof("Date: ")/sizeof(char))-1;
      buf = line.substring(idx,idx+10);
      resp->date=buf;
      buf="";
    }
    else if (line.startsWith("Connection: ")){
      idx = (sizeof("Connection: ")/sizeof(char))-1;
      buf = line.substring(idx,idx+10);
      resp->connection=buf;
      buf="";
    }
    else if (line.startsWith("{")){
      buf = line;
      resp->payload=buf;
      buf="";
    }
  }

  // Serial.println("HTTP_Status: "+String(resp->httpStatus));
  // Serial.println("closing connection");
  // Serial.println();
	
}
