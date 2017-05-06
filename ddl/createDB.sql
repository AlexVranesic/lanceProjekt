-- DROP TABLE HACK.PERSON
--CREATE TABLE HACK.PERSON1 (
--		ID INTEGER NOT NULL PRIMARY KEY GENERATED BY DEFAULT AS IDENTITY,
--		FIRSTNAME VARCHAR(50),
--		LASTNAME VARCHAR(50),
--		DATE_OF_BIRTH DATE,
--		ADDRESS VARCHAR(100),
--		TS_CREATE TIMESTAMP NOT NULL DEFAULT CURRENT TIMESTAMP,
--		CREATED_BY VARCHAR(50) NOT NULL
--
--
--		   
--);
--;/>

--<ScriptOptions statementTerminator=;/>


-- DELETE ALL TABLES --

DROP TABLE FREELANCE.TEAM;
DROP TABLE FREELANCE.CLAIM;
DROP TABLE FREELANCE.CLAIM_TYPE;
DROP TABLE FREELANCE.CLIENT;
DROP TABLE FREELANCE.CLIENTS_CLIENT;
DROP TABLE FREELANCE.CONTRACT;
DROP TABLE FREELANCE.FILE;
DROP TABLE FREELANCE.GEAR;
DROP TABLE FREELANCE.GEAR_CLAIM;
DROP TABLE FREELANCE.GEAR_TYPE;
DROP TABLE FREELANCE.LIABILITY;
DROP TABLE FREELANCE.LIABILITY_CLAIM;
DROP TABLE FREELANCE.MONTHLY_PAYMENT;
DROP TABLE FREELANCE.OCCUPATION;
DROP TABLE FREELANCE.POLICY;
DROP TABLE FREELANCE.POLICY_PRODUCT;
DROP TABLE FREELANCE.PRODUCT;
DROP TABLE FREELANCE.REPAIR_SERVICE;
DROP TABLE FREELANCE.SICK_DAY_CLAIM;
DROP TABLE FREELANCE.SICKNESS_INSURANCE;



CREATE FREELANCE FREELANCE;


CREATE TABLE FREELANCE.TEAM (
		ID_team INTEGER NOT NULL PRIMARY KEY GENERATED BY DEFAULT AS IDENTITY,
  	team_name VARCHAR(30),
  	team_key INTEGER NOT NULL
	)
	DATA CAPTURE NONE 
	COMPRESS NO;

CREATE TABLE FREELANCE.CLAIM (
		ID_claim INTEGER NOT NULL PRIMARY KEY GENERATED BY DEFAULT AS IDENTITY, 
		is_valid INTEGER, 
		claim_value DOUBLE, 
		claim_date DATE, 
		return_account_number INTEGER,
  	ID_team INTEGER NOT NULL
	)
	DATA CAPTURE NONE 
	COMPRESS NO;

CREATE TABLE FREELANCE.CLAIM_TYPE (
		ID_claim_type INTEGER NOT NULL PRIMARY KEY GENERATED BY DEFAULT AS IDENTITY, 
		claim_type VARCHAR(30),
  	ID_team INTEGER NOT NULL
	)
	DATA CAPTURE NONE 
	COMPRESS NO;

CREATE TABLE FREELANCE.CLIENT (
		ID_client INTEGER NOT NULL PRIMARY KEY GENERATED BY DEFAULT AS IDENTITY, 
		email VARCHAR(30), 
		name VARCHAR(30), 
		surname VARCHAR(30), 
		birth_date DATE, 
		is_fulltime INTEGER, 
		y_of_experience INTEGER, 
		annual_income INTEGER, 
		addressL1 VARCHAR(30), 
		addressL2 VARCHAR(30), 
		post INTEGER, 
		city VARCHAR(30), 
		country VARCHAR(30), 
		password VARCHAR(30), 
		card_number INTEGER, 
		ccv VARCHAR(30), 
		ID_occupation INTEGER NOT NULL,
  	ID_team INTEGER NOT NULL
	)
	DATA CAPTURE NONE 
	COMPRESS NO;
	
