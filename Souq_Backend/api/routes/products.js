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
    const category = request.params.category

    const query = "SELECT * FROM products WHERE category = ?";
    const args = [category]

    database.query(query, args, (error, result) => {
        if(error) throw error
        response.status(200).json(result)
    });
}); 


// Insert Product
router.post("/insert",uploadImage.single('image'), (request, response) => {
    const name = request.body.name
    const price = request.body.price
    const quantity = request.body.quantity
    const supplier = request.body.supplier
    const category = request.body.category
    
    const file = request.file;
    var filePath = ""
    if(file != null){
        filePath = file.path
    }
   
    const query = "INSERT INTO products(name, price, quantity, supplier, category, image) VALUES(?, ?, ?, ?, ?,?)"
        
    const args = [name, price, quantity, supplier, category, filePath]

        database.query(query, args, (error, result) => {
            if (error) throw error
            response.status(200).send("Product Inserted")
        });
});

// Delete Product
router.delete("/:id", (request, response) => {
    const id = request.params.id;
    const query = "DELETE FROM products WHERE id = ?"
    const args = [id]

    database.query(query, args, (error, result) => {
        if(error) throw error
        response.status(200).send("Product is deleted")
    });
});

module.exports = router