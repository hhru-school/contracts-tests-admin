import React, { useState } from 'react';
import useSWR from 'swr';
import { Alert } from 'reactstrap';
type propsErrorValidation = {
    keyErr: string;
};

export const DeleteErrorValidation: React.FC<propsErrorValidation> = ({ keyErr }) => {
    const [errorMessage, setErrorMessage] = useState<string>('');
    async function deleteErrorValidation(url: string) {
        try {
            const result = await fetch(url, {
                method: 'DELETE',
            });
            if (!result.ok) {
                setErrorMessage(`Элемент "${keyErr}" нельзя удалять`);
            }
        } catch (err: any) {
            setErrorMessage(`Элемент "${err.message}" нельзя удалять`);
        }
    }
    const { isLoading, error } = useSWR(`/api/error-types/${keyErr}`, deleteErrorValidation);
    if (isLoading) {
        return null;
    }
    if (error) {
        return <Alert color="danger"> Cant load delete element "{keyErr}" </Alert>;
    }
    if (errorMessage.length > 0) {
        return <Alert color="danger"> {errorMessage} </Alert>;
    }
    return <Alert color="danger"> Элемент "{keyErr}" успешно удалён </Alert>;
};
