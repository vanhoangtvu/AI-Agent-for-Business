from flask import Flask
from flask_restx import Api
from flask_cors import CORS
import google.generativeai as genai
import chromadb
import os

# Import routes
from routes.health import health_ns
from routes.gemini import gemini_ns, load_gemini_models
from routes.chroma import chroma_ns, set_chroma_client

# Initialize Flask app
app = Flask(__name__)
CORS(app)

# Configure Google Gemini API
GOOGLE_API_KEY = os.getenv('GOOGLE_API_KEY')
if not GOOGLE_API_KEY:
    raise ValueError("GOOGLE_API_KEY environment variable is not set. Please add it to .env file")
genai.configure(api_key=GOOGLE_API_KEY)

# Initialize ChromaDB
chroma_client = chromadb.PersistentClient(path="./chroma_data")
set_chroma_client(chroma_client)

# Load Gemini models
load_gemini_models()

# Initialize API with Swagger documentation
api = Api(
    app,
    version='1.0',
    title='Python API Service',
    description='API service with Swagger documentation',
    doc='/docs',
    doc_expansion='list'  # Auto-expand all endpoints
)

# Register namespaces
api.add_namespace(health_ns)
api.add_namespace(gemini_ns)
api.add_namespace(chroma_ns)

if __name__ == '__main__':
    app.run(debug=True, host='0.0.0.0', port=5000)
