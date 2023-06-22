import { Button, Spinner } from 'reactstrap';
import useSWRMutation from 'swr/mutation';
import React, { useState } from 'react';
import { useGlobalContext } from '../../context/AppContext';
import { ReactComponent as PlayIcon } from '../ToolBar/img/play.svg';
import { useCntStatusValidation } from '../../context/AppCntStatusValidation';
async function startValidation(url: string) {
    await fetch(url, {
        method: 'POST',
    });
}
//standName.length !== 0 && validation ?
export const ValidationBtn = () => {
    const { standName } = useGlobalContext();
    const [validation, setValidation] = useState<boolean>(false);
    const { cntStatus } = useCntStatusValidation();
    const { trigger, error } = useSWRMutation(
        `/api/stands/${standName}/validations`,
        startValidation,
    );
    if (error) {
        console.log(error);
    }
    const submitRequest = () => {
        setValidation(true);
        trigger();
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
