'use client';
import Link from 'next/link';
import React, {FormEvent, useState} from 'react';

import {Alert, AlertDescription} from '@/components/ui/alert';
import Button from '@/components/buttons/Button'
import {Card, CardContent, CardHeader, CardTitle} from '@/components/ui/card';
import {Input} from '@/components/ui/input';
import {Label} from '@/components/ui/label';

const RegisterForm = () => {
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
      const endpoint = '/api/v1/user/register';
      const response = await fetch(endpoint, {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify(data),
      });

      if (!response.ok) {
        const body = await response.json();
        throw new Error(body.message);
      }

      window.location.href = '/';
    } catch (err) {
      if (err instanceof Error) setError(err.message);
      else setError('An unknown error occurred');
    } finally {
      setIsLoading(false);
    }
  };

  return (
      <Card className='min-h-screen flex items-center justify-center p-4 bg-gray-50'>
        <div className='w-full max-w-md shadow-lg border rounded-lg bg-white'>
          <CardHeader>
            <CardTitle>Register</CardTitle>
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
                    value={email}
                    onChange={(e) => setEmail(e.target.value)}
                    disabled={isLoading}
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

              <div className='mt-1'>
                <div className='flex justify-between mb-1'>
                  <span className='text-sm'>Password strength:</span>
                  <span className='text-sm font-medium'>
                  {password === '' && 'None'}
                    {password !== '' && password.length < 9 && 'Weak'}
                    {password !== '' &&
                        password.length >= 9 &&
                        password.length < 16 &&
                        'Medium'}
                    {password !== '' && password.length >= 16 && 'Strong'}
                </span>
                </div>
                <div className='w-full bg-gray-200 rounded-full h-2'>
                  <div
                      className={`h-2 rounded-full ${
                          password === ''
                              ? 'w-0'
                              : password.length < 9
                                  ? 'w-1/4 bg-red-500'
                                  : password.length < 12
                                      ? 'w-2/4 bg-yellow-500'
                                      : 'w-full bg-green-500'
                      }`}
                  ></div>
                </div>
                <a
                    href='https://xkcd.com/936/'
                    target='_blank'
                    rel='noopener noreferrer'
                >
                  <p className='text-xs text-gray-500 mt-1 underline'>
                    Ideally, use long, memorable passphrases.
                  </p>
                </a>
              </div>

              {error && (
                  <Alert variant='destructive'>
                    <AlertDescription>{error}</AlertDescription>
                  </Alert>
              )}

              <Button type='submit' className='w-full bg-primary-500 hover:bg-primary-600' disabled={isLoading}>
                {isLoading ? 'Registering...' : 'Register'}
              </Button>
            </form>

            <div className='text-center mt-4'>
              <p className='text-sm text-gray-600'>
                Already have an account?{' '}
                <Link href='/login' className='text-blue-500 hover:underline'>
                  Log in
                </Link>
              </p>
            </div>
          </CardContent>
        </div>
      </Card>
  );
};

export default RegisterForm;
