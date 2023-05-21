import { Nav, NavItem, NavLink } from 'reactstrap';
import { Alert } from 'reactstrap';
import useSWR from 'swr';
type standResponce = {
    id: number;
    createdDate: string;
    executeDate: string;
    releaseLink: string;
    status: string;
    errorCount: number;
};
export type ServicesContainerProps = {
    standName: string;
};

export const ValidationHistory: React.FC<ServicesContainerProps> = (standName) => {
    const { isLoading, data, error } = useSWR<standResponce[]>(
        standName ? `/api/stands/${standName.standName}/validations` : null,
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
    console.log(data);
    //const filteredData = data.slice(0, 5);
    return (
        <div className="sidebar col-2 pt-3 justify-content-start">
            <Nav vertical pills>
                {data.map((item: standResponce) => (
                    <NavItem>
                        <NavLink className="link" href="#">
                            Валидация {item.id} {item.createdDate}
                        </NavLink>
                    </NavItem>
                ))}
            </Nav>
        </div>
    );
};
