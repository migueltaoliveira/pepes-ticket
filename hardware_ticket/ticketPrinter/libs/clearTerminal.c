void clearTerminal(){
	//while(Serial.available()>0) Serial.read();
	Serial.flush();
    Serial.write(27); // ESC
    Serial.print("[2J"); // clear screen
    Serial.write(27); // ESC
    Serial.print("[H"); // cursor to home
}