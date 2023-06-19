import React from 'react';
import useSWR from 'swr';
export const ErrorValidation = () => {
    const { isLoading, data, error } = useSWR('/api/');
    if (isLoading) {
        return null;
    }
    if (error || !data) {
        return null;
    }
    return <h1>{data}</h1>;
};
