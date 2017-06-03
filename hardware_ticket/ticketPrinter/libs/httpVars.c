
struct accessPoint{
String ssid = "android8945";
String password = "macacoadriano";
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
  const char* host="95f9a241.ngrok.io";
  const int httpPort=80;
  String url = "/market/rest/endpoints/tickets/generate";
  String connection = "close";
  String payload;
};