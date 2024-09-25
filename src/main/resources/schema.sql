CREATE TABLE IF NOT EXISTS `users` (
    `user_id` BINARY(16) PRIMARY KEY NOT NULL,
    `account` VARCHAR(255) UNIQUE NOT NULL,
    `password` VARCHAR(255) NOT NULL,
    `role` ENUM('ROLE_ADMIN', 'ROLE_MEMBER') NOT NULL
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE IF NOT EXISTS `categories` (
    `category_id` BIGINT AUTO_INCREMENT PRIMARY KEY NOT NULL,
    `category_name` VARCHAR(255) NOT NULL,
    `created_at` DATETIME(6) NOT NULL,
    `deleted_at` DATETIME(6) DEFAULT NULL,
    `updated_at` DATETIME(6) DEFAULT NULL,
    `user_id` BINARY(16) DEFAULT NULL,
    FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`)
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE IF NOT EXISTS `budgets` (
    `budget_id` BIGINT AUTO_INCREMENT PRIMARY KEY NOT NULL,
    `amount` BIGINT NOT NULL,
    `created_at` DATETIME(6) NOT NULL,
    `deleted_at` DATETIME(6) DEFAULT NULL,
    `updated_at` DATETIME(6) DEFAULT NULL,
    `category_id` BIGINT DEFAULT NULL,
    `user_id` BINARY(16) DEFAULT NULL,
    FOREIGN KEY (`category_id`) REFERENCES `categories` (`category_id`),
    FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`)
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE IF NOT EXISTS `expenses` (
    `expense_id` BIGINT AUTO_INCREMENT PRIMARY KEY NOT NULL,
    `amount` BIGINT NOT NULL,
    `created_at` DATETIME(6) NOT NULL,
    `deleted_at` DATETIME(6) DEFAULT NULL,
    `exclude_from_total` BIT(1) NOT NULL,
    `expensed_at` DATE NOT NULL,
    `memo` VARCHAR(200) NOT NULL,
    `updated_at` DATETIME(6) DEFAULT NULL,
    `category_id` BIGINT NOT NULL,
    `user_id` BINARY(16) NOT NULL,
    FOREIGN KEY (`category_id`) REFERENCES `categories` (`category_id`),
    FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`)
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

