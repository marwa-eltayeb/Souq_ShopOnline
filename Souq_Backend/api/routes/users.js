const express = require('express')
const router = express.Router()
// For Token
const jwt = require('jsonwebtoken')
// For encrypted password
const bcrypt = require('bcrypt');
// Upload and store images
const multer = require('multer')

const storage = multer.diskStorage({
    // Place of picture
    destination: (request, file, callback) => {
        callback(null, 'storage_user/');
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

// Get All users
router.get("/", (request, response) => {
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
        parseInt(page_size),
        parseInt(page)
    ];

    const query = "SELECT * FROM users LIMIT ? OFFSET ?"
    database.query(query, args, (error, result) => {
        if(error) throw error;
        response.status(200).json({
            "error" : false,
            "users" : result
        })

    })
});

// Login
router.get("/login", (request, response) => {
    const email = request.query.email
    const password = request.query.password
    const query = "SELECT password, id FROM users WHERE email = ?";
    const args = [email]
    database.query(query, args, (error, result) => {
        if(error) throw error
        if(result.length == 1){
            const dataPassword = result[0]['password'];
            // Compare two passwords
            bcrypt.compare(password, dataPassword, (err, isSame) => {
                if(isSame){
                    // Return Token
                    jwt.sign(email, "key", (err, token) => {
                        if (err) throw err;
                        response.status(200).json({"id" : result[0]["id"], "error" : false, "message" : "Successful Login","token" : token});
                    });
                }else{
                    response.status(500).send("Invalid Password")
                }
            });
        }else{
            response.status(500).send("Invalid Email")
        }
    });
});

// Insert User
router.post("/register",uploadImage.single('image'), (request, response) => {
    const name = request.body.name
    const email = request.body.email
    const password = request.body.password
    var gender = request.body.gender
    var age = request.body.age

    const checkQuery = "SELECT id FROM users WHERE email = ?"
    database.query(checkQuery, email , (error, result)  => {
        if(error) throw error;
        if(result.length != 0){
            response.status(401).json({
                "error" : true,
                "message" : "User Already Registered"
            })
        }else{

            // Register new user
            if(typeof gender == 'undefined' && gender == null){
                gender = "undertermined";
            }
        
            if(typeof age == 'undefined' && age == null){
                age = 0;
            }
        
            const file = request.file;
            var filePath = ""
            if(file != null){
                filePath = file.path
            }
        
            if(password.length < 8){
                response.status(500).send("Invalid Password")
            }
                
            const query = "INSERT INTO users(name, email, password, gender, age, image) VALUES(?, ?, ?, ?, ?,?)"
                
            // Encrypt Password
            bcrypt.hash(password, 10, (error, hashedPassword) => {
                if(error) throw error
        
                const args = [name, email, hashedPassword, gender, age, filePath]
        
                database.query(query, args, (error, result) => {
                    if (error) throw error
                    response.status(200).json({
                        "id" : result.insertId,
                        "error" : false,
                        "message" : "Register Done"
                    })
                });
            });
        }
    });
});
    
// Delete User
router.delete("/:id", (request, response) => {
    const id = request.params.id;
    const query = "DELETE FROM users WHERE id = ?"
    const args = [id]

    database.query(query, args, (error, result) => {
        if(error) throw error
        response.status(200).send("User is deleted")
    });
});
 
// Update two Info
router.put("/info", (request, response) => {
    const id = request.body.id;
    const name = request.body.name;
    const password = request.body.password;

    const query = "UPDATE users SET name = ?, password = ? WHERE id = ?"    
   
    // Encrypt Password
    bcrypt.hash(password, 10, (error, hashedPassword) => {
        if(error) throw error

        const args = [name,hashedPassword,id]

        database.query(query, args, (error, result) => {
            if(result['affectedRows']  == 1){
                response.status(200).send("User Info is updated")
            }else{
                response.status(500).send("Invalid Update")
            }
        });

    });
});

// Update image of user
router.put("/update", uploadImage.single('image'), (request, response) => {
    const id = request.body.id;
    
    const file = request.file;
    var filePath = ""
    if(file != null){
        filePath = file.path
    }

    const selectQuery = "SELECT image FROM users WHERE id = ?"
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

    const query = "UPDATE users SET image = ? WHERE id = ?"  
    
    const args = [filePath,id]

    database.query(query, args, (error, result) => {
        if(error) throw error

        if(result['affectedRows']  == 1){
            response.status(200).send("User Image is updated")
        }else{
            response.status(500).send("Invalid Update")
        }
    });

});

module.exports = router