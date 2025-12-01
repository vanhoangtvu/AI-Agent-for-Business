from flask_restx import Namespace, Resource, fields
from flask import Response, stream_with_context, request
import google.generativeai as genai
import json
import os

# Create namespace
gemini_ns = Namespace('gemini', description='Google Gemini AI operations')

# Define models for Swagger documentation
chat_input_model = gemini_ns.model('ChatInput', {
    'message': fields.String(required=True, description='Message to send to Gemini AI'),
    'model': fields.String(required=False, description='Gemini model to use (default: gemini-2.5-flash)', default='gemini-2.5-flash')
})

chat_response_model = gemini_ns.model('ChatResponse', {
    'message': fields.String(description='User message'),
    'response': fields.String(description='Gemini AI response'),
    'model': fields.String(description='AI model used')
})

model_info = gemini_ns.model('ModelInfo', {
    'name': fields.String(description='Model name'),
    'display_name': fields.String(description='Display name'),
    'supported_methods': fields.List(fields.String, description='Supported methods')
})

# Cache available models
AVAILABLE_MODELS = []

def load_gemini_models():
    """Load available Gemini models"""
    global AVAILABLE_MODELS
    try:
        models = genai.list_models()
        AVAILABLE_MODELS = [
            {
                'name': m.name.replace('models/', ''),
                'display_name': m.display_name,
                'supported_methods': m.supported_generation_methods
            }
            for m in models if 'generateContent' in m.supported_generation_methods
        ]
    except Exception as e:
        print(f"Warning: Could not load models list: {e}")

@gemini_ns.route('/models')
class ModelsList(Resource):
    @gemini_ns.doc('list_models')
    @gemini_ns.marshal_list_with(model_info)
    def get(self):
        '''Get list of available Gemini models'''
        return AVAILABLE_MODELS, 200

@gemini_ns.route('/chat')
class GeminiChat(Resource):
    @gemini_ns.doc('chat_with_gemini')
    @gemini_ns.expect(chat_input_model)
    @gemini_ns.marshal_with(chat_response_model)
    def post(self):
        '''Chat with Google Gemini AI'''
        try:
            user_message = gemini_ns.payload.get('message')
            model_name = gemini_ns.payload.get('model', 'gemini-2.5-flash')
            
            if not user_message:
                gemini_ns.abort(400, 'Message is required')
            
            # Initialize Gemini model with selected model
            model = genai.GenerativeModel(model_name)
            
            # Generate response
            response = model.generate_content(user_message)
            
            return {
                'message': user_message,
                'response': response.text,
                'model': model_name
            }, 200
            
        except Exception as e:
            gemini_ns.abort(500, f'Error communicating with Gemini: {str(e)}')

@gemini_ns.route('/chat/stream')
class GeminiChatStream(Resource):
    @gemini_ns.doc('chat_with_gemini_stream')
    @gemini_ns.expect(chat_input_model)
    def post(self):
        '''Chat with Google Gemini AI with streaming response'''
        try:
            data = request.get_json()
            user_message = data.get('message')
            model_name = data.get('model', 'gemini-2.5-flash')
            
            if not user_message:
                return {'error': 'Message is required'}, 400
            
            def generate():
                try:
                    # Initialize Gemini model
                    model = genai.GenerativeModel(model_name)
                    
                    # Send initial metadata
                    yield f"data: {json.dumps({'type': 'start', 'model': model_name})}\n\n"
                    
                    # Stream response
                    response = model.generate_content(user_message, stream=True)
                    
                    for chunk in response:
                        if chunk.text:
                            yield f"data: {json.dumps({'type': 'chunk', 'text': chunk.text})}\n\n"
                    
                    # Send completion signal
                    yield f"data: {json.dumps({'type': 'done'})}\n\n"
                    
                except Exception as e:
                    yield f"data: {json.dumps({'type': 'error', 'error': str(e)})}\n\n"
            
            return Response(
                stream_with_context(generate()),
                mimetype='text/event-stream',
                headers={
                    'Cache-Control': 'no-cache',
                    'X-Accel-Buffering': 'no',
                    'Connection': 'keep-alive'
                }
            )
            
        except Exception as e:
            return {'error': f'Error communicating with Gemini: {str(e)}'}, 500
