const express = require('express')
const bodyParser = require('body-parser')

const app = express()

// User can read pictures from it
app.use('/storage_user', express.static('storage_user'));
app.use('/storage_product', express.static('storage_product'));

// Import my file
const userRouter = require('./api/routes/users')
const productRouter = require('./api/routes/products')
const favoriteRouter = require('./api/routes/favorites')
const cartRouter = require('./api/routes/carts')
const historyRouter = require('./api/routes/history')


const port = 3000

// Middleware
app.use(bodyParser.urlencoded({ extended: false }))
app.use(bodyParser.json())

// Use methods from my file
app.use('/users', userRouter)
app.use('/products',productRouter)
app.use('/favorites',favoriteRouter)
app.use('/carts',cartRouter)
app.use('/history',historyRouter)


// Make my server work on port 3000 and listen when user use it
app.listen(port, () => console.log("Server Started"))


