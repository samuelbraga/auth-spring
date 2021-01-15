CREATE TABLE IF NOT EXISTS `user_profiles` (
    `user_id` BIGINT NOT NULL,
    `profiles_id` BIGINT NOT NULL,
    FOREIGN KEY (user_id) REFERENCES user(id),
    FOREIGN KEY (profiles_id) REFERENCES profile(ID)
);