CREATE TABLE `persons`
(
    `id`         INT         NOT NULL,
    `first_name` VARCHAR(45) NULL,
    `last_name`  VARCHAR(45) NULL,
    PRIMARY KEY (`id`),
    UNIQUE INDEX `id_UNIQUE` (`id` ASC) VISIBLE
);

