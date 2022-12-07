USE ElectricDevicesDB

CREATE TABLE Rooms
(
    room_Id   int          NOT NULL
        CONSTRAINT PK_Rooms_roomId PRIMARY KEY,
    room_name nvarchar(20) NOT NULL,
);

CREATE TABLE Devices
(
    device_id              int          NOT NULL
        CONSTRAINT PK_Devices_deviceId PRIMARY KEY,
    device_name            nvarchar(20) NOT NULL,
    electric_power_default float        NOT NULL,
    is_active              bit          NOT NULL,
    roomID                 int          NOT NULL
        CONSTRAINT FK_Rooms_Devices FOREIGN KEY
            REFERENCES Rooms (room_Id)
            ON DELETE NO ACTION
            ON UPDATE NO ACTION,
);

INSERT INTO Rooms
VALUES (1, 'Kitchen'),
       (2, 'Bedroom'),
       (3, 'LivingRoom')

INSERT INTO Devices
VALUES (1245, 'Microwave', 800, 1, 1),
       (6812, 'Coffee machine', 1500, 1, 1),
       (6827, 'Toaster', 800, 0, 1),
       (9485, 'Humidifier', 30, 0, 2),
       (6543, 'Table LED lamp', 4, 1, 2),
       (8132, 'Electronic clock', 6, 0, 2),
       (3983, 'PS5', 800, 0, 3),
       (5132, 'TV', 500, 0, 3),
       (7915, 'Musical center', 600, 1, 3)