CREATE TABLE FREELANCE.CLIENTS_CLIENT (
		ID_clients_client INTEGER NOT NULL PRIMARY KEY GENERATED BY DEFAULT AS IDENTITY, 
		name VARCHAR(30), 
		tax_id VARCHAR(15), 
		risk_contract_percent DOUBLE,
  	ID_team INTEGER NOT NULL)
  	
	DATA CAPTURE NONE 
	COMPRESS NO;
	
	
CREATE TABLE FREELANCE.CONTRACT (
		ID_contract INTEGER NOT NULL PRIMARY KEY GENERATED BY DEFAULT AS IDENTITY, 
		contract_value DOUBLE, 
		payment_due_to DATE, 
 		is_paid INTEGER, 
		ID_policy INTEGER NOT NULL, 
		ID_clients_client INTEGER NOT NULL, 
		ID_claim INTEGER NOT NULL,
  	ID_team INTEGER NOT NULL
	)
	DATA CAPTURE NONE 
	COMPRESS NO;

CREATE TABLE FREELANCE.FILE (
		ID_file INTEGER NOT NULL PRIMARY KEY GENERATED BY DEFAULT AS IDENTITY, 
		path VARCHAR(100), 
		ID_contract INTEGER,
    ID_gear INTEGER,
    ID_gear_claim INTEGER,
    ID_sick_day_claim INTEGER,
    ID_liability_claim INTEGER,
  	ID_team INTEGER NOT NULL
	)
	DATA CAPTURE NONE 
	COMPRESS NO;

CREATE TABLE FREELANCE.GEAR (
    ID_gear INTEGER NOT NULL PRIMARY KEY GENERATED BY DEFAULT AS IDENTITY,
		gear_value DECIMAL(20, 2), 
		date_of_purchase DATE, 
		premium_price DOUBLE, 
		ID_policy_product INTEGER NOT NULL, 
		ID_gear_type INTEGER NOT NULL,
  	ID_team INTEGER NOT NULL
	)
	DATA CAPTURE NONE 
	COMPRESS NO;

CREATE TABLE FREELANCE.GEAR_CLAIM (
		ID_gear_claim INTEGER NOT NULL PRIMARY KEY GENERATED BY DEFAULT AS IDENTITY, 
		event_date DATE, 
		event_description VARCHAR(100),
    ID_claim INTEGER NOT NULL, 
		ID_repair_service INTEGER NOT NULL, 
		ID_claim_type INTEGER NOT NULL, 
		ID_gear INTEGER NOT NULL,
  	ID_team INTEGER NOT NULL
	)
	DATA CAPTURE NONE 
	COMPRESS NO;

CREATE TABLE FREELANCE.GEAR_TYPE (
		ID_gear_type INTEGER NOT NULL PRIMARY KEY GENERATED BY DEFAULT AS IDENTITY, 
		gear_type VARCHAR(30),
  	ID_team INTEGER NOT NULL
	)
	DATA CAPTURE NONE 
	COMPRESS NO;

CREATE TABLE FREELANCE.LIABILITY (
		ID_liability INTEGER NOT NULL PRIMARY KEY GENERATED BY DEFAULT AS IDENTITY, 
		premium_price DOUBLE, 
		max_claim_value DOUBLE, 
		ID_policy_product INTEGER NOT NULL,
  	ID_team INTEGER NOT NULL
	)
	DATA CAPTURE NONE 
	COMPRESS NO;

CREATE TABLE FREELANCE.LIABILITY_CLAIM (
		ID_liability_claim INTEGER NOT NULL PRIMARY KEY GENERATED BY DEFAULT AS IDENTITY, 
		description VARCHAR(100), 
		ID_liability INTEGER NOT NULL, 
		ID_claim INTEGER NOT NULL,
  	ID_team INTEGER NOT NULL
	)
	DATA CAPTURE NONE 
	COMPRESS NO;

