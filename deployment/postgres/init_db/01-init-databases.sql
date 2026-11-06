-- Create individual databases for order-service and customer-service
CREATE DATABASE order_db;
CREATE DATABASE customer_db;

-- Grant privileges to the service user
GRANT ALL PRIVILEGES ON DATABASE order_db TO iteuser;
GRANT ALL PRIVILEGES ON DATABASE customer_db TO iteuser;