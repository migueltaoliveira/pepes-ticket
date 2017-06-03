var server="http://localhost:8080";
var BTNOFF=-1;
var Xarray=[BTNOFF,BTNOFF,BTNOFF,BTNOFF,BTNOFF,BTNOFF];
var Yarray=[BTNOFF,BTNOFF,BTNOFF,BTNOFF,BTNOFF,BTNOFF];


// var Xarray=[0,BTNOFF,0,1,1,0];
// var Yarray=[0,BTNOFF,1,0,1,0];


var imageRatio=[1,1];
var freeze=[0,0,0,0,0,0];


var isMobile = /iPhone|iPad|iPod|Android/i.test(navigator.userAgent);
var timer;
var endOccured=true; 
var touchduration = 700; 
var currentChannel=1;
var on2click=false;


var tapedTwiceMinus = false;
var tapedTwicePlus = false;

//setInterval(function() {console.log([myImg.width,myImg.height]);},1000);


function loadStuff(){
  
  
  createButtons();

  if(isMobile){ 
    configTouch();
    server="http://192.168.1.93:8080";
  }
imageRatio=[myImg.width-parseInt(but1.style.width)  ,myImg.height-parseInt(but1.style.height)];
loadCoordinates();
placeButtons();  

loadFreezeVec();


for (i = 0; i < freeze.length; i++) { 
  updateFreezeStyle(i+1);
}

var dropZone=document.getElementById("drop_zone");
dropZone.addEventListener('dragover', handleDragOver, false);
dropZone.addEventListener('drop', handleFileSelect, false);
}

function getImageRatio(){
imageRatio=[myImg.width-parseInt(but1.style.width)  ,myImg.height-parseInt(but1.style.height)];
placeButtons(); 
}

function loadCoordinates(){
  var xmlhttp, text;
  xmlhttp = new XMLHttpRequest();
  
  var sendReq = server+"/loadCoordinates";

  xmlhttp.open('GET', sendReq, false);
  xmlhttp.send();
  text = xmlhttp.responseText;
  var splitC=text.split(';');
  imageRatio[0]=parseInt(splitC[0]);
  imageRatio[1]=parseInt(splitC[1]);
  var x=splitC[2].split(' ').map(Number);
  var y=splitC[3].split(' ').map(Number);

for (i = 0; i < x.length; i++) { 
  if(x[i] != BTNOFF){
  Xarray[i] = x[i]/1000;
}
  if(y[i] != BTNOFF){
  Yarray[i] = y[i]/1000;
}
}
}

  function createButtons(){

    for (i = 0; i < Xarray.length; i++) { 

     var btn1 = document.createElement("BUTTON");
     btn1.id="but"+String(i+1);
     btn1.style.width="30px";
     btn1.style.height="30px";
     if (freeze[i]) btn1.style.background='#ff0000';
     else btn1.style.background='#00ff00';
     btn1.innerHTML=i+1;
     btn1.style.position = "relative";
     btn1.style.top="20px";
     btn1.style.left="-2px";
     btn1.setAttribute("onclick","debounceClick(this.innerHTML)");
     if(isMobile){

     }
     else{
      btn1.setAttribute("ondblclick","openChannel(this)");
      btn1.setAttribute("draggable","true");
      btn1.setAttribute("ondragstart","drag(event)");
    }
    document.getElementById("divB"+String(i+1)).appendChild(btn1); 

  }
}

function configTouch(){
  for (i = 0; i < Xarray.length; i++) { 

    var btn=document.getElementById("but"+String(i+1));
    btn.addEventListener('touchstart', function(event) {
      endOccured=false;
      timer=setTimeout(function() {
        getHold(event.target);
      },touchduration);
    }, false);
    btn.addEventListener('touchend', function(event) {
     clearTimeout(timer); 
     endOccured=true;
   }, false);


    btn.addEventListener('touchmove', function(event) {
      event.preventDefault();
      clearTimeout(timer); 
      var touch = event.targetTouches[0];
      var xRaw=(touch.pageX);
      var yRaw=(touch.pageY);
      moveButton(event.target,xRaw,yRaw);


    }, false);
  }

  btn=document.getElementById("minusB");
  btn.addEventListener("touchstart", tapHandlerMinus);

  btn=document.getElementById("plusB");
  btn.addEventListener("touchstart", tapHandlerPlus);

}


