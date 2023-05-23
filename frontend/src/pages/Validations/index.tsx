import { AppContext } from 'context/AppContext';
import { useContext } from 'react';
import { Col, Row, Alert, Card, CardText, CardBody } from 'reactstrap';
import { standResponce } from '../../components/ValidationHistory/types';
import { Loader } from 'components/Loader';
import useSWR from 'swr';

export const ValidationsPage: React.FC = () => {
    const { standName } = useContext(AppContext);

    const { isLoading, data, error } = useSWR<standResponce[]>(
        standName ? `/api/stands/${standName}/validations` : null,
    );

    if (!standName || isLoading) {
        return (
            <div className="pt-5 d-flex justify-content-center">
                {!standName && <h5>выберите стенд для отображения сервисов</h5>}
                {isLoading && <Loader />}
            </div>
        );
    }
    if (error) {
        return <Alert color="danger"> Cant load data {error.message} </Alert>;
    }
    if (!data) {
        return null;
    }
    if (!Array.isArray(data) || data.length === 0) {
        return null;
    }
    return (
        <>
            {data.map((item: standResponce) => (
                <Row>
                    <Col md={12} className="mb-3">
                        <Card>
                            <CardBody>
                                <CardText>
                                    <Row>
                                        <Col md={3}>Валидация {item.id}</Col>
                                        <Col md={4}>
                                            {new Date(item.createdDate).toLocaleDateString('ru-RU')}
                                        </Col>
                                        <Col md={3}>{item.releaseLink}</Col>
                                        <Col md={2}>{item.errorCount} ошибок</Col>
                                    </Row>
                                </CardText>
                            </CardBody>
                        </Card>
                    </Col>
                </Row>
            ))}
        </>
    );
};
