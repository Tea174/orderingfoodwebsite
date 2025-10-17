-- Create Schemas for Bounded Contexts
DROP SCHEMA IF EXISTS kdg_owners CASCADE;
DROP SCHEMA IF EXISTS kdg_customers CASCADE;
CREATE SCHEMA IF NOT EXISTS kdg_restaurants;
CREATE SCHEMA IF NOT EXISTS kdg_customers;
CREATE SCHEMA IF NOT EXISTS kdg_orders;
CREATE SCHEMA IF NOT EXISTS kdg_deliveries;
-- -- Grant permissions
-- GRANT ALL ON SCHEMA kdg_owners TO kdg;
-- GRANT ALL ON SCHEMA kdg_customers TO kdg;
