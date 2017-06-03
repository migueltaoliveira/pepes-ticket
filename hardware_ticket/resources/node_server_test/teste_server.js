
  


  

// set up ========================
  var express   = require('express');
  var app       = express();                // create our app w/ express
  var morgan      = require('morgan');
  var path        = require('path');
  var bodyParser  = require('body-parser');
  //var multiparty  = require('multiparty');
  //var formidable  = require('formidable');
  var port    = Number(process.env.PORT || 5347);
  //var fs          = require('fs'); 
  var ip = require("ip");



  app.use(morgan('dev'));
  //app.use(bodyParser());
  // parse application/x-www-form-urlencoded
  app.use(bodyParser.urlencoded({ extended: false }))

  //app.use(express.static(__dirname + '/public'));
  //app.use(express.static(path.join(__dirname, 'public')));

	// listen (start app with node server.js) ======================================
	app.listen(port, function() {
		console.log("Listening on " + port);
	});


console.dir ( ip.address() );


  // gets/posts
	app.get('/confirmationID', function(req, res) {
		res.send('Payload: stagecom1234');
    //res.send('Payload: fuck_you!');
	});




   app.post('/actionPOST', function(request, response){
    console.log(request.body);
    response.send(200);
});

  