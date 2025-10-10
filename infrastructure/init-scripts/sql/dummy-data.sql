-- ============================================
-- DUMMY DATA FOR KEEPDISHGOING
-- ============================================

DELETE FROM kdg_restaurants.dishes;
DELETE FROM kdg_restaurants.restaurants;
DELETE FROM kdg_restaurants.owners;


-- Insert restaurant
INSERT INTO kdg_restaurants.restaurants (restaurant_id, owner_id, name, address, email, picture_url, cuisine, preparation_time, opening_time, created_at, updated_at)
VALUES ('660e8400-e29b-41d4-a716-446655440000',
        '550e8400-e29b-41d4-a716-446655440000',
        'Bella Italia',
        '123 Main Street, City',
        'info@bellaitalia.com',
        'https://example.com/restaurant.jpg',
        'ITALIAN',
        '00:30:00',
        '11:00:00',
        NOW(),
        NOW());

-- Insert dishes
INSERT INTO kdg_restaurants.dishes (dish_id, restaurant_id, dish_name, dish_type, description, price, picture_url, state, created_at, updated_at)
VALUES
    ('770e8400-e29b-41d4-a716-446655440001', '660e8400-e29b-41d4-a716-446655440000', 'Margherita Pizza', 'MAIN_COURSE', 'Classic pizza with tomato and mozzarella', 12.50, 'https://example.com/pizza.jpg', 'PUBLISHED', NOW(), NOW()),
    ('770e8400-e29b-41d4-a716-446655440002', '660e8400-e29b-41d4-a716-446655440000', 'Caesar Salad', 'APPETIZER', 'Fresh romaine lettuce with parmesan', 8.50, 'https://example.com/salad.jpg', 'PUBLISHED', NOW(), NOW());

-- Insert food tags
INSERT INTO kdg_restaurants.dish_food_tags (dish_id, food_tag)
VALUES
    ('770e8400-e29b-41d4-a716-446655440001', 'PIZZA'),
    ('770e8400-e29b-41d4-a716-446655440001', 'ITALIAN'),
    ('770e8400-e29b-41d4-a716-446655440002', 'VEGETARIAN');