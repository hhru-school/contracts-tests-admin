import { NavLink, generatePath } from 'react-router-dom';
import { Alert, ListGroup, ListGroupItem, Spinner, Row, Col } from 'reactstrap';
import { ReactComponent as SuccessIcon } from './img/success.svg';
import { ReactComponent as FailedIcon } from './img/failed.svg';
import { ReactComponent as CancelledIcon } from './img/cancelled.svg';
import { StandResponse, Direction } from './types';
import useSWR from 'swr';
import navigation from 'routes/navigation';
export type ServicesContainerProps = {
    standName: string;
    sizeLimit?: number;
};
const getStatus = (statusResponce: Direction) => {
    switch (statusResponce) {
        case Direction.InProgress:
            return <Spinner color="primary" />;
            break;
        case Direction.Cancelled:
            return <CancelledIcon />;
            break;
        case Direction.Failed:
            return <FailedIcon />;
            break;
        case Direction.Success:
            return <SuccessIcon />;
            break;
    }
};
export const ValidationHistory: React.FC<ServicesContainerProps> = ({
    standName,
    sizeLimit = 5,
}) => {
    const { isLoading, data, error } = useSWR<StandResponse[]>(
        standName ? `/api/stands/${standName}/validations?sizeLimit=${sizeLimit}` : null,
        { refreshInterval: 3000 },
    );
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
    console.log(`data = ${data.length}`);
    return (
        <ListGroup>
            {data.map((item: StandResponse) => (
                <ListGroupItem key={item.id}>
                    <Row className="align-items-center">
                        <Col xs={7} xl={9}>
                            <NavLink
                                className="link"
                                to={generatePath(navigation.validations.detail, {
                                    validationId: item.id,
                                })}
                            >
                                Валидация {item.id} (
                                {new Date(item.createdDate).toLocaleDateString('ru-RU')} )
                            </NavLink>
                        </Col>
                        <Col xs={5} xl={3}>
                            <div className=" d-flex justify-content-center">
                                {getStatus(item.status)}
                            </div>
                        </Col>
                    </Row>
                </ListGroupItem>
            ))}
        </ListGroup>
    );
};
