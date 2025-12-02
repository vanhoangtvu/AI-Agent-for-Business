#!/bin/bash

# Script to start Flask API with Swagger and Gemini integration

# Colors for output
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
RED='\033[0;31m'
NC='\033[0m' # No Color

echo -e "${GREEN}Starting Python API Service...${NC}"

# Get the directory where the script is located
DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )"
cd "$DIR"

# Check if virtual environment exists
if [ ! -d "venv" ]; then
    echo -e "${YELLOW}Virtual environment not found. Creating...${NC}"
    python3 -m venv venv
    echo -e "${GREEN}Virtual environment created.${NC}"
fi

# Activate virtual environment and install dependencies
echo -e "${YELLOW}Installing/updating dependencies...${NC}"
./venv/bin/pip install -q -r requirements.txt

# Check if .env file exists
if [ ! -f ".env" ]; then
    echo -e "${RED}Error: .env file not found!${NC}"
    echo -e "${YELLOW}Please create .env file with the following content:${NC}"
    echo -e ""
    echo -e "FLASK_APP=app.py"
    echo -e "FLASK_ENV=development"
    echo -e "PORT=5000"
    echo -e "GOOGLE_API_KEY=your_api_key_here"
    echo -e ""
    exit 1
fi

# Load environment variables
export $(grep -v '^#' .env | xargs)

echo -e "${GREEN}Starting Flask server...${NC}"
echo -e "${GREEN}API will be available at:${NC}"
echo -e "  - Local: ${YELLOW}http://127.0.0.1:5000${NC}"
echo -e "  - Swagger UI: ${YELLOW}http://127.0.0.1:5000/docs${NC}"
echo -e ""
echo -e "${YELLOW}Press CTRL+C to stop the server${NC}"
echo -e ""

# Start the Flask application
./venv/bin/python app.py
