import { Button, Spinner } from 'reactstrap';
import useSWR from 'swr';
import React, { useState } from 'react';
import { useGlobalContext } from '../../context/AppContext';
import { ReactComponent as PlayIcon } from '../ToolBar/img/play.svg';

async function updateUser(url: string) {
    await fetch(url, {
        method: 'POST',
    });
}

export const ValidationBtn = () => {
    const { standName } = useGlobalContext();
    const [validation, setValidation] = useState<boolean>(false);
    const { data } = useSWR(
        standName.length !== 0 && validation ? `/api/stands/${standName}/validations` : null,
        updateUser,
    );
    if (data) {
        console.log(data);
    }
    const submitRequest = () => {
        setValidation(true);
        setTimeout(() => {
            setValidation(false);
        }, 5000);
    };

    return (
        <Button
            color="primary"
            className="flex-shrink-0"
            disabled={validation}
            onClick={() => {
                submitRequest();
            }}
        >
            {validation ? (
                <>
                    <Spinner color="white" style={{ width: '16px', height: '16px' }} /> Process
                </>
            ) : (
                <>
                    <PlayIcon /> Start
                </>
            )}
        </Button>
    );
};
