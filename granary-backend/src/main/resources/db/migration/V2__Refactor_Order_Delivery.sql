ALTER TABLE orders DROP COLUMN IF EXISTS delivery_method;
ALTER TABLE orders DROP CONSTRAINT IF EXISTS orders_delivery_status_check;
ALTER TABLE orders
ADD CONSTRAINT orders_delivery_status_check CHECK (delivery_status IN ('pending', 'delivered'));