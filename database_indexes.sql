-- =====================================================
-- ÍNDICES DE OPTIMIZACIÓN PARA USUARIOS-PRIMAC-API
-- Para mejorar el rendimiento con 20k+ registros
-- =====================================================

-- Índices para la tabla USERS
-- Optimiza búsquedas por email y username
CREATE INDEX IF NOT EXISTS idx_users_email ON users(email);
CREATE INDEX IF NOT EXISTS idx_users_username ON users(username);
CREATE INDEX IF NOT EXISTS idx_users_phone ON users(phone);
CREATE INDEX IF NOT EXISTS idx_users_created_at ON users(created_at);

-- Índices para la tabla CLIENTS  
-- Optimiza búsquedas por nombre, apellido y documento
CREATE INDEX IF NOT EXISTS idx_clients_first_name ON clients(first_name);
CREATE INDEX IF NOT EXISTS idx_clients_last_name ON clients(last_name);
CREATE INDEX IF NOT EXISTS idx_clients_document_number ON clients(document_number);
CREATE INDEX IF NOT EXISTS idx_clients_full_name ON clients(first_name, last_name);
CREATE INDEX IF NOT EXISTS idx_clients_created_at ON clients(created_at);

-- Índices para la tabla AGENTS
-- Optimiza búsquedas por código, nombre y estado
CREATE INDEX IF NOT EXISTS idx_agents_code ON agents(code);
CREATE INDEX IF NOT EXISTS idx_agents_first_name ON agents(first_name);
CREATE INDEX IF NOT EXISTS idx_agents_last_name ON agents(last_name);
CREATE INDEX IF NOT EXISTS idx_agents_is_active ON agents(is_active);
CREATE INDEX IF NOT EXISTS idx_agents_user_id ON agents(user_id);
CREATE INDEX IF NOT EXISTS idx_agents_full_name ON agents(first_name, last_name);

-- Índices para la tabla BENEFICIARIES
-- Optimiza búsquedas por nombre, cliente y documento
CREATE INDEX IF NOT EXISTS idx_beneficiaries_first_name ON beneficiaries(first_name);
CREATE INDEX IF NOT EXISTS idx_beneficiaries_last_name ON beneficiaries(last_name);
CREATE INDEX IF NOT EXISTS idx_beneficiaries_document_number ON beneficiaries(document_number);
CREATE INDEX IF NOT EXISTS idx_beneficiaries_client_id ON beneficiaries(client_id);
CREATE INDEX IF NOT EXISTS idx_beneficiaries_relationship ON beneficiaries(relationship);
CREATE INDEX IF NOT EXISTS idx_beneficiaries_full_name ON beneficiaries(first_name, last_name);
CREATE INDEX IF NOT EXISTS idx_beneficiaries_created_at ON beneficiaries(created_at);

-- Índices compuestos para consultas frecuentes
-- Optimiza filtros combinados
CREATE INDEX IF NOT EXISTS idx_clients_name_document ON clients(first_name, last_name, document_number);
CREATE INDEX IF NOT EXISTS idx_agents_name_code ON agents(first_name, last_name, code);
CREATE INDEX IF NOT EXISTS idx_beneficiaries_client_name ON beneficiaries(client_id, first_name, last_name);

-- =====================================================
-- ANÁLISIS DE RENDIMIENTO
-- Ejecutar después de crear los índices
-- =====================================================

-- Verificar que los índices se crearon correctamente
SHOW INDEX FROM users;
SHOW INDEX FROM clients;
SHOW INDEX FROM agents;
SHOW INDEX FROM beneficiaries;

-- Análisis de consultas lentas (opcional)
-- SET GLOBAL slow_query_log = 'ON';
-- SET GLOBAL long_query_time = 1;

-- =====================================================
-- ESTIMACIONES DE MEJORA DE RENDIMIENTO
-- =====================================================

-- Sin índices:
-- SELECT * FROM clients WHERE first_name LIKE '%Juan%' → 5-10 segundos (scan completo)
-- 
-- Con índices:
-- SELECT * FROM clients WHERE first_name LIKE '%Juan%' → 0.1-0.5 segundos (índice)
--
-- Mejora esperada: 90-95% más rápido