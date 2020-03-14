const express = require('express')
const router = express.Router()

// import file
const database = require("../../config")

router.get("/", (request, response) => {
    var productId = request.query.productId;
    var page = request.query.page;
    var page_size = request.query.page_size;

    if(page == null || page < 1){
        page = 1;
    }
 
    if(page_size == null){
        page_size = 20;
    }

    // OFFSET starts from zero
    const offset = page - 1;
    // OFFSET * LIMIT
    page = offset * page_size;

    const args = [
        productId,
        parseInt(page_size),
        parseInt(page)
    ];

    const query = "SELECT user.name, DATE_FORMAT(review.review_date, '%d/%m/%Y') As date,review.rate, review.feedback FROM Review JOIN Product JOIN User ON review.product_id = product.id AND review.user_id = user.id WHERE product_id = ? LIMIT ? OFFSET ?"
    database.query(query, args, (error, reviews) => {
        if(error) throw error;

        const avgQuery = 'SELECT AVG(rate) AS averageRate FROM review WHERE product_id = ?'

        database.query(avgQuery, productId, (err, avrg) => {
            if(err) throw err;
            response.status(200).json({
                "page": offset + 1,
                "error" : false,
                "avrg_review" : avrg[0]['averageRate'],
                "review" : reviews
            })
        });
    })
});

// Add Review about Product
router.post("/add", (request, response) => {
    const userId = request.body.userId
    const productId = request.body.productId
    const feedback = request.body.feedback
    const rate = request.body.rate

    const query = "INSERT INTO review(user_Id, product_Id, feedback, rate ,review_date) VALUES(?, ?, ?, ?,NOW())"
   
    const args = [userId, productId, feedback,rate]

    database.query(query, args, (error, result) => {
        if (error) {
            if (error.code === 'ER_DUP_ENTRY') {
                response.status(500).send("Deplicate Entry")
            } else {
                throw error;
            }
        } else {
            response.status(200).send("Review is Added")
        }
    });
});


module.exports = router