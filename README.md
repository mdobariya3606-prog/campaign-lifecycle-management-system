# Sale Campaign Management System API

Backend system for managing promotional campaigns and dynamic product pricing during sale periods. The system supports campaign-based discounts, price history tracking, and paginated product retrieval for large catalogs.

## Features

- Manage large product catalog (~100,000 products)
- Paginated product API for efficient data retrieval
- Create sale campaigns with product-specific discounts
- Automatic price adjustment during active campaigns
- Price reverts to original value after campaign ends
- Store pricing history of each product
- View past, current, and upcoming campaigns
- RESTful API design
- Scalable database structure

## Tech Stack

- Java
- Spring Boot
- Spring Data JPA
- MySQL
- REST APIs
- Maven

## Product Properties

- product id
- title
- maximum retail price (MRP)
- current price
- discount percentage
- inventory count

## Campaign Properties

- campaign title
- start date
- end date
- campaign discount
- product-specific discount mapping

## API Endpoints

### Product APIs

GET /products?page=1&pageSize=10  
Returns paginated list of products including pricing and inventory.

GET /products/{id}  
Returns product details including current price with applied discount.

GET /products/{id}/price-history  
Returns pricing history for a product.

---

### Campaign APIs

POST /campaigns  
Create new sale campaign with product discounts.

GET /campaigns  
Get all campaigns (past, current, upcoming).

GET /campaigns/{id}  
Get campaign details.

PUT /campaigns/{id}  
Update campaign information.

DELETE /campaigns/{id}  
Delete campaign.

---

## Pricing Logic

Discount is applied on MRP (Maximum Retail Price).

Example:

MRP = 1000  
Campaign Discount = 10%

Final Price during campaign:

1000 - (10% of 1000) = 900

After campaign ends:
Price returns to original price stored before campaign.

The system ensures:
- discount calculation always uses MRP
- original pricing is preserved
- price history is stored for tracking changes

## Architecture

Controller → handles HTTP requests and responses  

Service → business logic for campaign discount calculation and price updates  

Repository → database interaction using JPA  

Entity → database model representing products, campaigns, and price history  

---

## Database Design (concept)

Tables:

products  
campaigns  
campaign_products  
price_history  

Relationships:

One campaign can apply discounts to multiple products.  
Each product maintains price history for tracking changes over time.

---

## Use Cases

- Retrieve products with current sale price applied
- Apply discount to multiple products during campaign
- Track product price changes
- Manage future campaigns
- Handle large product catalog efficiently using pagination

---

## Future Improvements

- validation for overlapping campaigns
- role-based access control (Admin)
- scheduled activation of campaigns
- dashboard for campaign analytics
- caching for faster product retrieval
- Docker deployment
