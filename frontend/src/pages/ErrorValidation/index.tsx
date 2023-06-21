import React, { useState } from 'react';
import useSWR from 'swr';
import { DeleteErrorValidation } from '../../components/ErrValidation/delete';
import { ListGroup, ListGroupItem, Row, Col, Button } from 'reactstrap';
import { ReactComponent as EditIcon } from '../../components/ErrValidation/img/edit.svg';
import { ReactComponent as DeleteIcon } from '../../components/ErrValidation/img/delete.svg';
import { AddEditValidationError } from '../../components/ErrValidation/AddEditValidationError';
type tpErrorValidation = {
    key: string;
    comment: string;
    version: number;
};
enum tpMode {
    underfine = -1,
    delelete = 0,
    edit = 1,
    add = 2,
}
export const ErrorValidation = () => {
    const { isLoading, data, error } = useSWR<tpErrorValidation[]>('/api/error-types', {
        refreshInterval: 2000,
    });
    const [currentKey, setCurrentKey] = useState<string>('');
    const [currentMode, setCurrentMode] = useState<tpMode>(tpMode.underfine);
    if (isLoading) {
        return null;
    }
    if (error || !data) {
        return null;
    }
    const deleteRecord = (key: string) => {
        setCurrentKey(key);
        setCurrentMode(tpMode.delelete);
        setTimeout(() => {
            setCurrentMode(tpMode.underfine);
        }, 3000);
    };
    const editRecord = (key: string) => {
        setCurrentKey(key);
        setCurrentMode(tpMode.edit);
    };
    const resetState = () => {
        setCurrentKey('');
        setCurrentMode(tpMode.underfine);
    };
    return (
        <div>
            {currentMode === tpMode.delelete && <DeleteErrorValidation keyErr={currentKey} />}
            <ListGroup>
                <ListGroupItem key="__form__">
                    <AddEditValidationError isEdit={false} resetState={resetState} />
                </ListGroupItem>
                {data.map((errValidation: tpErrorValidation) => {
                    if (currentMode === tpMode.edit && errValidation.key === currentKey) {
                        return (
                            <ListGroupItem key={errValidation.key}>
                                <AddEditValidationError
                                    isEdit={true}
                                    data={{
                                        key: errValidation.key,
                                        comment: errValidation.comment,
                                        version: errValidation.version,
                                    }}
                                    resetState={resetState}
                                />
                            </ListGroupItem>
                        );
                    } else {
                        return (
                            <ListGroupItem key={errValidation.key}>
                                <Row>
                                    <Col md={5} className="d-flex align-items-center">
                                        {errValidation.key}
                                    </Col>
                                    <Col md={5} className="d-flex align-items-center">
                                        {errValidation.comment}
                                    </Col>
                                    <Col md={1}>
                                        <Button
                                            color="primary"
                                            onClick={() => editRecord(errValidation.key)}
                                        >
                                            <EditIcon />
                                        </Button>
                                    </Col>
                                    <Col md={1}>
                                        <Button
                                            color="primary"
                                            onClick={() => deleteRecord(errValidation.key)}
                                        >
                                            <DeleteIcon />
                                        </Button>
                                    </Col>
                                </Row>
                            </ListGroupItem>
                        );
                    }
                })}
            </ListGroup>
        </div>
    );
};
