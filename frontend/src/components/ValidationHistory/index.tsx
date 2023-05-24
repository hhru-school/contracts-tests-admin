import { NavLink } from 'react-router-dom';
import { Alert, Card, CardText, CardBody, CardHeader, ListGroup, ListGroupItem } from 'reactstrap';
import { StandResponse } from './types';
import useSWR from 'swr';
export type ServicesContainerProps = {
    standName: string;
    sizeLimit: number;
};

export const ValidationHistory: React.FC<ServicesContainerProps> = ({ standName, sizeLimit }) => {
    const { isLoading, data, error } = useSWR<StandResponse[]>(
        standName ? `/api/stands/${standName}/validations?sizeLimit=${sizeLimit}` : null,
    );
    if (isLoading) {
        return <Alert color="primary"> Data is loading </Alert>;
    }

    if (error) {
        return <Alert color="danger"> Cant load data {error.message} </Alert>;
    }
    if (!data) {
        return (
            <div className="pt-5 d-flex justify-content-center">
                <h5>Выберите стенд для отображения валидации</h5>
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
        <Card>
            <CardBody>
                <CardHeader>Последние валидации</CardHeader>
                <CardText>
                    <ListGroup flush>
                        {data.map((item: StandResponse) => (
                            <ListGroupItem>
                                <NavLink className="link" to="#">
                                    Валидация {item.id}
                                </NavLink>
                                ( {new Date(item.createdDate).toLocaleDateString('ru-RU')} )
                            </ListGroupItem>
                        ))}
                    </ListGroup>
                </CardText>
            </CardBody>
        </Card>
    );
};
