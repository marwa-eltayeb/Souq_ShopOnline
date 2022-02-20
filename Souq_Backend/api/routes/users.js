const express = require('express')
const router = express.Router()
// For Token
const jwt = require('jsonwebtoken')
// For encrypted password
const bcrypt = require('bcrypt');
const checkAuth = require('../../middleware/check_auth');

// Deal with file
const fileSystem = require('fs');

// Upload and store images
const multer = require('multer')
// Send mail
const mail_util = require('../../utils/mail');

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

    const query = "SELECT * FROM user LIMIT ? OFFSET ?"
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
    const query = "SELECT id, password, name, email, if(isAdmin=1,  'true', 'false') as isAdmin FROM user WHERE email = ?";
    const args = [email]
    database.query(query, args, (error, result) => {
        if(error) throw error
        if(result.length == 1){
            const dataPassword = result[0]['password'];
            // Compare two passwords
            bcrypt.compare(password, dataPassword, (err, isSame) => {
                if(isSame){
                    // Return Token
                    jwt.sign(email, process.env.JWT_KEY, (err, token) => {
                        if (err) throw err;
                        response.status(200).json({
                           "id" : result[0]["id"],
                           "name" : result[0]["name"],
                           "email" : result[0]["email"],
                           "isAdmin" : result[0]["isAdmin"],
                           "error" : false, 
                           "message" : "Successful Login",
                           "password": password,
                           "token" : token});
                    });
                }else{
                    response.status(500).send("Invalid Password")
                }
            });
        }else{
            response.status(214).json({
                "error" : true, 
                "message" : "Account does not exist"});
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

    const checkQuery = "SELECT id FROM user WHERE email = ?"
    database.query(checkQuery, email , (error, result)  => {
        if(error) throw error;
        if(result.length != 0){
            response.status(217).json({
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
                
            const query = "INSERT INTO user(name, email, password, gender, age, image) VALUES(?, ?, ?, ?, ?,?)"
                
            // Encrypt Password
            bcrypt.hash(password, 10, (error, hashedPassword) => {
                if(error) throw error
        
                const args = [name, email, hashedPassword, gender, age, filePath]
        
                database.query(query, args, (error, result) => {
                    if (error) throw error
                    /*
                    response.status(200).json({
                        "id" : result.insertId,
                        "error" : false,
                        "message" : "Register Done"
                    })
                    */
                   const userQuery = "SELECT id, name, email, password, if(isAdmin=1,  'true', 'false') as isAdmin FROM user WHERE id = ?";
                   database.query(userQuery, result.insertId, (err, res) => {
                       if (error) throw error
                       response.status(200).json({
                           "error" : false,
                           "message" : "Register Done",
                           "user" : res[0],
                       })
                   })
                });
            });
        }
    });
});
    
// Delete User
router.delete("/:id", checkAuth, (request, response) => {
    const id = request.params.id;
    const query = "DELETE FROM user WHERE id = ?"
    const args = [id]

    database.query(query, args, (error, result) => {
        if(error) throw error
        response.status(200).send("Account is deleted")
    });
});
 
// Update Password
router.put("/info", checkAuth, (request, response) => {
    const id = request.query.id;
    const password = request.query.password;

    const query = "UPDATE user SET password = ? WHERE id = ?"    
   
    // Encrypt Password
    bcrypt.hash(password, 10, (error, hashedPassword) => {
        if(error) throw error

        const args = [hashedPassword,id]

        database.query(query, args, (error, result) => {
            if(result['affectedRows']  == 1){
                response.status(200).send("Password is updated")
            }else{
                response.status(500).send("Invalid Update")
            }
        });

    });
});

// Update image of user                 
router.put("/upload", checkAuth, uploadImage.single('image'), (request, response) => {
    const id = request.body.id;
    console.log(id);

    const file = request.file;
    var filePath = ""
    if(file != null){
        filePath = file.path
    }
    console.log(filePath);

    const selectQuery = "SELECT image FROM user WHERE id = ?"
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

    const query = "UPDATE user SET image = ? WHERE id = ?"  
    
    const args = [filePath,id]

    database.query(query, args, (error, result) => {
        if(error) throw error

        if(result['affectedRows']  == 1){
            response.status(200).send("User Photo is updated")
        }else{
            response.status(500).send("Invalid Update")
        }
    });

});


// Get Image
router.get("/getImage", (request, response) => {
    const id = request.query.id;
   
    const args = [id];

    const query = "SELECT image FROM user WHERE id = ?";

    database.query(query, args, (error, result) => {
        if(error) throw error
        response.status(200).json({
            "error" : false,
            "message" : "Setting Image",
            "image" : result[0]["image"],
        })
    });
}); 


// Get OTP
router.get("/otp", checkAuth, (request, response) => {
    const email = request.query.email

    const args = [email];

    const query = "SELECT email FROM user WHERE email = ?"
    database.query(query, args, (error, result) => {
        // Error in database
        if(error) throw error;

        // if email is correct
        if(result.length == 1) {

            const otp = mail_util.getRandomInt(100000, 999999)
            mail_util.sendOptMail(email, otp);
           
            response.status(200).json({
                "error" : false,
                "otp": otp,
                "email": email
            });

        } else{
            response.status(500).json({"error" : true,"message": "Incorrect Email"});
        }
    })
});


module.exports = router