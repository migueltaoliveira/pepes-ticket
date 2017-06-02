/* Debounces de inputs in the MCP23017.
INPUTS: 
		buttonStatesRaw - History of the raw input values. Each element of array corresponds to a full read of all channels at time t
		threshold - Number of required 0's in sequence: 11111000 00000000 in order to trigger a push 
		mcp - Object that communicates with the MCP23017 via I2C

OUTPUTS: buttonStates - Debounced states as a uint16_t (1 bit per channel)		
*/


uint16_t debouceButtons(uint16_t *buttonStatesRaw, uint16_t threshold, Adafruit_MCP23017* mcp){

	if (threshold > 15) threshold = 15;
	if (threshold < 1) threshold = 1;


	//Shift all values to the left in the array
	int maxIndex=15-threshold;
	for (int i=0; i<(sizeof(uint16_t)*8)-1;i++){
	 	buttonStatesRaw[i]=buttonStatesRaw[i+1];
	 	if (i<maxIndex) buttonStatesRaw[i]=0xFFFF;
	}

	//Append raw values
	buttonStatesRaw[15]=mcp->readGPIOAB();

	
	uint16_t buttonStates=0;
    uint16_t buffer=0;
    bool val;
    uint16_t th_mask = 0xffff ^ ((1<<threshold) - 1); 

    for(uint16_t button=0; button<16;button++){
        buffer=0;      
        for (int t=0;t<16;t++){
        val = ((buttonStatesRaw[t] & (1 << button)) >> button);
        buffer = buffer | (val << (15-t)) ;
        }
        
        bool teste = !(buffer ^ th_mask);
        buttonStates = buttonStates | (teste << button);
    }
	

	return buttonStates;
}