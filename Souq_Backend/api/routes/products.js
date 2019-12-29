const express = require('express')

const router = express.Router()


router.get("/products", (request, response) => {
    response.send("Get Products")
});











module.exports = router