# clone source

git clone

# install mavent

mvn install

# database (postgresql)

CREATE TABLE ad_type_vehicle
(
ad_type_vehicle_id SERIAL PRIMARY KEY,
name VARCHAR(100)            NOT NULL,
description VARCHAR(255),
created_at TIMESTAMP DEFAULT NOW() NOT NULL,
updated_at TIMESTAMP DEFAULT NOW() NOT NULL,
isactive BOOLEAN DEFAULT TRUE NOT NULL
);

CREATE TABLE ad_user
(
ad_user_id SERIAL PRIMARY KEY,
username VARCHAR(100)            NOT NULL UNIQUE,
password_hash VARCHAR(1024)           NOT NULL,
email VARCHAR(50),
ad_role_id INT NOT NULL,
created_at TIMESTAMP DEFAULT NOW() NOT NULL,
updated_at TIMESTAMP DEFAULT NOW() NOT NULL,
isactive BOOLEAN DEFAULT TRUE NOT NULL,
FOREIGN KEY (ad_role_id) REFERENCES ad_role (ad_role_id)
);

CREATE TABLE ad_vehicle
(
ad_vehicle_id SERIAL PRIMARY KEY,
vehicle_name VARCHAR(100)            NOT NULL,
description VARCHAR(255),
ad_user_id INT NOT NULL,
brand VARCHAR(20),
ad_type_vehicle_id INT NOT NULL,
model VARCHAR(10),
style VARCHAR(20),
license_plate VARCHAR(10),
year INT,
created_at TIMESTAMP DEFAULT NOW() NOT NULL,
updated_at TIMESTAMP DEFAULT NOW() NOT NULL,
isactive BOOLEAN DEFAULT TRUE NOT NULL,
FOREIGN KEY (ad_user_id) REFERENCES ad_user (ad_user_id),
FOREIGN KEY (ad_type_vehicle_id) REFERENCES ad_type_vehicle (ad_type_vehicle_id)
);

CREATE TABLE ad_maintenance_vehicle
(
ad_maintenance_vehicle_id SERIAL PRIMARY KEY,
name_maintenance VARCHAR(100)            NOT NULL,
ad_vehicle_id INT NOT NULL,
description VARCHAR(255),
cost DECIMAL(10, 2),
discount DECIMAL(10, 2),
final_cost DECIMAL(10, 2),
service_date TIMESTAMP,
created_at TIMESTAMP DEFAULT NOW() NOT NULL,
updated_at TIMESTAMP DEFAULT NOW() NOT NULL,
isactive BOOLEAN DEFAULT TRUE NOT NULL,
FOREIGN KEY (ad_vehicle_id) REFERENCES ad_vehicle (ad_vehicle_id)
);

CREATE TABLE ad_maintenance_cost_detail
(
ad_maintenance_cost_detail_id SERIAL PRIMARY KEY,
ad_maintenance_vehicle_id INT NOT NULL,
description VARCHAR(255),
cost DECIMAL(10, 2)          NOT NULL,
discount DECIMAL(10, 2),
final_cost DECIMAL(10, 2)          NOT NULL,
created_at TIMESTAMP DEFAULT NOW() NOT NULL,
updated_at TIMESTAMP DEFAULT NOW() NOT NULL,
isactive BOOLEAN DEFAULT TRUE NOT NULL,
FOREIGN KEY (ad_maintenance_vehicle_id) REFERENCES ad_maintenance_vehicle (ad_maintenance_vehicle_id)
);

# config (application.properties)

spring.application.name=VehicleManagement

spring.datasource.url=jdbc:postgresql://localhost:5432/vehiclesys
spring.datasource.username=postgres
spring.datasource.password=truongtran
spring.datasource.driver-class-name=org.postgresql.Driver

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.PostgreSQLDialect

logging.level.root=INFO
logging.level.com.example=DEBUG
logging.file.name=logs/application.log

jwt.secret=dtjik_secret_key
jwt.expiration=36000000

# **** API enpoints ****

# User Registration

    URL: /api/users/register
    Method: POST
    Authorization: token
    Request Body

    username(String,required)
    password(String,required) betweent 8 and 255 charracter
    roleId (Long,required)

    Ex:

    {
        "username": "",
        "password": "",
        "roleId" :
    }
    
    Response:

    Success: 200 OK
    
        {
            "message": "success",
            "data": create user success: " + id"
        }
    
    Failure: 400 Bad Request
    
        {
            "message": "fail",
            "data": info error
        }

# Login

    URL: /api/users/login
    Method: POST
    Request Body

    username(String,required)
    password(String,required)
    
    {
        "username": "",
        "password": ""
    }
    
    Response:

    Success: 200 OK
    
    {
        "jwt": ""
    }
    
    Failure: 400 Bad Request/500 Internal Server Error
    
    {
        "message": "fail",
        "data": info error
    }

# Add Vehicle

    URL: /api/vehicles/add
    Method: POST
    Authorization: token
    Request Body

    name(String,required)
    description(String)
    userOwnerId(Long)
    branch (String)
    typeVehicle (Long,required)
    model (String)
    style(String)
    license_plate(String)
    year(Int)
    
    {
        "name": "",
        "description": "",
        "userOwnerId": null,
        "branch": "",
        "typeVehicle": null,
        "model": "",
        "style": "",
        "license_plate": "",
        "year": 0
    }

    Response:
    Success: 200 OK
    
    {
        "message": "success",
        "data":create vehicle success: " + id"
    }
    
    Failure: 400 Bad Request/500 Internal Server Error
    
    {
        "message": "fail",
        "data": info error
    }

