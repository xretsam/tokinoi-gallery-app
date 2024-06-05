INSERT INTO users (username, password, email, phone_number, profile_picture, role, status, created_at, updated_at, last_login) VALUES
('john_doe', 'password123', 'john.doe@example.com', '123-456-7890', 'john_profile.jpg', 'USER', 'ACTIVE', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('jane_smith', 'password456', 'jane.smith@example.com', '234-567-8901', 'jane_profile.jpg', 'USER', 'ACTIVE', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('admin_user', 'adminPass789', 'admin@example.com', '345-678-9012', 'admin_profile.jpg', 'ADMIN', 'ACTIVE', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('alice_brown', 'alicePass101', 'alice.brown@example.com', '456-789-0123', 'alice_profile.jpg', 'USER', 'INACTIVE', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, NULL),
('bob_white', 'bobPass202', 'bob.white@example.com', '567-890-1234', 'bob_profile.jpg', 'USER', 'ACTIVE', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('charlie_black', 'charliePass303', 'charlie.black@example.com', '678-901-2345', 'charlie_profile.jpg', 'USER', 'BANNED', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, NULL),
('david_green', 'davidPass404', 'david.green@example.com', '789-012-3456', 'david_profile.jpg', 'MODERATOR', 'ACTIVE', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('eve_yellow', 'evePass505', 'eve.yellow@example.com', '890-123-4567', 'eve_profile.jpg', 'USER', 'ACTIVE', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('frank_red', 'frankPass606', 'frank.red@example.com', '901-234-5678', 'frank_profile.jpg', 'USER', 'ACTIVE', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('grace_blue', 'gracePass707', 'grace.blue@example.com', '012-345-6789', 'grace_profile.jpg', 'USER', 'ACTIVE', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO gallery (title, description, created_at, updated_at, status, thumbnail, user_id, category_id) VALUES
('Sunset Over the Mountains', 'A beautiful sunset captured over the mountains.', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'ACTIVE', 'sunset_thumbnail.jpg', 1, NULL),
('City Lights', 'The city comes alive with lights at night.', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'ACTIVE', 'city_lights_thumbnail.jpg', 1, NULL),
('Forest Adventure', 'Exploring the dense forest and its hidden treasures.', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'ACTIVE', 'forest_adventure_thumbnail.jpg', 1, NULL),
('Ocean Waves', 'Waves crashing on the shore during a storm.', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'INACTIVE', 'ocean_waves_thumbnail.jpg', 1, NULL),
('Desert Dunes', 'Golden dunes stretching as far as the eye can see.', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'ACTIVE', 'desert_dunes_thumbnail.jpg', 1, NULL),
('Snowy Mountains', 'Snow-covered peaks under a clear blue sky.', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'ACTIVE', 'snowy_mountains_thumbnail.jpg', 1, NULL),
('Autumn Leaves', 'The forest floor covered in vibrant autumn leaves.', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'INACTIVE', 'autumn_leaves_thumbnail.jpg', 1, NULL),
('Spring Blossoms', 'Cherry blossoms in full bloom during spring.', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'ACTIVE', 'spring_blossoms_thumbnail.jpg', 1, NULL),
('Wildlife Safari', 'A close-up of a lion during a safari.', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'ACTIVE', 'wildlife_safari_thumbnail.jpg', 1, NULL),
('Starry Night', 'A clear night sky filled with stars.', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'ACTIVE', 'starry_night_thumbnail.jpg', 1, NULL);
