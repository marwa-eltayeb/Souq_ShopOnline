const express = require('express')
const router = express.Router()

// import file
const database = require("../../config")

// Get All favorite products
router.get("/", (request, response) => {
    var userId = request.query.userId;
    var page = request.query.page;
    var page_size = request.query.page_size;

    console.log(typeof page);

    if(page == null){
        page = 0;
     }
 
     if(page_size == null){
        page_size = 25;
     }

     const args = [
        userId,
        userId,
        parseInt(page_size),
        parseInt(page)
    ];

    const query = `SELECT product.id,
                          product.product_name, 
                          product.price, product.image, 
                          product.category, 
                          product.quantity, 
                          product.supplier,
                          (SELECT IF(COUNT(*) >= 1, TRUE, FALSE) FROM cart WHERE cart.user_id = ? AND cart.product_id = product.id) as isInCart
                          FROM favorite JOIN product JOIN User 
                          ON favorite.product_id = product.id AND favorite.user_id = user.id 
                          WHERE user_id = ? 
                          LIMIT ? OFFSET ?`

    database.query(query, args, (error, result) => {
        if(error) throw error;
        response.status(200).json({
            "favorites" : result
        })

    })
});

// Add Favorite Product
router.post("/add", (request, response) => {
    const userId = request.body.userId
    const productId = request.body.productId
  
    const query = "INSERT INTO favorite(user_Id, product_Id) VALUES(?, ?)"
   
    const args = [userId, productId]

    database.query(query, args, (error, result) => {
        if (error) {
            if (error.code === 'ER_DUP_ENTRY') {
                response.status(500).send("Deplicate Entry")
            } else {
                throw error;
            }
        } else {
            response.status(200).send("Bookmark as favorite")
        }
    });
});
      
// Delete Favorite Product
router.delete("/remove", (request, response) => {
    const userId = request.query.userId;
    const productId = request.query.productId;
    const query = "DELETE FROM favorite WHERE user_id = ? and product_id = ?"
    const args = [userId, productId]

    database.query(query, args, (error, result) => {
        if(error) throw error
        response.status(200).send("Bookmark as Unfavorite")
    });
});
 
module.exports = router