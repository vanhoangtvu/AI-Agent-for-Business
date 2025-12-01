from flask import Flask
from flask_restx import Api, Resource, fields
from flask_cors import CORS

# Initialize Flask app
app = Flask(__name__)
CORS(app)

# Initialize API with Swagger documentation
api = Api(
    app,
    version='1.0',
    title='Python API Service',
    description='API service with Swagger documentation',
    doc='/docs'
)

# Create namespace
ns = api.namespace('api', description='API operations')

# Define models for Swagger documentation
user_model = api.model('User', {
    'id': fields.Integer(required=True, description='User ID'),
    'name': fields.String(required=True, description='User name'),
    'email': fields.String(required=True, description='User email')
})

user_input_model = api.model('UserInput', {
    'name': fields.String(required=True, description='User name'),
    'email': fields.String(required=True, description='User email')
})

# Sample data
users = [
    {'id': 1, 'name': 'Nguyen Van A', 'email': 'nguyenvana@example.com'},
    {'id': 2, 'name': 'Tran Thi B', 'email': 'tranthib@example.com'}
]

@ns.route('/users')
class UserList(Resource):
    @ns.doc('list_users')
    @ns.marshal_list_with(user_model)
    def get(self):
        '''Get all users'''
        return users
    
    @ns.doc('create_user')
    @ns.expect(user_input_model)
    @ns.marshal_with(user_model, code=201)
    def post(self):
        '''Create a new user'''
        new_user = api.payload
        new_user['id'] = len(users) + 1
        users.append(new_user)
        return new_user, 201

@ns.route('/users/<int:user_id>')
@ns.param('user_id', 'The user identifier')
class User(Resource):
    @ns.doc('get_user')
    @ns.marshal_with(user_model)
    def get(self, user_id):
        '''Get a user by ID'''
        user = next((u for u in users if u['id'] == user_id), None)
        if user:
            return user
        api.abort(404, f"User {user_id} not found")
    
    @ns.doc('update_user')
    @ns.expect(user_input_model)
    @ns.marshal_with(user_model)
    def put(self, user_id):
        '''Update a user'''
        user = next((u for u in users if u['id'] == user_id), None)
        if user:
            user.update(api.payload)
            return user
        api.abort(404, f"User {user_id} not found")
    
    @ns.doc('delete_user')
    def delete(self, user_id):
        '''Delete a user'''
        global users
        user = next((u for u in users if u['id'] == user_id), None)
        if user:
            users = [u for u in users if u['id'] != user_id]
            return {'message': 'User deleted successfully'}, 200
        api.abort(404, f"User {user_id} not found")

@ns.route('/health')
class Health(Resource):
    @ns.doc('health_check')
    def get(self):
        '''Health check endpoint'''
        return {'status': 'healthy', 'message': 'API is running'}, 200

if __name__ == '__main__':
    app.run(debug=True, host='0.0.0.0', port=5000)
