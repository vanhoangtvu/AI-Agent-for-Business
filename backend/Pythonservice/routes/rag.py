"""
RAG Prompts API Routes
Manages RAG prompts for AI responses
"""
from flask_restx import Namespace, Resource, fields
from flask import request
from services.rag_prompt_service import RAGPromptService

# Create namespace
rag_ns = Namespace('rag', description='RAG Prompts management for AI responses')

# Global service instance
rag_prompt_service = None

def set_rag_prompt_service(service: RAGPromptService):
    """Set the RAG prompt service instance"""
    global rag_prompt_service
    rag_prompt_service = service

# Define models for Swagger documentation
prompt_input_model = rag_ns.model('PromptInput', {
    'prompt': fields.String(required=True, description='The RAG prompt text', example='When greeting users, always be friendly and professional.'),
    'category': fields.String(required=False, description='Category of the prompt', example='greeting'),
    'tags': fields.List(fields.String, required=False, description='Tags for the prompt', example=['customer-service', 'friendly']),
    'metadata': fields.Raw(required=False, description='Additional metadata')
})

prompt_update_model = rag_ns.model('PromptUpdate', {
    'prompt': fields.String(required=False, description='New prompt text'),
    'category': fields.String(required=False, description='New category'),
    'tags': fields.List(fields.String, required=False, description='New tags'),
    'metadata': fields.Raw(required=False, description='New metadata')
})

prompt_response_model = rag_ns.model('PromptResponse', {
    'id': fields.String(description='Prompt ID'),
    'prompt': fields.String(description='Prompt text'),
    'category': fields.String(description='Category'),
    'tags': fields.List(fields.String, description='Tags'),
    'metadata': fields.Raw(description='Metadata'),
    'message': fields.String(description='Success message')
})

prompt_list_model = rag_ns.model('PromptListItem', {
    'id': fields.String(description='Prompt ID'),
    'prompt': fields.String(description='Prompt text'),
    'category': fields.String(description='Category'),
    'tags': fields.List(fields.String, description='Tags'),
    'metadata': fields.Raw(description='Metadata')
})

stats_model = rag_ns.model('Stats', {
    'total_prompts': fields.Integer(description='Total number of prompts'),
    'categories': fields.Raw(description='Prompts count by category'),
    'collection_name': fields.String(description='ChromaDB collection name')
})


@rag_ns.route('/prompts')
class RAGPrompts(Resource):
    @rag_ns.doc('push_rag_prompt')
    @rag_ns.expect(prompt_input_model)
    @rag_ns.marshal_with(prompt_response_model, code=201)
    def post(self):
        '''Push a new RAG prompt to ChromaDB'''
        try:
            data = rag_ns.payload
            prompt = data.get('prompt')
            category = data.get('category')
            tags = data.get('tags')
            metadata = data.get('metadata')
            
            if not prompt:
                rag_ns.abort(400, 'Prompt text is required')
            
            result = rag_prompt_service.push_prompt(
                prompt=prompt,
                category=category,
                tags=tags,
                metadata=metadata
            )
            
            return result, 201
        except Exception as e:
            rag_ns.abort(500, f'Error pushing prompt: {str(e)}')
    
    @rag_ns.doc('get_rag_prompts')
    @rag_ns.param('category', 'Filter by category', _in='query', required=False)
    @rag_ns.param('tags', 'Filter by tags (comma-separated)', _in='query', required=False)
    @rag_ns.param('limit', 'Maximum number of prompts to return', _in='query', type='integer', required=False)
    @rag_ns.marshal_list_with(prompt_list_model)
    def get(self):
        '''Get all RAG prompts from ChromaDB'''
        try:
            # Get query parameters
            category = request.args.get('category')
            tags_str = request.args.get('tags')
            limit = request.args.get('limit', type=int)
            
            # Parse tags
            tags = tags_str.split(',') if tags_str else None
            
            prompts = rag_prompt_service.get_prompts(
                category=category,
                tags=tags,
                limit=limit
            )
            
            return prompts, 200
        except Exception as e:
            rag_ns.abort(500, f'Error getting prompts: {str(e)}')
    
    @rag_ns.doc('delete_all_prompts')
    @rag_ns.param('category', 'Delete only prompts in this category', _in='query', required=False)
    def delete(self):
        '''Delete all RAG prompts or prompts in a specific category'''
        try:
            category = request.args.get('category')
            
            result = rag_prompt_service.delete_all_prompts(category=category)
            
            return result, 200
        except Exception as e:
            rag_ns.abort(500, f'Error deleting prompts: {str(e)}')


@rag_ns.route('/prompts/<string:prompt_id>')
@rag_ns.param('prompt_id', 'The prompt ID')
class RAGPrompt(Resource):
    @rag_ns.doc('get_rag_prompt')
    @rag_ns.marshal_with(prompt_list_model)
    def get(self, prompt_id):
        '''Get a specific RAG prompt by ID'''
        try:
            prompt = rag_prompt_service.get_prompt_by_id(prompt_id)
            
            if not prompt:
                rag_ns.abort(404, f'Prompt with ID "{prompt_id}" not found')
            
            return prompt, 200
        except Exception as e:
            rag_ns.abort(500, f'Error getting prompt: {str(e)}')
    
    @rag_ns.doc('update_rag_prompt')
    @rag_ns.expect(prompt_update_model)
    @rag_ns.marshal_with(prompt_response_model)
    def put(self, prompt_id):
        '''Update a specific RAG prompt'''
        try:
            data = rag_ns.payload
            
            result = rag_prompt_service.update_prompt(
                prompt_id=prompt_id,
                prompt=data.get('prompt'),
                category=data.get('category'),
                tags=data.get('tags'),
                metadata=data.get('metadata')
            )
            
            return result, 200
        except ValueError as e:
            rag_ns.abort(404, str(e))
        except Exception as e:
            rag_ns.abort(500, f'Error updating prompt: {str(e)}')
    
    @rag_ns.doc('delete_rag_prompt')
    def delete(self, prompt_id):
        '''Delete a specific RAG prompt'''
        try:
            result = rag_prompt_service.delete_prompt(prompt_id)
            return result, 200
        except ValueError as e:
            rag_ns.abort(404, str(e))
        except Exception as e:
            rag_ns.abort(500, f'Error deleting prompt: {str(e)}')


@rag_ns.route('/prompts/context')
class RAGPromptsContext(Resource):
    @rag_ns.doc('get_prompts_context')
    @rag_ns.param('category', 'Filter by category', _in='query', required=False)
    @rag_ns.param('tags', 'Filter by tags (comma-separated)', _in='query', required=False)
    def get(self):
        '''Get all RAG prompts formatted as context string for AI'''
        try:
            category = request.args.get('category')
            tags_str = request.args.get('tags')
            tags = tags_str.split(',') if tags_str else None
            
            context = rag_prompt_service.get_all_prompts_as_context(
                category=category,
                tags=tags
            )
            
            return {
                'context': context,
                'category': category,
                'tags': tags
            }, 200
        except Exception as e:
            rag_ns.abort(500, f'Error generating context: {str(e)}')


@rag_ns.route('/stats')
class RAGStats(Resource):
    @rag_ns.doc('get_rag_stats')
    @rag_ns.marshal_with(stats_model)
    def get(self):
        '''Get statistics about RAG prompts'''
        try:
            stats = rag_prompt_service.get_stats()
            return stats, 200
        except Exception as e:
            rag_ns.abort(500, f'Error getting stats: {str(e)}')
