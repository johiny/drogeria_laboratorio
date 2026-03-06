#!/bin/bash

# Script para ejecutar la aplicación de Droguería

echo "==================================="
echo "Iniciando Droguería Paco..."
echo "==================================="

if [ ! -d "build/classes" ]; then
    echo "❌ El proyecto no está compilado."
    echo "Ejecuta primero: ./compile.sh"
    exit 1
fi

java -cp build/classes view.Drogueria