function placeButtons(){
  imageRatio=[myImg.width-parseInt(but1.style.width)  ,myImg.height-parseInt(but1.style.height)];
  var btn;
  for (i = 0; i < Xarray.length; i++) { 
    btn=document.getElementById("but"+String(i+1));

    if((Xarray[i]!= BTNOFF && Yarray[i]!=BTNOFF)&&(Xarray[i] < myImg.width)&&(Yarray[i]<myImg.height)){
     btn.style.position="absolute";
     btn.style.left = (Xarray[i]*imageRatio[0])+'px';//*(myImg.width/imageRatio[0]))
     btn.style.top = (Yarray[i]*imageRatio[1]) +'px';//*(myImg.height/imageRatio[1])
     imageC.appendChild(btn);
     } 
  }
}

function moveButton(element,rawX,rawY){

  imageRatio=[myImg.width-parseInt(but1.style.width)  ,myImg.height-parseInt(but1.style.height)];
  imageC.appendChild(element);
  var rect = myImg.getBoundingClientRect();
  var x=(rawX-rect.left-(parseInt(element.style.width)/2));
  var y=(rawY-rect.top-(parseInt(element.style.height)/2));

 if((x > myImg.width-70) && (y < 50)){  //If placed on corner make it go to stack and put BTNOFF
  buttonBacktoStack(element);
  Xarray[element.innerHTML-1]=-1;
  Yarray[element.innerHTML-1]=-1;
}
else{    
  element.style.position="absolute";    //If placed anywhere on image make it stay there
  element.style.left = (x) + 'px';
  element.style.top = (y) + 'px';

  Xarray[element.innerHTML-1]=x/imageRatio[0];
  Yarray[element.innerHTML-1]=y/imageRatio[1];

  }
  sendCoordinates(element.innerHTML); 
}


function sendCoordinates(index){

  var formData = new FormData();
  var xSend=String(Xarray[index-1]);
  var ySend=String(Yarray[index-1]);
  
  formData.append("CHANNEL", String(index));
  formData.append("X", xSend * 1000);
  formData.append("Y", ySend * 1000);

  var xhr;
  xhr = new XMLHttpRequest();
  xhr.open('POST', server+'/sendCoordinates', true);
  xhr.onreadystatechange = function() {
    if(xhr.readyState == XMLHttpRequest.DONE && xhr.status == 200) {
        // Request finished. Do processing here.
      }
    }
    xhr.send(formData);

  }


function buttonBacktoStack(element){

 element.style.position = "relative";
 element.style.top="20px";
 element.style.left="-2px"; 
 document.getElementById("divB"+element.innerHTML).appendChild(element);
}

function getHold(element){
 if (!endOccured){ 
  endOccured=true;
  openChannel(element);
}
}


function tapHandlerMinus(event) {
  if(!tapedTwiceMinus) {
    tapedTwiceMinus = true;
    setTimeout( function() { tapedTwiceMinus = false; }, 300 );
    return false;
  }
  event.preventDefault();
}

function tapHandlerPlus(event) {
  if(!tapedTwicePlus) {
    tapedTwicePlus = true;
    setTimeout( function() { tapedTwicePlus = false; }, 300 );
    return false;
  }
  event.preventDefault();
}


function allowDrop(ev) {
  ev.preventDefault();
}

function drag(ev) {
  ev.dataTransfer.setData("text", ev.target.id);
}

function drop(ev) {

  ev.preventDefault();
  var data = ev.dataTransfer.getData("text");
  var d = document.getElementById(data);
  var xRaw=event.clientX;
  var yRaw=event.clientY;
  moveButton(d,xRaw,yRaw); 
}


function readURL() {
  var input = document.getElementById("myFile");
  if (input.files && input.files[0]) {
    var reader = new FileReader();
    reader.onload = function (e) {
      var image=e.target.result;
      document.getElementById("myImg").src=image;
    }
    reader.readAsDataURL(input.files[0]);
  }
}

