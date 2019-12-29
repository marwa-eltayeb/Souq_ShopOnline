const express = require('express')
const router = express.Router()

// Upload and store images
const multer = require('multer')

const storage = multer.diskStorage({
    // Place of picture
    destination: (request, file, callback) => {
        callback(null, 'storage_product/');
    },
    filename: (request, file, callback) => {
        const avatarName = Date.now() + file.originalname;
        callback(null, avatarName);
    }
});

const uploadImage = multer({
    storage: storage,
});


// import file
const database = require("../../config")


// Read All products
router.get("/", (request, response) => {
    const query = "SELECT * FROM products"
    database.query(query, (error, result) => {
        if(error) throw error;
        response.status(200).json(result)
    })
});

// Read products by category
router.get("/", (request, response) => {
    const category = request.param.category

    const query = "SELECT * FROM products WHERE category = ?";
    const args = [category]

    database.query(query, args, (error, result) => {
        if(error) throw error
        response.status(200).json(result)
    });
}); 

module.exports = router