-- Enable UUID generation
CREATE EXTENSION IF NOT EXISTS "pgcrypto";

-- Products table
CREATE TABLE products (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    name VARCHAR(100) NOT NULL,
    size VARCHAR(20) NOT NULL,
    price_cents INT NOT NULL,
    stock_quantity INT NOT NULL DEFAULT 0,
    low_stock_alert INT DEFAULT 10,
    image_url VARCHAR(500),
    is_active BOOLEAN DEFAULT true,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Orders table
CREATE TABLE orders (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    product_id UUID REFERENCES products(id),
    quantity_ordered INT NOT NULL,
    total_amount_cents INT NOT NULL,
    customer_name VARCHAR(100) NOT NULL,
    customer_phone VARCHAR(20) NOT NULL,
    customer_email VARCHAR(100) NOT NULL,
    customer_address TEXT NOT NULL,
    delivery_method VARCHAR(20) CHECK (delivery_method IN ('pickup', 'delivery')),
    payment_status VARCHAR(20) DEFAULT 'pending'
        CHECK (payment_status IN ('pending','paid','failed')),
    order_status VARCHAR(20) DEFAULT 'new'
        CHECK (order_status IN ('new','packaging','ready','delivered')),
    mpesa_transaction_id VARCHAR(100),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

