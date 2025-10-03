#!/bin/bash

# Script de deployment para Usuarios-Primac-API
# Puerto: 8085
# Base de datos: MySQL 8.0

echo "🚀 Iniciando deployment de Usuarios-Primac-API..."

# Verificar si Docker está ejecutándose
if ! docker info > /dev/null 2>&1; then
    echo "❌ Error: Docker no está ejecutándose. Por favor, inicia Docker Desktop."
    exit 1
fi

# Detener y remover contenedores existentes
echo "🛑 Deteniendo contenedores existentes..."
docker-compose down

# Limpiar imágenes anteriores (opcional)
echo "🧹 Limpiando imágenes anteriores..."
docker image prune -f

# Construir y ejecutar los contenedores
echo "🔨 Construyendo y ejecutando contenedores..."
docker-compose up --build -d

# Esperar a que los servicios estén listos
echo "⏳ Esperando a que los servicios estén listos..."
sleep 30

# Verificar el estado de los contenedores
echo "📋 Estado de los contenedores:"
docker-compose ps

# Mostrar logs de la aplicación
echo "📜 Últimos logs de la aplicación:"
docker-compose logs --tail=20 app

echo ""
echo "✅ Deployment completado!"
echo "🌐 API disponible en: http://localhost:8085"
echo "🗄️  Base de datos MySQL en: localhost:3306"
echo ""
echo "📊 Comandos útiles:"
echo "  - Ver logs: docker-compose logs -f app"
echo "  - Detener servicios: docker-compose down"
echo "  - Reiniciar aplicación: docker-compose restart app"
echo "  - Acceder a la base de datos: docker exec -it usuarios-primac-db mysql -u admin -p"