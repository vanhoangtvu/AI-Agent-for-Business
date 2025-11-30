'use client';

import { useState } from 'react';
import { apiClient } from '@/lib/api';

export default function TestAPIPage() {
  const [results, setResults] = useState<string[]>([]);
  const [loading, setLoading] = useState(false);

  const addResult = (message: string) => {
    setResults(prev => [...prev, `${new Date().toLocaleTimeString()}: ${message}`]);
  };

  const testLogin = async () => {
    setLoading(true);
    addResult('Testing login API...');
    try {
      // Try a test login
      const response = await fetch('http://113.178.203.147:8089/api/v1/auth/login', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({ username: 'test', password: 'test' })
      });
      
      addResult(`Login response status: ${response.status}`);
      const text = await response.text();
      addResult(`Login response: ${text.substring(0, 200)}`);
    } catch (err) {
      addResult(`Login error: ${err instanceof Error ? err.message : 'Unknown error'}`);
    }
    setLoading(false);
  };

  const testProfile = async () => {
    setLoading(true);
    addResult('Testing profile API...');
    try {
      const token = apiClient.getAuthToken();
      addResult(`Token exists: ${token ? 'Yes' : 'No'}`);
      
      if (!token) {
        addResult('No token found. Please login first.');
        setLoading(false);
        return;
      }
      
      addResult(`Token preview: ${token.substring(0, 20)}...`);
      
      const response = await fetch('http://113.178.203.147:8089/api/v1/profile', {
        method: 'GET',
        headers: {
          'Content-Type': 'application/json',
          'Authorization': `Bearer ${token}`,
        },
      });
      
      addResult(`Profile response status: ${response.status}`);
      const text = await response.text();
      addResult(`Profile response: ${text.substring(0, 500)}`);
    } catch (err) {
      addResult(`Profile error: ${err instanceof Error ? err.message : 'Unknown error'}`);
    }
    setLoading(false);
  };

  const testCart = async () => {
    setLoading(true);
    addResult('Testing cart API...');
    try {
      const token = apiClient.getAuthToken();
      if (!token) {
        addResult('No token found. Please login first.');
        setLoading(false);
        return;
      }
      
      const response = await fetch('http://113.178.203.147:8089/api/v1/cart', {
        method: 'GET',
        headers: {
          'Content-Type': 'application/json',
          'Authorization': `Bearer ${token}`,
        },
      });
      
      addResult(`Cart response status: ${response.status}`);
      const text = await response.text();
      addResult(`Cart response: ${text.substring(0, 500)}`);
    } catch (err) {
      addResult(`Cart error: ${err instanceof Error ? err.message : 'Unknown error'}`);
    }
    setLoading(false);
  };

  const testOrders = async () => {
    setLoading(true);
    addResult('Testing orders API...');
    try {
      const token = apiClient.getAuthToken();
      if (!token) {
        addResult('No token found. Please login first.');
        setLoading(false);
        return;
      }
      
      const response = await fetch('http://113.178.203.147:8089/api/v1/orders/my-orders', {
        method: 'GET',
        headers: {
          'Content-Type': 'application/json',
          'Authorization': `Bearer ${token}`,
        },
      });
      
      addResult(`Orders response status: ${response.status}`);
      const text = await response.text();
      addResult(`Orders response: ${text.substring(0, 500)}`);
    } catch (err) {
      addResult(`Orders error: ${err instanceof Error ? err.message : 'Unknown error'}`);
    }
    setLoading(false);
  };

  const clearResults = () => {
    setResults([]);
  };

  const checkLocalStorage = () => {
    const token = localStorage.getItem('auth_token');
    const userData = localStorage.getItem('user_data');
    addResult(`Token in localStorage: ${token ? 'Yes' : 'No'}`);
    if (token) {
      addResult(`Token preview: ${token.substring(0, 50)}...`);
    }
    addResult(`User data in localStorage: ${userData ? 'Yes' : 'No'}`);
    if (userData) {
      addResult(`User data: ${userData}`);
    }
  };

  return (
    <div className="min-h-screen bg-gray-50 p-8">
      <div className="max-w-4xl mx-auto">
        <h1 className="text-3xl font-bold mb-8">API Test Page</h1>
        
        <div className="bg-white rounded-lg shadow p-6 mb-6">
          <h2 className="text-xl font-semibold mb-4">Test Functions</h2>
          <div className="flex flex-wrap gap-3">
            <button
              onClick={checkLocalStorage}
              className="px-4 py-2 bg-gray-500 text-white rounded hover:bg-gray-600 disabled:bg-gray-300"
              disabled={loading}
            >
              Check LocalStorage
            </button>
            <button
              onClick={testLogin}
              className="px-4 py-2 bg-blue-500 text-white rounded hover:bg-blue-600 disabled:bg-blue-300"
              disabled={loading}
            >
              Test Login API
            </button>
            <button
              onClick={testProfile}
              className="px-4 py-2 bg-green-500 text-white rounded hover:bg-green-600 disabled:bg-green-300"
              disabled={loading}
            >
              Test Profile API
            </button>
            <button
              onClick={testCart}
              className="px-4 py-2 bg-yellow-500 text-white rounded hover:bg-yellow-600 disabled:bg-yellow-300"
              disabled={loading}
            >
              Test Cart API
            </button>
            <button
              onClick={testOrders}
              className="px-4 py-2 bg-purple-500 text-white rounded hover:bg-purple-600 disabled:bg-purple-300"
              disabled={loading}
            >
              Test Orders API
            </button>
            <button
              onClick={clearResults}
              className="px-4 py-2 bg-red-500 text-white rounded hover:bg-red-600"
            >
              Clear Results
            </button>
          </div>
        </div>

        <div className="bg-white rounded-lg shadow p-6">
          <h2 className="text-xl font-semibold mb-4">Results</h2>
          {loading && <p className="text-blue-600 mb-2">Loading...</p>}
          <div className="space-y-2">
            {results.length === 0 ? (
              <p className="text-gray-500">No results yet. Click a test button to start.</p>
            ) : (
              results.map((result, index) => (
                <div key={index} className="p-2 bg-gray-50 rounded border border-gray-200">
                  <pre className="text-sm whitespace-pre-wrap break-words">{result}</pre>
                </div>
              ))
            )}
          </div>
        </div>
      </div>
    </div>
  );
}
