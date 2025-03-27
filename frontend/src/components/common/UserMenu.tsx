'use client';

import {LinkIcon, LogIn, User} from 'lucide-react';
import Link from 'next/link';
import React, {useEffect, useState} from 'react';

import {
  DropdownMenu,
  DropdownMenuContent,
  DropdownMenuItem,
  DropdownMenuTrigger,
} from '@/components/ui/dropdown-menu';

const UserMenu: React.FC = () => {
  const [isLoggedIn, setIsLoggedIn] = useState<boolean>(false);
  const [isLoading, setIsLoading] = useState<boolean>(true);
  const [isLoggingOut, setIsLoggingOut] = useState<boolean>(false);

  useEffect(() => {
    const fetchUserData = async () => {
      try {
        setIsLoading(true);
        const response = await fetch('/api/v1/user/me');

        if (!response.ok) {
          setIsLoggedIn(false);
          setIsLoading(false);
          return;
        }

        setIsLoggedIn(true);
      } catch (error) {
        console.error('Error fetching user data:', error);
      } finally {
        setIsLoading(false);
      }
    };

    fetchUserData();
  }, []);

  // Handle logout
  const handleLogout = async () => {
    try {
      setIsLoggingOut(true);

      // Send POST request to logout endpoint
      const response = await fetch('/api/v1/logout', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        credentials: 'include',
      });

      if (response.ok) {
        setIsLoggedIn(false);
      } else {
        console.error('Logout failed');
      }
    } catch (error) {
      console.error('Error during logout:', error);
    } finally {
      setIsLoggingOut(false);
    }
  };

  if (isLoading) {
    return (
        <div
            className='inline-flex items-center px-3 py-2 border border-gray-300 rounded-md text-sm font-medium text-gray-700 bg-white'>
          <span className='mr-2'>Loading...</span>
        </div>
    );
  }

  return (
      <DropdownMenu>
        <DropdownMenuTrigger
            className='inline-flex items-center px-3 py-2 border border-gray-300 rounded-md text-sm font-medium text-gray-700 bg-white hover:bg-gray-50'>
          {isLoggedIn ? (
              <>
                <span className='mr-2'>Account</span>
                <User className='w-4 h-4'/>
              </>
          ) : (
              <>
                <span className='mr-2'>Log In</span>
                <User className='w-4 h-4'/>
              </>
          )}
        </DropdownMenuTrigger>
        <DropdownMenuContent align='end'>
          {isLoggedIn ? (
              <>
                <DropdownMenuItem asChild>
                  <Link
                      href='#'
                      className='flex items-center text-gray-400 cursor-not-allowed'
                      title='Links not yet implemented'
                      onClick={(e) => e.preventDefault()}
                  >
                    <LinkIcon className='mr-2 h-4 w-4'/>
                    <span>Links</span>
                  </Link>
                </DropdownMenuItem>
                <DropdownMenuItem asChild>
                  <Link
                      href='#'
                      className='flex items-center text-gray-400 cursor-not-allowed'
                      title='Settings not yet implemented'
                      onClick={(e) => e.preventDefault()}
                  >
                    <User className='mr-2 h-4 w-4'/>
                    <span>Settings</span>
                  </Link>
                </DropdownMenuItem>
                <DropdownMenuItem
                    onSelect={(e) => {
                      e.preventDefault();
                      handleLogout();
                    }}
                >
                  <button
                      disabled={isLoggingOut}
                      className='flex items-center w-full text-left'
                  >
                    <LogIn className='mr-2 h-4 w-4'/>
                    <span>{isLoggingOut ? 'Signing out...' : 'Sign out'}</span>
                  </button>
                </DropdownMenuItem>
              </>
          ) : (
              <>
                <DropdownMenuItem asChild>
                  <Link href='/login' className='flex items-center'>
                    <LogIn className='mr-2 h-4 w-4'/>
                    <span>Sign in</span>
                  </Link>
                </DropdownMenuItem>
                <DropdownMenuItem asChild>
                  <Link href='/register' className='flex items-center'>
                    <User className='mr-2 h-4 w-4'/>
                    <span>Register</span>
                  </Link>
                </DropdownMenuItem>
              </>
          )}
        </DropdownMenuContent>
      </DropdownMenu>
  );
};

export default UserMenu;