CREATE TABLE FREELANCE.MONTHLY_PAYMENT (
		ID_payment INTEGER NOT NULL PRIMARY KEY GENERATED BY DEFAULT AS IDENTITY, 
		payment_value DOUBLE, 
		payment_date DATE, 
		ID_policy INTEGER NOT NULL,
  	ID_team INTEGER NOT NULL
	)
	DATA CAPTURE NONE 
	COMPRESS NO;

CREATE TABLE FREELANCE.OCCUPATION (
		ID_occupation INTEGER NOT NULL PRIMARY KEY GENERATED BY DEFAULT AS IDENTITY, 
		occupation VARCHAR(30),
  	ID_team INTEGER NOT NULL
	)
	DATA CAPTURE NONE 
	COMPRESS NO;

CREATE TABLE FREELANCE.POLICY (
		ID_policy INTEGER NOT NULL PRIMARY KEY GENERATED BY DEFAULT AS IDENTITY, 
		ID_client INTEGER NOT NULL,
  	ID_team INTEGER NOT NULL
	)
	DATA CAPTURE NONE 
	COMPRESS NO;

CREATE TABLE FREELANCE.POLICY_PRODUCT (
		ID_policy_product INTEGER NOT NULL PRIMARY KEY GENERATED BY DEFAULT AS IDENTITY, 
		ID_policy INTEGER NOT NULL, 
		ID_product INTEGER NOT NULL, 
		date_from DATE, 
		date_to DATE,
  	ID_team INTEGER NOT NULL
	)
	DATA CAPTURE NONE 
	COMPRESS NO;

CREATE TABLE FREELANCE.PRODUCT (
		ID_product INTEGER NOT NULL PRIMARY KEY GENERATED BY DEFAULT AS IDENTITY, 
		product VARCHAR(30),
  	ID_team INTEGER NOT NULL
	)
	DATA CAPTURE NONE 
	COMPRESS NO;

CREATE TABLE FREELANCE.REPAIR_SERVICE (
		ID_repair_service INTEGER NOT NULL PRIMARY KEY GENERATED BY DEFAULT AS IDENTITY, 
		name VARCHAR(30), 
		address VARCHAR(30), 
		ID_gear_type INTEGER NOT NULL,
  	ID_team INTEGER NOT NULL
	)
	DATA CAPTURE NONE 
	COMPRESS NO;

CREATE TABLE FREELANCE.SICK_DAY_CLAIM (
		ID_sick_day_claim INTEGER NOT NULL PRIMARY KEY GENERATED BY DEFAULT AS IDENTITY,
  	date_from DATE, 
		date_to DATE, 
		ID_sickness_insurance INTEGER NOT NULL, 
		ID_claim INTEGER NOT NULL,
  	ID_team INTEGER NOT NULL
	)
	DATA CAPTURE NONE 
	COMPRESS NO;

CREATE TABLE FREELANCE.SICKNESS_INSURANCE (
		ID_sickness_insurance INTEGER NOT NULL PRIMARY KEY GENERATED BY DEFAULT AS IDENTITY, 
		premium_price DOUBLE, 
		daily_compensation DOUBLE, 
		ID_policy_product INTEGER NOT NULL,
  	ID_team INTEGER NOT NULL
	)
	DATA CAPTURE NONE 
	COMPRESS NO;
  
  
--  -----------------------------FOREIN KEY CREATION---------------------------------

ALTER TABLE FREELANCE.CLIENT ADD CONSTRAINT clien_occupatio_FK FOREIGN KEY
	(ID_occupation)
	REFERENCES FREELANCE.OCCUPATION
	(ID_occupation)
	ON DELETE CASCADE;
	
ALTER TABLE FREELANCE.CONTRACT ADD CONSTRAINT contra_clients_cl_FK FOREIGN KEY
	(ID_clients_client)
	REFERENCES FREELANCE.CLIENTS_CLIENT
	(ID_clients_client)
	ON DELETE CASCADE;

