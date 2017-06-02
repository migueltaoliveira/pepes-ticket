function handleFileSelect(evt)
{
	evt.stopPropagation();
	evt.preventDefault();
	var files = evt.dataTransfer.files;
	var reader = new FileReader();

	reader.onload = function(event) {
   var rawString = event.target.result;
   var removeCR =  rawString.replace(/\r/g, "");
   var removeLF = removeCR.replace(/[\n]+/g, '\n');
   var fileName = reader.get
   document.getElementById('drop_zone').value = removeLF;


   var index=document.getElementById('mySelect').value;
   document.getElementById("option"+index).text = "CH"+index+": "+files[0].name.replace(".txt","");
   submitSPIFFS();
 }
 reader.readAsText(files[0],"UTF-8");
}

function handleDragOver(evt) {
	evt.stopPropagation();
	evt.preventDefault();
	evt.dataTransfer.dropEffect = 'copy';
  console.log("onDragOver");
}

function onChangeChannel(){
  loadFreeze();
  loadInstructions();
}



function loadFreeze(){


  var xmlhttp, text;
  xmlhttp = new XMLHttpRequest();
  var sendReq = '/loadFreeze'+'?CHANNEL='+ (document.getElementById("mySelect").value);
  xmlhttp.open('GET', sendReq, false);
  xmlhttp.send();
  text = xmlhttp.responseText;
  var state;
  if (text=="true") state=1;
  else state=0; 
  document.getElementById('onoff').checked=state;

}

function writeTextArea(text) {
  document.getElementById("drop_zone").value = text;
}

function writeOption(index,name){
  document.getElementById("option"+(index)).text = "CH"+(index)+": "+name;
}



function loadSPIFFS(){
 loadConfigs();
 loadNames();
 loadInstructions();
 loadFreeze();
 var myVar = setInterval(refreshStuff, 1000);
 var dropZone = document.getElementById("drop_zone");
 dropZone.addEventListener('dragover', handleDragOver, false);
 dropZone.addEventListener('drop', handleFileSelect, false);
}

function submitSPIFFS(){

  var index=document.getElementById('mySelect').value;
  var gcode=document.getElementById("drop_zone").value;
  var name= document.getElementById("option"+index).text.split(": ");
  var text=index+";\n"+name[1]+";\n"+gcode;
  console.log(text);
  var formData = new FormData();
  formData.append("sendStuff", text);
  var xmlhttp;
  xmlhttp = new XMLHttpRequest();
  xmlhttp.open('POST', '/saveSPIFFS', true);
  xmlhttp.onload = function () {
    console.log(this.responseText);
  };
  xmlhttp.send(formData);

}

function loadConfigs(){
  var xmlhttp, text;
  xmlhttp = new XMLHttpRequest();
  var sendReq = '/loadConfigs';
  xmlhttp.open('GET', sendReq, false);
  xmlhttp.send();
  text = xmlhttp.responseText;
  var res = text.split("\n");
  var headerString = "PP2016 MASTER CONFIGURATION: "+res[0];
  document.getElementById("headerTitle").innerHTML= headerString; 
  document.getElementById("ssidTA").value = res[0];
  document.getElementById("passTA").value = res[1];
  document.getElementById("currentMaxTA").value = res[2];

}

function submitConfigs(){

  var ssid1=document.getElementById("ssidTA").value;
  var pass1=document.getElementById("passTA").value;
  var hidden1 = document.getElementById("hiddenTA").checked;
  var text=ssid1+";\n"+pass1+";\n"+hidden1;
  console.log(text);
  var formData = new FormData();
  formData.append("sendStuff", text);
  var xmlhttp;
  xmlhttp = new XMLHttpRequest();
  xmlhttp.open('POST', '/saveConfigs', true);
  xmlhttp.onload = function () {
    console.log(this.responseText);
  };
  xmlhttp.send(formData);
}


function loadNames(){
  var xmlhttp, text;
  xmlhttp = new XMLHttpRequest();
  var sendReq = '/loadNames';
  xmlhttp.open('GET', sendReq, false);
  xmlhttp.send();
  text = xmlhttp.responseText;
  var res = text.split("\n");

  for (i = 0; i < 6; i++) {    
   writeOption(i+1,res[i]);  
 }  
}

function loadInstructions(){
  var xmlhttp, text;
  xmlhttp = new XMLHttpRequest();
  var sendReq = '/load'+'?CHANNEL='+ (document.getElementById("mySelect").value);
  xmlhttp.open('GET', sendReq, false);
  xmlhttp.send();
  text = xmlhttp.responseText;
  writeTextArea(text); 
}


function sendFreeze(){

  var index=document.getElementById('mySelect').value;
  var freeze=document.getElementById('onoff').checked;
  var text=index+";\n"+freeze;
  console.log(text);
  var formData = new FormData();
  formData.append("sendStuff", text);
  var xmlhttp;
  xmlhttp = new XMLHttpRequest();
  xmlhttp.open('POST', '/sendFreeze', true);
  xmlhttp.onload = function () {
    console.log(this.responseText);
  };
  xmlhttp.send(formData);

}

function restartBoard(){
  console.log("restarting board");
  var xmlhttp;
  xmlhttp = new XMLHttpRequest();
  xmlhttp.open('POST', '/restartBOARD', true);
  xmlhttp.send();
  
}


function refreshStuff(){
getCurrentStatus();
loadFreeze();
}

function getCurrentStatus() {

  var xmlhttp, text;
  xmlhttp = new XMLHttpRequest();
  var sendReq = '/loadCurrent';
  xmlhttp.open('GET', sendReq, false);
  xmlhttp.send();
  text = xmlhttp.responseText;
  document.getElementById("currentPeakTA").value = text;
}

function setMaxCurrent(){
  var text= document.getElementById("currentMaxTA").value;
  var formData = new FormData();
  formData.append("sendStuff", text);
  var xmlhttp;
  xmlhttp = new XMLHttpRequest();
  xmlhttp.open('POST', '/setCurrentMax', true);
  xmlhttp.onload = function () {
    console.log(this.responseText);
  };
  xmlhttp.send(formData);
}

