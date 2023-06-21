import { NavLink, generatePath } from 'react-router-dom';
import { Alert, ListGroup, ListGroupItem, Row, Col } from 'reactstrap';
import { StandResponse, Direction, getStatus } from '../Validation/types';
import useSWR from 'swr';
import navigation from 'routes/navigation';
import { useCntStatusValidation } from '../../context/AppCntStatusValidation';
export type ServicesContainerProps = {
    standName: string;
    sizeLimit?: number;
};

export const ValidationHistory: React.FC<ServicesContainerProps> = ({
    standName,
    sizeLimit = 5,
}) => {
    const { isLoading, data, error } = useSWR<StandResponse[]>(
        standName ? `/api/stands/${standName}/validations?sizeLimit=${sizeLimit}` : null,
        { refreshInterval: 1000 },
    );
    const { setCntStatus } = useCntStatusValidation();
    if (isLoading) {
        return <Alert color="primary"> Data is loading </Alert>;
    }

    if (error) {
        return <Alert color="danger"> Cant load data {error.message} </Alert>;
    }
    if (!data) {
        return <small> Выберите стенд для отображения валидации</small>;
    }
    if (!Array.isArray(data) || data.length === 0) {
        return <small> Нет валидаций для стенда({standName})</small>;
    }
    const countProgress = data.filter((item: StandResponse) => {
        return item.status === Direction.InProgress;
    }).length;
    setCntStatus(countProgress);
    return (
        <ListGroup>
            {data.map((item: StandResponse) => (
                <ListGroupItem key={item.id}>
                    <Row className="align-items-center">
                        <Col xs={5}>
                            <div className="d-flex justify-content-center">
                                {getStatus(item.status)}
                            </div>
                        </Col>
                        <Col xs={7}>
                            <NavLink
                                className="link"
                                to={generatePath(navigation.validations.detail, {
                                    validationId: item.id,
                                })}
                            >
                                Валидация -{item.id}
                                <div className="d-none d-sm-block d-md-block">
                                    ({new Date(item.createdDate).toLocaleString('ru-RU')} )
                                </div>
                            </NavLink>
                        </Col>
                    </Row>
                </ListGroupItem>
            ))}
        </ListGroup>
    );
};
