
struct accessPoint{
String ssid = "iPhone de Francisco";
String password = "randompassword";
long timeout=20000;
};

struct httpResp {
  int httpStatus;
  String contentType;
  int contentLength; 
  String etag;
  String date;
  String connection;
  String payload;
};


struct httpReq {
  String type = "POST";
  const char* host="bdf89679.ngrok.io";
  const int httpPort=80;
  String url = "/market/rest/endpoints/tickets/generate";
  String connection = "close";
  String payload;
};