# View Vehicles

    URL: /api/vehicles/getinfo
    Method: GET
    Authorization: token
    Request Params
    
    nameVehicle(String,required)
    
    Response:

    vehicleId(Long)
    vehicleName(String)
    description(String)
    user(String)
    type(String)
    brand(String)
    model(String)
    style(String)
    licensePlate(String)
    year(Int)
    createdAt(TimeStamp)
    updatedAt(TimeStamp)
    maintenanceVehicleReponseList: List

    Success: 200 OK
    
    {
        "message": "success",
        "data": {
            "vehicleId": null,
            "vehicleName": "",
            "description": "",
            "user": "",
            "type": "",
            "brand": "",
            "model": "",
            "style": "",
            "licensePlate": "",
            "year": 0,
            "createdAt": null,
            "updatedAt": null,
            "maintenanceVehicleReponseList": []
        }
    }

    Failure: 400 Bad Request/500 Internal Server Error
    
    {
        "message": "fail",
        "data": info error
    }

# Update Vehicle

    URL: /api/vehicles/update
    Method: PUT
    Authorization: token
    Request Body

    vehicleId(Long,Required)
    vehicleName(String)
    description(String)
    user(String)
    type(String)
    brand(String)
    model(String)
    style(String)
    licensePlate(String)
    year(Int)
    createdAt(TimeStamp)
    updatedAt(TimeStamp)
    maintenanceVehicleReponseList: List
    {
        "vehicleId": null,
        "vehicleName": "",
        "description": "",
        "user": "",
        "type": "",
        "brand": "",
        "model": "",
        "style": "",
        "licensePlate": "",
        "year": 0,
        "createdAt": null,
        "updatedAt": null,
        "maintenanceVehicleReponseList": []
    }

    Response:
    Success: 200 OK
    
    {
        "message": "success",
        "data":  “Update vehicle success: “ + id
    }
    
    Failure: 400 Bad Request/500 Internal Server Error
    
    {
        "message": "fail",
        "data": info error
    }

# delete vehicle

    URL: /api/vehicles/remove
    Method: DELETE
    Authorization: token
    PathVariable
    id (Long,Required)
    
    Response:
    Success: 200 OK
    
    {
        "message": "success",
        "data": “delete vehicle success:” + id
    }
    
    Failure: 400 Bad Request/500 Internal Server Error
    
    {
        "message": "fail",
        "data": info error
    }

# Add Maintenance Record

    URL: /api/maintenance/add
    Method: POST
    Authorization: token
    Request Body

    nameMaintenance(String, required)
    vehicleId(Long, required)
    description(String)
    cost(double)
    discount(double)
    finalCost(double)
    serviceDate(TimeStamp)

    {
        "nameMaintenance": "",
        "vehicleId": null,
        "description": "",
        "cost": 0.0,
        "discount": 0.0,
        "finalCost": 0.0,
        "serviceDate": null
    }

    Response:
    Success: 200 OK
    
    {
        "message": "success",
        "data": Add maintenance vehicle success: " + id"
    }
    
    Failure: 400 Bad Request/500 Internal Server Error
    
    {
        "message": "fail",
        "data": info error
    }

# View Maintenance Records

    URL: /api/maintenance/getinfo
    Method: GET
    Authorization: token
    Request Params

    vehicleId : Long
    
    Response:
    maintenanceVehicleId(Long)
    nameMaintenance(String, required)
    vehicleId(Long, required)
    description(String)
    cost(double)
    discount(double)
    finalCost(double)
    serviceDate(TimeStamp)
    createdAt(TimeStamp)
    updateAt(TimeStamp)
    isActive(boolean)

    Success: 200 OK
    
    {
        "message": "success",
        "data": {
            "maintenanceVehicleId": null,
            "nameMaintenance": "",
            "vehicleId": null,
            "description": "",
            "cost": 0.0,
            "discount": 0.0,
            "finalCost": 0.0,
            "serviceDate": null,
            "createdAt": null,
            "updateAt": null,
            "isActive": false
            
        }
    }

    Failure: 400 Bad Request/500 Internal Server Error
    
    {
        "message": "fail",
        "data": info error
    }

# Update Maintenance Record

    URL: /api/maintenance/update
    Method: PUT
    Authorization: token
    Request Body

    id(Long, required)
    nameMaintenance(String)
    vehicleId(Long, required)
    description(String)
    cost(double)
    discount(double)
    finalCost(double)
    serviceDate(TimeStamp)

    {
        "id": ,
        "nameMaintenance": "",
        "vehicleId": null,
        "description": "",
        "cost": 0.0,
        "discount": 0.0,
        "finalCost": 0.0,
        "serviceDate": null
    }

    Response:
    Success: 200 OK
    
    {
        "message": "success",
        "data":  “Update maintenance vehicle success: “ + id
    }
    
    Failure: 400 Bad Request/500 Internal Server Error
    
    {
        "message": "fail",
        "data": info error
    }

