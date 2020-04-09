const nodemailer = require('nodemailer');

const serverSupportMail = 'mardroid.apps@gmail.com'
const serverSupportPassword = '12345android'

const transporter = nodemailer.createTransport({
  service: 'gmail',
  auth: {
    user: serverSupportMail,
    pass: serverSupportPassword
  }
});

function sendOptMail(email, optCode){
  
  var mailOptions = {
    from: 'mardroid.apps@gmail.com',
    to: email,
    subject: 'Sending Email using Node.js',
    text: 'That was easy! This is your opt' + optCode
  };
  
  
  transporter.sendMail(mailOptions, function(error, info){
    if (error) {
      console.log(error);
      response.status(501).send("Invalid")
    } else {
      console.log('Email sent: ' + info.response);
    }
  });    
  
}

function getRandomInt(min, max) {
  min = Math.ceil(min);
  max = Math.floor(max);
  return Math.floor(Math.random() * (max - min)) + min; 
}


module.exports.sendOptMail = sendOptMail;
module.exports.getRandomInt = getRandomInt;



