import { useGlobalContext } from 'context/AppContext';
import { Col, Row, Alert, ListGroup, ListGroupItem } from 'reactstrap';
import { StandResponse, getStatus, Direction } from '../../components/Validation/types';
import { Loader } from 'components/Loader';
import useSWR from 'swr';
import { generatePath, useNavigate } from 'react-router-dom';
import navigation from 'routes/navigation';

export const ValidationsPage: React.FC = () => {
    const { standName } = useGlobalContext();
    const navigate = useNavigate();

    const { isLoading, data, error } = useSWR<StandResponse[]>(
        standName ? `/api/stands/${standName}/validations` : null,
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
    if (!data) {
        return (
            <div className="pt-5 d-flex justify-content-center">
                <h5>Выберите стенд для отображения валидаций</h5>
            </div>
        );
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
                                <Col md={3}>
                                    <div className=" d-flex justify-content-center">
                                        {getStatus(item.status)}
                                    </div>
                                </Col>
                                <Col md={3}>Валидация {item.id}</Col>
                                <Col md={3}>
                                    {new Date(item.createdDate).toLocaleString('ru-RU')}
                                </Col>
                                <Col md={2}>{item.releaseLink}</Col>
                                <Col md={1}>{item.errorCount} ошибок</Col>
                            </>
                        ) : (
                            <>
                                <Col md={3}>
                                    <div className=" d-flex justify-content-center">
                                        {getStatus(item.status)}
                                    </div>
                                </Col>
                                <Col md={3}>Валидация {item.id}</Col>
                                <Col md={4}>
                                    {new Date(item.createdDate).toLocaleString('ru-RU')}
                                </Col>
                                <Col md={2}>{item.releaseLink}</Col>
                            </>
                        )}
                    </Row>
                </ListGroupItem>
            ))}
        </ListGroup>
    );
};
