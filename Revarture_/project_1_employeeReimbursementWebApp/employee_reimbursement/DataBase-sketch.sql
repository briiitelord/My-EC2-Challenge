
drop table if exists Employee;
drop table if exists Manager;
drop table if exists Reimbursements;

CREATE DATABASE EmployeeReimbursements;

CREATE TABLE "Employee" (
    EmployeeID INT IDENTITY (1, 1) PRIMARY KEY,
    UserName VARCHAR(22), NOT NULL,
    PassWrd VARCHAR(11), NOT NULL,
    FirstName VARCHAR(22), NOT NULL,
    DOB int (8), NOT NULL,
    ReceiptSubmitted BOOLEAN, 
    ReceiptApproved BOOLEAN,
    CONSTRAINT FK_Manager FOREIGN KEY (ManagerID)
        REFERENCES Manager (ManagerID),


    /**
    *   Tests - 
        - successful account-type verification ()
        - successful new account creation - type - Employee
        - successful employee existence verification - returns != NULL
        - successful login - records match username, password on file
        - successful receipt submission - 
        

    */
)


CREATE TABLE "Manager" (
    ManagerID INT IDENTITY (7, 7) PRIMARY KEY,
    UserName VARCHAR(22), NOT NULL,
    PassWrd VARCHAR(11), NOT NULL,
    ReceiptApproval BOOLEAN, 
    CONSTRAINT FK_EmployeeID FOREIGN KEY (EmployeeID) 
        REFERENCES Employee (EmployeeID),
    CONSTRAINT FK_ReimbursementID FOREIGN KEY (ReimbursementID) 
        REFERENCES Reimbursement (ReimbursementID),

    

    /*
        Tests - 
            - successful account type verification
            - successful new account creation - type - Manager
            - successful new account creation - type - Employee
            - successful manager existence verification - returns != NULL
            - successful login - type - Manager - records match employee records + managerID
            - successful receipt acquisition - 
            - successful receipt approval
            - successful receipt denial
            - successful view all pending requests from all employees
            - successful view all pending requests from employee - by EmployeeID
            - successful view all Employees
            
    *
    */
    
)

CREATE TABLE "Reimbursement" (
    ReimbursementID INT IDENTITY (1, 1) PRIMARY KEY,
    EmployeeSubmittedFirstName VARCHAR(22), NOT NULL,
    EmployeeSubmittedLastName VARCHAR(22), NOT NULL,
    TimeStampOfSubmission TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    TimeStampOfApproval TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    ReimbursementAmount NUMERIC (11, 2), NOT NULL,
    ManagerSubmittedFirstName VARCHAR(22), NOT NULL,
    ManagerSubmittedLastName VARCHAR(22), NOT NULL,
    ReimbursementPending BOOLEAN, 
    ReimbursementApproved BOOLEAN,
    ReimbursementRejected BOOLEAN,
    CONSTRAINT FK_Manager FOREIGN KEY (ManagerID)
        REFERENCES Manager (ManagerID),
    CONSTRAINT FK_EmployeeID FOREIGN KEY (EmployeeID)
        REFERENCES Employee (EmployeeID),

        /*
        * Tests - 

            - successful verification of reimbursement existence (all "NOT NULL" values filled)
            - successful verification of employee submitting reimbursemet existing in system - first and last name
            - successful verification of manager receiving reimbursement request existing in system - first and last name
            - successful timestamping of reimbursement submission and approval
            - successful verification of reimbursement amount by - reimbursementID - timestamp
            - successful test of reimbursement flow - pending-approval, or pending-rejected
            - 


            \   
        /

