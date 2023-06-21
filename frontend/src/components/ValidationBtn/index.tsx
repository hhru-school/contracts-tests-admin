import { Button, Spinner } from 'reactstrap';
import useSWR from 'swr';
import React, { useState } from 'react';
import { useGlobalContext } from '../../context/AppContext';
import { ReactComponent as PlayIcon } from '../ToolBar/img/play.svg';
import { useCntStatusValidation } from '../../context/AppCntStatusValidation';
async function updateUser(url: string) {
    await fetch(url, {
        method: 'POST',
    });
}

export const ValidationBtn = () => {
    const { standName } = useGlobalContext();
    const [validation, setValidation] = useState<boolean>(false);
    const { cntStatus } = useCntStatusValidation();
    const { data } = useSWR(
        standName.length !== 0 && validation ? `/api/stands/${standName}/validations` : null,
        updateUser,
    );
    if (data) {
        console.log(data);
    }
    const submitRequest = () => {
        setValidation(true);
        setInterval(() => {
            if (cntStatus === 0) {
                setValidation(false);
            }
        }, 2000);
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
                    <Spinner style={{ width: '16px', height: '16px', color: 'white' }} />
                    Процес
                </>
            ) : (
                <>
                    <PlayIcon /> Старт
                </>
            )}
        </Button>
    );
};
