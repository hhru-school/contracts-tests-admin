import { NavLink, generatePath } from 'react-router-dom';
import { Alert, ListGroup, ListGroupItem } from 'reactstrap';
import { StandResponse } from './types';
import useSWR from 'swr';
import navigation from 'routes/navigation';
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
    return (
        <ListGroup>
            {data.map((item: StandResponse) => (
                <ListGroupItem key={item.id}>
                    <NavLink
                        className="link"
                        to={generatePath(navigation.validations.detail, { validationId: item.id })}
                    >
                        Валидация {item.id}
                    </NavLink>
                    ( {new Date(item.createdDate).toLocaleDateString('ru-RU')} )
                </ListGroupItem>
            ))}
        </ListGroup>
    );
};