ALTER TABLE FREELANCE.CONTRACT ADD CONSTRAINT contrac_policie_FK FOREIGN KEY
	(ID_policy)
	REFERENCES FREELANCE.POLICY
	(ID_policy)
	ON DELETE CASCADE;

ALTER TABLE FREELANCE.CONTRACT ADD CONSTRAINT contract_claims_FK FOREIGN KEY
	(ID_claim)
	REFERENCES FREELANCE.CLAIM
	(ID_claim)
	ON DELETE CASCADE;

ALTER TABLE FREELANCE.FILE ADD CONSTRAINT file_contract__FK FOREIGN KEY
	(ID_contract)
	REFERENCES FREELANCE.CONTRACT
	(ID_contract)
	ON DELETE CASCADE;
  
ALTER TABLE FREELANCE.FILE ADD CONSTRAINT file_gear__FK FOREIGN KEY
	(ID_gear)
	REFERENCES FREELANCE.GEAR
	(ID_gear)
	ON DELETE CASCADE;
  
ALTER TABLE FREELANCE.FILE ADD CONSTRAINT file_gear_claim__FK FOREIGN KEY
	(ID_gear_claim)
	REFERENCES FREELANCE.GEAR_CLAIM
	(ID_gear_claim)
	ON DELETE CASCADE;
  
ALTER TABLE FREELANCE.FILE ADD CONSTRAINT file_sick_day_claim__FK FOREIGN KEY
	(ID_sick_day_claim)
	REFERENCES FREELANCE.SICK_DAY_CLAIM
	(ID_sick_day_claim)
	ON DELETE CASCADE;
  
ALTER TABLE FREELANCE.FILE ADD CONSTRAINT file_liability__FK FOREIGN KEY
	(ID_liability_claim)
	REFERENCES FREELANCE.LIABILITY_CLAIM
	(ID_liability_claim)
	ON DELETE CASCADE;



ALTER TABLE FREELANCE.GEAR ADD CONSTRAINT gear_gear_type_FK FOREIGN KEY
	(ID_gear_type)
	REFERENCES FREELANCE.GEAR_TYPE
	(ID_gear_type)
	ON DELETE CASCADE;

ALTER TABLE FREELANCE.GEAR ADD CONSTRAINT gear_policies_p_FK FOREIGN KEY
	(ID_policy_product)
	REFERENCES FREELANCE.POLICY_PRODUCT
	(ID_policy_product)
	ON DELETE CASCADE;


ALTER TABLE FREELANCE.GEAR_CLAIM ADD CONSTRAINT gear_claim_cl_FK FOREIGN KEY
	(ID_claim)
	REFERENCES FREELANCE.CLAIM
	(ID_claim)
	ON DELETE CASCADE;
  
ALTER TABLE FREELANCE.GEAR_CLAIM ADD CONSTRAINT gear_repair_se_FK FOREIGN KEY
	(ID_repair_service)
	REFERENCES FREELANCE.REPAIR_SERVICE
	(ID_repair_service)
	ON DELETE CASCADE;

ALTER TABLE FREELANCE.GEAR_CLAIM ADD CONSTRAINT gear_c_claim_t_FK FOREIGN KEY
	(ID_claim_type)
	REFERENCES FREELANCE.CLAIM_TYPE
	(ID_claim_type)
	ON DELETE CASCADE;

ALTER TABLE FREELANCE.GEAR_CLAIM ADD CONSTRAINT gear_claim_ge_FK FOREIGN KEY
	(ID_gear)
	REFERENCES FREELANCE.GEAR
	(ID_gear)
	ON DELETE CASCADE;
  

ALTER TABLE FREELANCE.LIABILITY ADD CONSTRAINT liab_policies_p_FK FOREIGN KEY
	(ID_policy_product)
	REFERENCES FREELANCE.POLICY_PRODUCT
	(ID_policy_product)
	ON DELETE CASCADE;


