'use client';
import Link from 'next/link';
import React, {FormEvent, useState} from 'react';

import {Alert, AlertDescription} from '@/components/ui/alert';
import Button from "@/components/buttons/Button";
import {Card, CardContent, CardHeader, CardTitle} from '@/components/ui/card';
import {Input} from '@/components/ui/input';
import {Label} from '@/components/ui/label';

const LoginForm = () => {
  const [isLoading, setIsLoading] = useState(false);
  const [error, setError] = useState('');
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');

  const handleSubmit = async (event: FormEvent<HTMLFormElement>) => {
    event.preventDefault();
    setIsLoading(true);
    setError('');

    const data = {
      email: email,
      password: password,
      credentials: 'include',
    };

    try {
      const endpoint = '/api/v1/user/login';
      const response = await fetch(endpoint, {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify(data),
      });

      if (response.status === 401) {
        setError("Invalid email or password");
        return;
      }

      if (!response.ok) {
        throw new Error('An error occurred');
      }

      window.location.href = '/';
    } catch (err) {
      setError('An error occurred. Please try again later.');
      return;
    } finally {
      setIsLoading(false);
    }
  };

  return (
      <Card className='min-h-screen flex items-center justify-center p-4 bg-gray-50'>
        <div className='w-full max-w-md shadow-lg border rounded-lg bg-white'>
          <CardHeader>
            <CardTitle>Login</CardTitle>
          </CardHeader>
          <CardContent>
            <form className='space-y-4' onSubmit={handleSubmit}>
              <div className='space-y-2'>
                <Label htmlFor='email'>Email</Label>
                <Input
                    id='email'
                    name='email'
                    type='text'
                    required
                    disabled={isLoading}
                    value={email}
                    onChange={(e) => setEmail(e.target.value)}
                />
              </div>

              <div className='space-y-2'>
                <Label htmlFor='password'>Password</Label>
                <Input
                    id='password'
                    name='password'
                    type='password'
                    required
                    disabled={isLoading}
                    value={password}
                    onChange={(e) => setPassword(e.target.value)}
                />
              </div>

              {error && (
                  <Alert variant='destructive'>
                    <AlertDescription>{error}</AlertDescription>
                  </Alert>
              )}

              <Button type='submit' className='w-full bg-primary-500 hover:bg-primary-600' disabled={isLoading}>
                {isLoading ? 'Logging in...' : 'Login'}
              </Button>

              <div className='text-center mt-4'>
                <p className='text-sm text-gray-600'>
                  Don't have an account?{' '}
                  <Link
                      href='/register'
                      className='text-blue-600 hover:underline'
                  >
                    Register
                  </Link>
                </p>
              </div>
            </form>
          </CardContent>
        </div>
      </Card>
  );
};

export default LoginForm;
