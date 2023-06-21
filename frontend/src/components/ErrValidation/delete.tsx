import React from 'react';
import useSWR from 'swr';
import { Alert } from 'reactstrap';
type propsErrorValidation = {
    keyErr: string;
};
async function deleteErrorValidation(url: string) {
    await fetch(url, {
        method: 'DELETE',
    });
}

export const DeleteErrorValidation: React.FC<propsErrorValidation> = ({ keyErr }) => {
    const { isLoading, error } = useSWR(`/api/error-types/${keyErr}`, deleteErrorValidation);
    if (isLoading) {
        return null;
    }
    if (error) {
        return <Alert color="danger"> Cant load delete element "{keyErr}" </Alert>;
    }
    return <Alert color="danger"> Element "{keyErr}" deleted successfully </Alert>;
};
