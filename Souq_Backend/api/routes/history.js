const express = require('express')
const router = express.Router()

// import file
const database = require("../../config")

// Add to History
router.post("/add", (request, response) => {
    const userId = request.body.userId
    const productId = request.body.productId
  
    const query = "INSERT INTO history(user_Id, product_Id) VALUES(?, ?)"
   
    const args = [userId, productId]

    database.query(query, args, (error, result) => {
        if(error) throw error
        response.status(200).send("Added to History")
    });
});
      
module.exports = router