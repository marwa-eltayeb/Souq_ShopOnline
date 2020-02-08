const express = require('express')
const router = express.Router()

// import file
const database = require("../../config")

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