const express = require('express')
const router = express.Router()

// Deal with file
const fileSystem = require('fs');

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


// Get All products
router.get("/", (request, response) => {
    var page = request.query.page;
    var page_size = request.query.page_size;

    console.log(typeof page);

    if(page == null){
        page = 0;
     }
 
     if(page_size == null){
        page_size = 20;
     }

     const args = [
        parseInt(page_size),
        parseInt(page)
    ];
    
    const query = "SELECT * FROM products LIMIT ? OFFSET ?"
    database.query(query,args, (error, result) => {
        if(error) throw error;
        response.status(200).json({
            "error" : false,
            "products" : result
        })
    })
});

// Get products by category
router.get("/", (request, response) => {
    const category = request.params.category
    var page = request.query.page;
    var page_size = request.query.page_size;

    console.log(typeof page);

    if(page == null){
        page = 0;
     }
 
    if(page_size == null){
        page_size = 20;
    }

    const args = [
        category,
        parseInt(page_size),
        parseInt(page)
    ];

    const query = "SELECT * FROM products WHERE category = ? LIMIT ? OFFSET ?";

    database.query(query, args, (error, result) => {
        if(error) throw error
        response.status(200).json({
            "error" : false,
            "products" : result
        })
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

// Update image of product
router.put("/update", uploadImage.single('image'), (request, response) => {
    const id = request.body.id;
    
    const file = request.file;
    var filePath = ""
    if(file != null){
        filePath = file.path
    }

    const selectQuery = "SELECT image FROM products WHERE id = ?"
    database.query(selectQuery, id, (error, result) => {

        console.log(result)
        if(error) throw error
        try {
            // Get value from key image
            var image = result[0]['image'];
            // Delete old image 
            fileSystem.unlinkSync(image);
        } catch (err) {
            console.error("Can't find file in storage/pictures Path");
        }
    });

    const query = "UPDATE products SET image = ? WHERE id = ?"  
    
    const args = [filePath,id]

    database.query(query, args, (error, result) => {
        if(error) throw error

        if(result['affectedRows']  == 1){
            response.status(200).send("Product Image is updated")
        }else{
            response.status(500).send("Invalid Update")
        }
    });

});

module.exports = router