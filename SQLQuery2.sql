USE ElectricDevicesDB

CREATE TABLE Rooms
(
    roomId   int          NOT NULL
        CONSTRAINT PK_Rooms_roomId PRIMARY KEY,
    roomName nvarchar(20) NOT NULL,
);

CREATE TABLE Devices
(
    deviceId             int          NOT NULL
        CONSTRAINT PK_Devices_deviceId PRIMARY KEY,
    deviceName           nvarchar(20) NOT NULL,
    electricPowerDefault float        NOT NULL,
    isActive             bit          NOT NULL,
    roomID               int          NOT NULL
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
VALUES (0245, 'Microwave', 800, 1, 1),
       (6812, 'Coffee machine', 1500, 1, 1),
       (6827, 'Toaster', 800, 0, 1),
       (9485, 'Humidifier', 30, 0, 2),
       (6543, 'Table LED lamp', 4, 1, 2),
       (8132, 'Electronic clock', 6, 0, 2),
       (3983, 'PS5', 800, 0, 3),
       (5132, 'TV', 500, 0, 3),
       (7915, 'Musical center', 600, 1, 3)