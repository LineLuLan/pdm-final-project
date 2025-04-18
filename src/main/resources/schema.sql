-- CREATE DATABASE blood_donation;
-- USE blood_donation;
CREATE DATABASE IF NOT EXISTS blood_donation CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE blood_donation;

-- Create Patient table with additional useful fields
CREATE TABLE IF NOT EXISTS Patient (
    Pid INT PRIMARY KEY AUTO_INCREMENT,
    Name VARCHAR(255) NOT NULL,
    BloodType ENUM('A+', 'A-', 'B+', 'B-', 'AB+', 'AB-', 'O+', 'O-') NOT NULL,
    Age INT,
    Gender ENUM('Male', 'Female', 'Other'),
    MedicalHistory TEXT,
    RegistrationDate DATETIME DEFAULT CURRENT_TIMESTAMP,
    LastUpdated DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB;

-- Patient phone numbers (1-to-many relationship)
CREATE TABLE IF NOT EXISTS PatientPhones (
    Pid INT NOT NULL,
    Phone VARCHAR(20) NOT NULL,
    IsPrimary TINYINT(1) DEFAULT 0,
    PRIMARY KEY (Pid, Phone),
    FOREIGN KEY (Pid) REFERENCES Patient(Pid) ON DELETE CASCADE
) ENGINE=InnoDB;

-- Doctor information
CREATE TABLE IF NOT EXISTS Doctor (
    Did INT PRIMARY KEY AUTO_INCREMENT,
    Name VARCHAR(255) NOT NULL,
    LicenseNumber VARCHAR(50) UNIQUE,
    Specialization VARCHAR(100),
    Email VARCHAR(255) UNIQUE,
    Pid INT,
    FOREIGN KEY (Pid) REFERENCES Patient(Pid) ON DELETE SET NULL
) ENGINE=InnoDB;

-- Blood stock information
CREATE TABLE IF NOT EXISTS BloodStock (
    StockId INT PRIMARY KEY AUTO_INCREMENT,
    BloodType ENUM('A+', 'A-', 'B+', 'B-', 'AB+', 'AB-', 'O+', 'O-') NOT NULL,
    Quantity INT DEFAULT 0 CHECK (Quantity >= 0),
    Status ENUM('Available', 'Reserved', 'Expired') DEFAULT 'Available',
    ExpirationDate DATE NOT NULL,
    StorageLocation VARCHAR(100)
) ENGINE=InnoDB;

-- Blood bank information
CREATE TABLE IF NOT EXISTS BloodBank (
    Bid INT PRIMARY KEY AUTO_INCREMENT,
    Name VARCHAR(255) NOT NULL,
    ContactEmail VARCHAR(255),
    ContactPhone VARCHAR(20),
    Address TEXT NOT NULL,
    City VARCHAR(100),
    Country VARCHAR(100),
    PostalCode VARCHAR(20),
    LastInventoryDate DATE
) ENGINE=InnoDB;

-- Donor information with eligibility tracking
CREATE TABLE IF NOT EXISTS Donor (
    DonorId INT PRIMARY KEY AUTO_INCREMENT,
    Name VARCHAR(255) NOT NULL,
    Gender ENUM('Male', 'Female', 'Other'),
    Age INT CHECK (Age >= 18 AND Age <= 65),
    BloodType ENUM('A+', 'A-', 'B+', 'B-', 'AB+', 'AB-', 'O+', 'O-') NOT NULL,
    Weight DECIMAL(5,2) CHECK (Weight >= 50), -- Minimum weight requirement in kg
    LastDonationDate DATE,
    HealthStatus TEXT,
    IsEligible BOOLEAN DEFAULT TRUE,
    RegistrationDate DATETIME DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB;

-- Donor phone numbers
CREATE TABLE IF NOT EXISTS DonorPhone (
    DonorId INT NOT NULL,
    Phone VARCHAR(20) NOT NULL,
    IsPrimary TINYINT(1) DEFAULT 0,
    PRIMARY KEY (DonorId, Phone),
    FOREIGN KEY (DonorId) REFERENCES Donor(DonorId) ON DELETE CASCADE,
    CONSTRAINT chk_donor_phone_format CHECK (Phone REGEXP '^[0-9]{10,15}$')
) ENGINE=InnoDB;

-- Donation records
CREATE TABLE IF NOT EXISTS DonateTimes (
    DonationId INT PRIMARY KEY AUTO_INCREMENT,
    Bid INT NOT NULL,
    DonorId INT NOT NULL,
    DonationDate DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    Quantity INT CHECK (Quantity BETWEEN 350 AND 500), -- Standard blood donation in mL
    Notes TEXT,
    FOREIGN KEY (Bid) REFERENCES BloodBank(Bid),
    FOREIGN KEY (DonorId) REFERENCES Donor(DonorId),
    INDEX (DonationDate),
    INDEX (DonorId)
) ENGINE=InnoDB;

-- Blood request records
CREATE TABLE IF NOT EXISTS RequestTimes (
    RequestId INT PRIMARY KEY AUTO_INCREMENT,
    Did INT NOT NULL,
    Bid INT NOT NULL,
    PatientId INT,
    RequestDate DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    BloodType ENUM('A+', 'A-', 'B+', 'B-', 'AB+', 'AB-', 'O+', 'O-') NOT NULL,
    Quantity INT NOT NULL CHECK (Quantity > 0),
    Urgency ENUM('Low', 'Medium', 'High', 'Critical') DEFAULT 'Medium',
    Status ENUM('Pending', 'Approved', 'Fulfilled', 'Rejected') DEFAULT 'Pending',
    FOREIGN KEY (Did) REFERENCES Doctor(Did),
    FOREIGN KEY (Bid) REFERENCES BloodBank(Bid),
    FOREIGN KEY (PatientId) REFERENCES Patient(Pid),
    INDEX (RequestDate),
    INDEX (Status)
) ENGINE=InnoDB;

-- Junction table for blood stock assignment to banks
CREATE TABLE IF NOT EXISTS BloodBankStock (
    Bid INT NOT NULL,
    StockId INT NOT NULL,
    AssignmentDate DATETIME DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (Bid, StockId),
    FOREIGN KEY (Bid) REFERENCES BloodBank(Bid) ON DELETE CASCADE,
    FOREIGN KEY (StockId) REFERENCES BloodStock(StockId) ON DELETE CASCADE
) ENGINE=InnoDB;
