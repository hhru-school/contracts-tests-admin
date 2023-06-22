import { Form, FormGroup, Input, Col, Row, Label, Button } from 'reactstrap';
import useSWRMutation from 'swr/mutation';
import { tpErr } from './types';
import { useState, ChangeEvent } from 'react';
//import { ReactComponent as AddIcon } from './img/add.svg';
async function addErr(url: string, { arg }: { arg: tpErr }) {
    await fetch(url, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify(arg),
    });
}
async function editErr(url: string, { arg }: { arg: tpErr }) {
    await fetch(url, {
        method: 'PUT',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify(arg),
    });
}

type propsAddValidation = {
    data?: tpErr;
    isEdit: boolean;
    resetState: () => void;
};
export const AddEditValidationError: React.FC<propsAddValidation> = ({
    data,
    isEdit,
    resetState,
}) => {
    const { trigger } = useSWRMutation('/api/error-types', isEdit ? editErr : addErr);
    const [keyValue, setKeyValue] = useState<string>(isEdit && data ? data?.key : ' ');
    const [commentValue, setCommentValue] = useState<string>(isEdit && data ? data?.comment : ' ');
    const handleSearchKey = (e: ChangeEvent<HTMLInputElement>) => {
        setKeyValue(e.target.value);
    };
    const handleSearchComment = (e: ChangeEvent<HTMLInputElement>) => {
        setCommentValue(e.target.value);
    };

    const handleSubmit = (event: any) => {
        event.preventDefault();
        const dt: tpErr = {
            key: isEdit ? keyValue : event.target.key.value,
            comment: event.target.comment.value,
            version: isEdit && data ? data?.version : 0,
        };
        try {
            trigger(dt);
        } catch (err: any) {
            console.log(`Cant add data ${err.message}`);
        }
        setKeyValue('');
        setCommentValue('');
        resetState();
    };
    return (
        <Form onSubmit={handleSubmit}>
            <FormGroup>
                <Row>
                    <Col md={5} className="d-block align-items-center">
                        {!isEdit && (
                            <>
                                <Label className="fw-bold" for="key">
                                    Ключ:
                                </Label>
                                <Input
                                    type="text"
                                    name="key"
                                    value={keyValue}
                                    onChange={handleSearchKey}
                                />
                            </>
                        )}
                        {isEdit && keyValue}
                    </Col>
                    <Col md={5} className="d-block align-items-center fw-bold">
                        {!isEdit && (
                            <Label className="fw-bold" for="key">
                                Комментарии:
                            </Label>
                        )}
                        <Input
                            type="text"
                            name="comment"
                            value={commentValue}
                            onChange={handleSearchComment}
                        />
                    </Col>
                    <Col md={2} className="d-flex align-items-end justify-content-start">
                        <Button type="submit" color="primary">
                            {isEdit && 'Сохранить'}
                            {!isEdit && 'Добавить'}
                        </Button>
                    </Col>
                </Row>
            </FormGroup>
        </Form>
    );
};
