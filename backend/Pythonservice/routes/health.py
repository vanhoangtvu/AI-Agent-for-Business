from flask_restx import Namespace, Resource

# Create namespace
health_ns = Namespace('health', description='Health check operations')

@health_ns.route('/')
class Health(Resource):
    @health_ns.doc('health_check')
    def get(self):
        '''Health check endpoint'''
        return {'status': 'healthy', 'message': 'API is running'}, 200
