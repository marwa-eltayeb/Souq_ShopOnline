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
        parseInt(page_size),
        parseInt(page)
    ];

    const query = "SELECT product.name, product.price, product.image, product.category, product.quantity, product.supplier FROM favorite JOIN product JOIN User ON favorite.product_id = product.id AND favorite.user_id = user.id WHERE user_id = ? LIMIT ? OFFSET ?"
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
        if(error) throw error
        response.status(200).json({
            "id" : result.insertId,
            "message" : "Bookmarked as favorite",
        })
    });
});
      
// Delete Favorite Id
router.delete("/:id", (request, response) => {
    const id = request.params.id;
    const query = "DELETE FROM favorite WHERE favorite_id = ?"
    const args = [id]

    database.query(query, args, (error, result) => {
        if(error) throw error
        response.status(200).json({
            "message" : "Bookmarked as Unfavorite",
        })
    });
});
 
module.exports = router