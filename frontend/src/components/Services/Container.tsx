import { useState, ChangeEvent } from 'react';
import {
    Col,
    Container,
    ListGroup,
    Nav,
    NavItem,
    NavLink,
    Row,
    TabContent,
    TabPane,
    Input,
} from 'reactstrap';
import { Loader } from 'components/Loader';
import { ApiResponse, StandServicesInfo } from './types/ApiResponse';
import useSWR from 'swr';
import { getServicesList } from './api';
import { ServicesListItem } from './ListItem';

const TabTitle: { [key in keyof StandServicesInfo]: string } = {
    stand: 'Из стенда',
    release: 'Из релиза',
};

export type ServicesContainerProps = {
    standName: string;
};

export const ServicesContainer: React.FC<ServicesContainerProps> = ({ standName }) => {
    const [activeTab, setActiveTab] = useState<keyof StandServicesInfo>('stand');
    const [searchServices, setSearchServices] = useState('');

    const { isLoading, isValidating, data } = useSWR<ApiResponse>(
        standName ? `/api/stands/${standName}` : null,
        getServicesList,
    );
    const handleSearchServices = (e: ChangeEvent<HTMLInputElement>) => {
        setSearchServices(e.target.value);
    };
    const pendingPage = !data || !standName || isLoading || isValidating;

    //(Object.keys(data.services) as (keyof StandServicesInfo)[]).filter((item )=> item.stand
    //const filteredResults = data?.services.filter((item) =>
    //item.toLowerCase().includes(input.toLowerCase())
    //);

    if (pendingPage) {
        return (
            <Container fluid>
                <div className="pt-5 d-flex justify-content-center">
                    {!standName && <h5>выберите стенд для отображения сервисов</h5>}
                    {(isLoading || isValidating) && <Loader />}
                </div>
            </Container>
        );
    }

    // если стенд релизный, то для него отображаем только сервисы из релиза
    if (data.isRelease) {
        return (
            <Container fluid>
                <Input
                    className="mb-3"
                    type="text"
                    value={searchServices}
                    onChange={handleSearchServices}
                    placeholder="Введите название сервиса"
                />

                <Row>
                    <Col>
                        <ListGroup>
                            {data.services.release.map((serviceItem, idx) => {
                                if (
                                    serviceItem.name
                                        .toLowerCase()
                                        .includes(searchServices.toLowerCase())
                                ) {
                                    return (
                                        <ServicesListItem
                                            key={serviceItem.name + idx}
                                            {...serviceItem}
                                        />
                                    );
                                } else {
                                    return null;
                                }
                            })}
                        </ListGroup>
                    </Col>
                </Row>
            </Container>
        );
    }

    // если стенд не релизный, то отображаем вкладки выбора типа сервисов ("из стенда", "из релиза")
    return (
        <Container fluid>
            <Input
                className="mb-3"
                type="text"
                value={searchServices}
                onChange={handleSearchServices}
                placeholder="Введите название сервиса"
            />

            <Nav tabs>
                {(Object.keys(data.services) as (keyof StandServicesInfo)[]).map((serviceType) => (
                    <NavItem key={serviceType}>
                        <NavLink
                            href="#"
                            className={activeTab === serviceType ? 'active' : ''}
                            onClick={() => setActiveTab(serviceType)}
                        >
                            {TabTitle[serviceType]}
                        </NavLink>
                    </NavItem>
                ))}
            </Nav>
            <TabContent activeTab={activeTab}>
                {Object.entries(data.services).map(([type, servicesList]) => (
                    <TabPane tabId={type} key={type}>
                        <Row>
                            <Col>
                                <ListGroup>
                                    {servicesList.map((serviceItem, idx) => {
                                        if (
                                            serviceItem.name
                                                .toLowerCase()
                                                .includes(searchServices.toLowerCase())
                                        ) {
                                            return (
                                                <ServicesListItem
                                                    key={serviceItem.name + idx}
                                                    {...serviceItem}
                                                />
                                            );
                                        } else {
                                            return null;
                                        }
                                    })}
                                </ListGroup>
                            </Col>
                        </Row>
                    </TabPane>
                ))}
            </TabContent>
        </Container>
    );
};
