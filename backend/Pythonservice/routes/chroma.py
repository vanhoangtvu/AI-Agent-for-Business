from flask_restx import Namespace, Resource, fields

# Create namespace
chroma_ns = Namespace('chroma', description='ChromaDB vector database operations')

# Global variable for chroma client (will be set from app.py)
chroma_client = None

def set_chroma_client(client):
    """Set the ChromaDB client"""
    global chroma_client
    chroma_client = client

# Define models for Swagger documentation
chroma_collection_info = chroma_ns.model('CollectionInfo', {
    'name': fields.String(description='Collection name'),
    'count': fields.Integer(description='Number of documents')
})

chroma_document_input = chroma_ns.model('DocumentInput', {
    'collection_name': fields.String(required=True, description='Collection name'),
    'documents': fields.List(fields.String, required=True, description='List of documents to add'),
    'metadatas': fields.List(fields.Raw, required=False, description='List of metadata objects'),
    'ids': fields.List(fields.String, required=False, description='List of document IDs')
})

chroma_query_input = chroma_ns.model('QueryInput', {
    'collection_name': fields.String(required=True, description='Collection name'),
    'query_texts': fields.List(fields.String, required=True, description='Query texts'),
    'n_results': fields.Integer(required=False, description='Number of results', default=5)
})

@chroma_ns.route('/collections')
class ChromaCollections(Resource):
    @chroma_ns.doc('list_collections')
    @chroma_ns.marshal_list_with(chroma_collection_info)
    def get(self):
        '''Get all ChromaDB collections'''
        try:
            collections = chroma_client.list_collections()
            return [
                {
                    'name': col.name,
                    'count': col.count()
                }
                for col in collections
            ], 200
        except Exception as e:
            chroma_ns.abort(500, f'Error listing collections: {str(e)}')

@chroma_ns.route('/collection/<string:collection_name>')
@chroma_ns.param('collection_name', 'The collection name')
class ChromaCollection(Resource):
    @chroma_ns.doc('get_collection')
    def get(self, collection_name):
        '''Get all documents from a collection'''
        try:
            collection = chroma_client.get_or_create_collection(name=collection_name)
            data = collection.get()
            return {
                'collection_name': collection_name,
                'count': collection.count(),
                'ids': data['ids'],
                'documents': data['documents'],
                'metadatas': data['metadatas']
            }, 200
        except Exception as e:
            chroma_ns.abort(500, f'Error getting collection: {str(e)}')
    
    @chroma_ns.doc('delete_collection')
    def delete(self, collection_name):
        '''Delete a collection'''
        try:
            chroma_client.delete_collection(name=collection_name)
            return {'message': f'Collection {collection_name} deleted successfully'}, 200
        except Exception as e:
            chroma_ns.abort(500, f'Error deleting collection: {str(e)}')

@chroma_ns.route('/documents')
class ChromaDocuments(Resource):
    @chroma_ns.doc('add_documents')
    @chroma_ns.expect(chroma_document_input)
    def post(self):
        '''Add documents to a collection'''
        try:
            data = chroma_ns.payload
            collection_name = data.get('collection_name')
            documents = data.get('documents')
            metadatas = data.get('metadatas', None)
            ids = data.get('ids', None)
            
            if not documents:
                chroma_ns.abort(400, 'Documents are required')
            
            # Generate IDs if not provided
            if not ids:
                ids = [f"doc_{i}" for i in range(len(documents))]
            
            collection = chroma_client.get_or_create_collection(name=collection_name)
            collection.add(
                documents=documents,
                metadatas=metadatas,
                ids=ids
            )
            
            return {
                'message': f'Added {len(documents)} documents to {collection_name}',
                'collection_name': collection_name,
                'count': collection.count()
            }, 201
        except Exception as e:
            chroma_ns.abort(500, f'Error adding documents: {str(e)}')

@chroma_ns.route('/query')
class ChromaQuery(Resource):
    @chroma_ns.doc('query_documents')
    @chroma_ns.expect(chroma_query_input)
    def post(self):
        '''Query documents from a collection'''
        try:
            data = chroma_ns.payload
            collection_name = data.get('collection_name')
            query_texts = data.get('query_texts')
            n_results = data.get('n_results', 5)
            
            if not query_texts:
                chroma_ns.abort(400, 'Query texts are required')
            
            collection = chroma_client.get_or_create_collection(name=collection_name)
            results = collection.query(
                query_texts=query_texts,
                n_results=n_results
            )
            
            return {
                'collection_name': collection_name,
                'results': results
            }, 200
        except Exception as e:
            chroma_ns.abort(500, f'Error querying documents: {str(e)}')