function openChannel(element){
  clearTimeout(timer);
  on2click=true;
  var index=element.innerHTML;
  currentChannel=index;

  loadFreeze(index);
  loadInstructions(index);
  var name="CHANNEL "+String(index);
  document.getElementById("label4").innerHTML=name;
  document.getElementById("tabbed4").checked=true;
  document.getElementById("label4").style.display="block";
}

function closeChannel(){
  document.getElementById("tabbed1").checked=true;
  document.getElementById("label4").style.display="none";
}

function id2index(text){
  return text;
}
function debounceClick(element){
  timer=setTimeout(function (){sendFreeze(element);},200);       
}

function sendFreeze(index){
  if (!on2click){
    var element=document.getElementById("but"+String(index));
    freeze[index-1]=!freeze[index-1];

    var text=index+";\n"+freeze[index-1];
    console.log(text);
    var formData = new FormData();
    formData.append("sendStuff", text);

    var xhr;
    xhr = new XMLHttpRequest();
    xhr.open('POST', server+'/sendFreeze', true);
  //xhr.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
  xhr.onreadystatechange = function() {
    if(xhr.readyState == XMLHttpRequest.DONE && xhr.status == 200) {
        // Request finished. Do processing here.
      }
    }
  // xmlhttp.onload = function () {
  //   console.log(this.responseText);
  // };
  xhr.send(formData);

  updateFreezeStyle(index);
}
on2click=false;
}

function sendFreezeSLIDER(){
  sendFreeze(currentChannel);
}

function loadFreeze(index){

  var xmlhttp, text;
  xmlhttp = new XMLHttpRequest();
  var sendReq = server+"/loadFreeze"+"?CHANNEL="+ String(index);
  xmlhttp.open('GET', sendReq, false);
  xmlhttp.send();
  text = xmlhttp.responseText;
  if (text=="true") freeze[index-1]=1;
  else freeze[index-1]=0; 
  updateFreezeStyle(index);
  
}

function loadFreezeVec(){

  var xmlhttp, text;
  xmlhttp = new XMLHttpRequest();
  var sendReq = server+"/loadFreezeVec";
  xmlhttp.open('GET', sendReq, false);
  xmlhttp.send();
  text = xmlhttp.responseText;
  var x=text.split(' ').map(Number);
  freeze = x.slice();  
}


function updateFreezeStyle(index){
  var element=document.getElementById("but"+index);

  if (freeze[index-1]) element.style.background='#ff0000';
  else element.style.background='#00ff00';
  document.getElementById('onoff').checked=!freeze[index-1];


}

function loadInstructions(channel){
  // var xmlhttp, text;
  // xmlhttp = new XMLHttpRequest();
  // var sendReq = '/loadInstructions'+'?CHANNEL='+ (document.getElementById("mySelect").value);
  // xmlhttp.open('GET', sendReq, false);
  // xmlhttp.send();
  // text = xmlhttp.responseText;
  document.getElementById("drop_zone").value = "Hello CHANNEL "+channel;
}

function showVal(value){

  document.getElementById("sliderVal").value=value;

}

function decVal(){
  var x=parseInt(document.getElementById("sliderVal").value);
  document.getElementById("sliderVal").value=x-1;
  document.getElementById("myRange").value=x-1;
}
function incVal(){
  var x=parseInt(document.getElementById("sliderVal").value);
  document.getElementById("sliderVal").value=x+1;
  document.getElementById("myRange").value=x+1;
}

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


   //ar index=currentChannel;
   // document.getElementById("option"+index).text = "CH"+index+": "+files[0].name.replace(".txt","");
   // submitSPIFFS();
 }
 reader.readAsText(files[0],"UTF-8");
}

function handleDragOver(evt) {
  evt.stopPropagation();
  evt.preventDefault();
  evt.dataTransfer.dropEffect = 'copy';
  console.log("onDragOver");
}

function uploadImage(){
  var fileSelect = document.getElementById('myFile');
  var files = fileSelect.files;
  var formData = new FormData();
  var file = files[0];

  formData.append('uploads[]', file, file.name);

  var xhr = new XMLHttpRequest();
  xhr.open('POST', '/saveFile', true);
  xhr.onreadystatechange = function() {
    if(xhr.readyState == XMLHttpRequest.DONE && xhr.status == 200) {
        // Request finished. Do processing here.
      }
    }
    xhr.send(formData);
  }
