const express = require('express')
const router = express.Router()


// Deal with file
const fileSystem = require('fs');

// Upload and store images
const multer = require('multer')

const storage = multer.diskStorage({
    // Place of picture
    destination: (request, file, callback) => {
        callback(null, 'storage_poster/');
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


// Insert Poster
router.post("/insert", uploadImage.single('image'), (request, response) => {
    const file = request.file;
    var filePath = ""
    if(file != null){
        filePath = file.path
    }
    
    const query = "INSERT INTO poster(image) VALUES(?)"
    const args = [filePath]

    database.query(query,args, (error, result) => {
        if (error) throw error
        
        response.status(200).send("Poster Inserted")
    });
});

// Get posters
router.get("/", (request, response) => {
   
    const query = "SELECT * FROM poster";

    database.query(query, (error, result) => {
        if(error) throw error
        response.status(200).json({
            "error" : false,
            "posters" : result,
        })
    });
}); 


// Update Poster
router.put("/update", uploadImage.single('image'), (request, response) => {
    const id = request.body.id;
    console.log(id)

    const file = request.file;
    var filePath = ""
    if(file != null){
        filePath = file.path
    }

    const selectQuery = "SELECT image FROM poster WHERE poster_id = ?"
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

    const query = "UPDATE poster SET image = ? WHERE poster_id = ?"  
    
    const args = [filePath,id]

    database.query(query, args, (error, result) => {
        if(error) throw error

        if(result['affectedRows']  == 1){
            response.status(200).send("Poster updated")
        }else{
            response.status(500).send("Invalid Update")
        }
    });
});


// Delete Poster
router.delete("/:id", (request, response) => {
    const id = request.params.id;
    const query = "DELETE FROM poster WHERE poster_id = ?"
    const args = [id]

    database.query(query, args, (error, result) => {
        if(error) throw error
        response.status(200).send("Poster deleted")
    });
});

module.exports = router