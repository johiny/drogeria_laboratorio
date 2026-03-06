#!/bin/bash

# Script de compilación para el proyecto de Droguería
# Este script compila todo el proyecto Java

echo "==================================="
echo "Compilando proyecto de Droguería..."
echo "==================================="

# Crear directorio de compilación si no existe
mkdir -p build/classes

# Compilar todos los archivos Java
echo "Compilando archivos fuente..."
javac -d build/classes -sourcepath src \
    src/models/*.java \
    src/handlers/*.java \
    src/controller/*.java \
    src/view/*.java

if [ $? -eq 0 ]; then
    echo "✅ Compilación exitosa!"
    echo ""
    echo "Para ejecutar el proyecto, usa:"
    echo "  java -cp build/classes view.Drogueria"
    echo ""
    echo "O ejecuta el script: ./run.sh"
else
    echo "❌ Error en la compilación"
    exit 1
fi