ALTER TABLE FREELANCE.LIABILITY_CLAIM ADD CONSTRAINT liability__clai_FK FOREIGN KEY
	(ID_claim)
	REFERENCES FREELANCE.CLAIM
	(ID_claim)
	ON DELETE CASCADE;

ALTER TABLE FREELANCE.LIABILITY_CLAIM ADD CONSTRAINT liability__liab_FK FOREIGN KEY
	(ID_liability)
	REFERENCES FREELANCE.LIABILITY
	(ID_liability)
	ON DELETE CASCADE;


ALTER TABLE FREELANCE.MONTHLY_PAYMENT ADD CONSTRAINT monthly_pa_poli_FK FOREIGN KEY
	(ID_policy)
	REFERENCES FREELANCE.POLICY
	(ID_policy)
	ON DELETE CASCADE;


ALTER TABLE FREELANCE.POLICY ADD CONSTRAINT policie_client_FK FOREIGN KEY
	(ID_client)
	REFERENCES FREELANCE.CLIENT
	(ID_client)
	ON DELETE CASCADE;

ALTER TABLE FREELANCE.POLICY_PRODUCT ADD CONSTRAINT policies_p_poli_FK FOREIGN KEY
	(ID_policy)
	REFERENCES FREELANCE.POLICY
	(ID_policy)
	ON DELETE CASCADE;

ALTER TABLE FREELANCE.POLICY_PRODUCT ADD CONSTRAINT policies_p_prod_FK FOREIGN KEY
	(ID_product)
	REFERENCES FREELANCE.PRODUCT
	(ID_product)
	ON DELETE CASCADE;

ALTER TABLE FREELANCE.REPAIR_SERVICE ADD CONSTRAINT repair_service_gear_type_FK FOREIGN KEY
	(ID_gear_type)
	REFERENCES FREELANCE.GEAR_TYPE
	(ID_gear_type)
	ON DELETE CASCADE;

ALTER TABLE FREELANCE.SICK_DAY_CLAIM ADD CONSTRAINT sick_leav_clai_FK FOREIGN KEY
	(ID_claim)
	REFERENCES FREELANCE.CLAIM
	(ID_claim)
	ON DELETE CASCADE;

ALTER TABLE FREELANCE.SICK_DAY_CLAIM ADD CONSTRAINT sick_sickness_i_FK FOREIGN KEY
	(ID_sickness_insurance)
	REFERENCES FREELANCE.SICKNESS_INSURANCE
	(ID_sickness_insurance)
	ON DELETE CASCADE;

ALTER TABLE FREELANCE.SICKNESS_INSURANCE ADD CONSTRAINT sickness_polici_FK FOREIGN KEY
	(ID_policy_product)
	REFERENCES FREELANCE.POLICY_PRODUCT
	(ID_policy_product)
	ON DELETE CASCADE;
  
ALTER TABLE FREELANCE.CLAIM ADD CONSTRAINT claim_team_FK FOREIGN KEY
	(ID_team)
	REFERENCES FREELANCE.TEAM
	(ID_team)
	ON DELETE CASCADE;
  
ALTER TABLE FREELANCE.CLAIM_TYPE ADD CONSTRAINT claim_type_team_FK FOREIGN KEY
	(ID_team)
	REFERENCES FREELANCE.TEAM
	(ID_team)
	ON DELETE CASCADE;
  
ALTER TABLE FREELANCE.CLIENT ADD CONSTRAINT client_team_FK FOREIGN KEY
	(ID_team)
	REFERENCES FREELANCE.TEAM
	(ID_team)
	ON DELETE CASCADE;
  
  
ALTER TABLE FREELANCE.CLIENTS_CLIENT ADD CONSTRAINT clients_cl_team_FK FOREIGN KEY
	(ID_team)
	REFERENCES FREELANCE.TEAM
	(ID_team)
	ON DELETE CASCADE;
  

