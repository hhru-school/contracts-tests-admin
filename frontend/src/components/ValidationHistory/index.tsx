import { Nav, NavItem, NavLink } from 'reactstrap';
import { Alert, Card, CardText, CardBody, CardHeader } from 'reactstrap';

import useSWR from 'swr';
type standResponce = {
    id: number;
    createdDate: Date;
    executeDate: Date;
    releaseLink: string;
    status: string;
    errorCount: number;
};
export type ServicesContainerProps = {
    standName: string;
};

export const ValidationHistory: React.FC<ServicesContainerProps> = ({ standName }) => {
    const { isLoading, data, error } = useSWR<standResponce[]>(
        standName ? `/api/stands/${standName}/validations` : null,
    );
    if (isLoading) {
        return <Alert color="primary"> Data is loading </Alert>;
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
    console.log(data);
    const filteredData = data.slice(0, 5);
    return (
        <Card>
            <CardBody>
                <CardHeader>Последние валидации</CardHeader>
                <CardText>
                    <div className="justify-content-start">
                        <Nav vertical pills>
                            {filteredData.map((item: standResponce) => (
                                <NavItem>
                                    <NavLink className="link" href="#">
                                        Валидация {item.id} (
                                        {new Date(item.createdDate).toLocaleDateString('ru-RU')} )
                                    </NavLink>
                                </NavItem>
                            ))}
                        </Nav>
                    </div>
                </CardText>
            </CardBody>
        </Card>
    );
};
