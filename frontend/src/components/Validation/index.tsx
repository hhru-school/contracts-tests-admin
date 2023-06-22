import { Col, Row, Alert, ListGroup, ListGroupItem } from 'reactstrap';
import { StandResponse, getStatus, Direction } from './types';
import { Loader } from '../Loader';
import useSWR from 'swr';
import { Link, generatePath, useNavigate } from 'react-router-dom';
import navigation from 'routes/navigation';

export type propsContainerProps = {
    standName: string;
};

export const Validation: React.FC<propsContainerProps> = ({ standName }) => {
    const navigate = useNavigate();
    const { isLoading, data, error } = useSWR<StandResponse[]>(
        standName ? `/api/stands/${standName}/validations` : null,
        { refreshInterval: 2000 },
    );

    if (!standName || isLoading) {
        return (
            <div className="pt-5 d-flex justify-content-center">
                {!standName && <h5>Выберите стенд для отображения валидации</h5>}
                {isLoading && <Loader />}
            </div>
        );
    }
    if (error) {
        return <Alert color="danger"> Cant load data {error.message} </Alert>;
    }
    if (!Array.isArray(data) || data.length === 0) {
        return (
            <div className="pt-5 d-flex justify-content-center">
                <h5>Нет валидаций для стенда({standName})</h5>
            </div>
        );
    }
    return (
        <ListGroup>
            {data.map((item: StandResponse) => (
                <ListGroupItem
                    onClick={() =>
                        navigate(
                            generatePath(navigation.validations.detail, { validationId: item.id }),
                        )
                    }
                    action
                    key={item.id}
                >
                    <Row>
                        {item.status === Direction.Failed ? (
                            <>
                                <Col xs={5} sm={2} md={2}>
                                    <div className=" d-flex justify-content-center">
                                        {getStatus(item.status)}
                                    </div>
                                </Col>
                                <Col xs={4} sm={3} md={2}>
                                    Валидация {item.id}
                                </Col>
                                <Col xs={3} sm={3} md={2}>
                                    {item.errorCount} ошибок
                                </Col>

                                <Col sm={4} md={3} className="d-none d-sm-block">
                                    {new Date(item.createdDate).toLocaleString('ru-RU')}
                                </Col>
                                <Col md={3} className="d-none d-md-block">
                                    <Link to="#">Информация о релизе</Link>
                                </Col>
                            </>
                        ) : (
                            <>
                                <Col xs={7} sm={2} md={2}>
                                    <div className=" d-flex justify-content-center">
                                        {getStatus(item.status)}
                                    </div>
                                </Col>
                                <Col xs={5} sm={4} md={2}>
                                    Валидация {item.id}
                                </Col>
                                <Col sm={6} md={4} className="d-none d-sm-block">
                                    {new Date(item.createdDate).toLocaleString('ru-RU')}
                                </Col>
                                <Col md={4} className="d-none d-md-block">
                                    <Link to="#">Информация о релизе</Link>
                                </Col>
                            </>
                        )}
                    </Row>
                </ListGroupItem>
            ))}
        </ListGroup>
    );
};