ALTER TABLE FREELANCE.CONTRACT ADD CONSTRAINT contract_team_FK FOREIGN KEY
	(ID_team)
	REFERENCES FREELANCE.TEAM
	(ID_team)
	ON DELETE CASCADE;
  

ALTER TABLE FREELANCE.FILE ADD CONSTRAINT file_team_FK FOREIGN KEY
	(ID_team)
	REFERENCES FREELANCE.TEAM
	(ID_team)
	ON DELETE CASCADE;
  

ALTER TABLE FREELANCE.GEAR ADD CONSTRAINT gear_team_FK FOREIGN KEY
	(ID_team)
	REFERENCES FREELANCE.TEAM
	(ID_team)
	ON DELETE CASCADE;
  

ALTER TABLE FREELANCE.GEAR_CLAIM ADD CONSTRAINT gear_claim_team_FK FOREIGN KEY
	(ID_team)
	REFERENCES FREELANCE.TEAM
	(ID_team)
	ON DELETE CASCADE;
  

ALTER TABLE FREELANCE.GEAR_TYPE ADD CONSTRAINT gear_type_team_FK FOREIGN KEY
	(ID_team)
	REFERENCES FREELANCE.TEAM
	(ID_team)
	ON DELETE CASCADE;
  
ALTER TABLE FREELANCE.LIABILITY ADD CONSTRAINT liability_team_FK FOREIGN KEY
	(ID_team)
	REFERENCES FREELANCE.TEAM
	(ID_team)
	ON DELETE CASCADE;
  
  
ALTER TABLE FREELANCE.LIABILITY_CLAIM ADD CONSTRAINT liability_cl_team_FK FOREIGN KEY
	(ID_team)
	REFERENCES FREELANCE.TEAM
	(ID_team)
	ON DELETE CASCADE;
  
  
ALTER TABLE FREELANCE.MONTHLY_PAYMENT ADD CONSTRAINT monthly_payment_team_FK FOREIGN KEY
	(ID_team)
	REFERENCES FREELANCE.TEAM
	(ID_team)
	ON DELETE CASCADE;


  
ALTER TABLE FREELANCE.OCCUPATION ADD CONSTRAINT occupation_team_FK FOREIGN KEY
	(ID_team)
	REFERENCES FREELANCE.TEAM
	(ID_team)
	ON DELETE CASCADE;
  
  
ALTER TABLE FREELANCE.POLICY ADD CONSTRAINT policy_team_FK FOREIGN KEY
	(ID_team)
	REFERENCES FREELANCE.TEAM
	(ID_team)
	ON DELETE CASCADE;
  

ALTER TABLE FREELANCE.POLICY_PRODUCT ADD CONSTRAINT policy_pr_team_FK FOREIGN KEY
	(ID_team)
	REFERENCES FREELANCE.TEAM
	(ID_team)
	ON DELETE CASCADE;
  
  
ALTER TABLE FREELANCE.PRODUCT ADD CONSTRAINT product_team_FK FOREIGN KEY
	(ID_team)
	REFERENCES FREELANCE.TEAM
	(ID_team)
	ON DELETE CASCADE;
  
ALTER TABLE FREELANCE.REPAIR_SERVICE ADD CONSTRAINT repair_serv_team_FK FOREIGN KEY
	(ID_team)
	REFERENCES FREELANCE.TEAM
	(ID_team)
	ON DELETE CASCADE;
  
  
ALTER TABLE FREELANCE.SICK_DAY_CLAIM ADD CONSTRAINT sick_d_cl_team_FK FOREIGN KEY
	(ID_team)
	REFERENCES FREELANCE.TEAM
	(ID_team)
	ON DELETE CASCADE;
  

ALTER TABLE FREELANCE.SICKNESS_INSURANCE ADD CONSTRAINT sick_ins_team_FK FOREIGN KEY
	(ID_team)
	REFERENCES FREELANCE.TEAM
	(ID_team)
	ON DELETE CASCADE;
	
