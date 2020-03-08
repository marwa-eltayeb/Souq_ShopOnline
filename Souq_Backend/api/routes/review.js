const express = require('express')
const router = express.Router()

// import file
const database = require("../../config")

// Add Review about Product
router.post("/add", (request, response) => {
    const userId = request.body.userId
    const productId = request.body.productId
    const feedback = request.body.feedback
    const rate = request.body.rate

    const query = "INSERT INTO review(user_Id, product_Id, feedback, rate ,review_date) VALUES(?, ?, ?, ?,NOW())"
   
    const args = [userId, productId, feedback,rate]

    database.query(query, args, (error, result) => {
        if(error) throw error
        response.status(200).send("Review is Added")
    });
});


module.exports = router