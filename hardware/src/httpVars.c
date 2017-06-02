
struct accessPoint{
String ssid = "Landing.careers";
String password = "landing17";
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
  String type = "GET";
  const char* host="192.168.123.99";
  const int httpPort=5347;
  String url = "/";
  String connection = "close";
  String payload;
};