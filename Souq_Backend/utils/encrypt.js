var crypto = require('crypto');

function encrypt(cardNumber){
    var encryptedNumber = crypto.createHash("sha256")
    .update(cardNumber)
    .digest("hex");
    return encryptedNumber;
}

module.exports.encrypt = encrypt;