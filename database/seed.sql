INSERT INTO users(name,email,password_hash,role)
VALUES ('ShopFlow Admin','admin@shopflow.local',
'$2a$10$7EqJtq98hPqEX7fNZaFWoO8q9L4sJ1zFJ7YvX4n7fGxYwJ5JmQq4u','ADMIN')
ON CONFLICT(email) DO NOTHING;

INSERT INTO categories(name) VALUES ('Electronics'),('Accessories'),('Wearables')
ON CONFLICT(name) DO NOTHING;

INSERT INTO brands(name) VALUES ('ShopFlow'),('NovaTech'),('Pulse')
ON CONFLICT(name) DO NOTHING;

INSERT INTO products(name,description,price,stock,category_id,brand_id)
SELECT 'Wireless Headphones','Bluetooth over-ear headphones',2499,100,c.id,b.id
FROM categories c, brands b WHERE c.name='Electronics' AND b.name='NovaTech'
AND NOT EXISTS (SELECT 1 FROM products WHERE name='Wireless Headphones');

INSERT INTO products(name,description,price,stock,category_id,brand_id)
SELECT 'Mechanical Keyboard','RGB mechanical keyboard',3999,70,c.id,b.id
FROM categories c, brands b WHERE c.name='Electronics' AND b.name='ShopFlow'
AND NOT EXISTS (SELECT 1 FROM products WHERE name='Mechanical Keyboard');

INSERT INTO products(name,description,price,stock,category_id,brand_id)
SELECT 'Smart Watch','Fitness and notification smartwatch',5499,50,c.id,b.id
FROM categories c, brands b WHERE c.name='Wearables' AND b.name='Pulse'
AND NOT EXISTS (SELECT 1 FROM products WHERE name='Smart Watch');

INSERT INTO products(name,description,price,stock,category_id,brand_id)
SELECT 'USB-C Hub','Multi-port USB-C hub',1499,120,c.id,b.id
FROM categories c, brands b WHERE c.name='Accessories' AND b.name='ShopFlow'
AND NOT EXISTS (SELECT 1 FROM products WHERE name='USB-C Hub');

INSERT INTO coupons(code,discount_type,discount_value,minimum_cart_value,usage_limit,expires_at)
VALUES ('WELCOME10','PERCENTAGE',10,1000,100,CURRENT_TIMESTAMP + INTERVAL '365 days')
ON CONFLICT(code) DO NOTHING;
