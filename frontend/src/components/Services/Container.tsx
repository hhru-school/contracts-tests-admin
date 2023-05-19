import { useState } from 'react';
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
} from 'reactstrap';
import { Loader } from 'components/Loader';
import { ApiResponse } from './types/ApiResponse';
import useSWR from 'swr';
import { getServicesList } from './api';
import { ServicesListItem } from './ListItem';

enum TabName {
    stand = 'stand',
    release = 'release',
}

export type ServicesContainerProps = {
    standName: string;
};

export const ServicesContainer: React.FC<ServicesContainerProps> = ({ standName }) => {
    const [activeTab, setActiveTab] = useState<TabName>(TabName.stand);

    const { isLoading, isValidating, data } = useSWR<ApiResponse>(
        standName ? `/api/stands/${standName}` : null,
        getServicesList,
    );

    const showLoader = isLoading || isValidating;

    if (!standName || showLoader) {
        return (
            <Container fluid>
                <div className="pt-5 d-flex justify-content-center">
                    {!standName && <h5>выберите стенд для отображения сервисов</h5>}
                    {showLoader && <Loader />}
                </div>
            </Container>
        );
    }

    return (
        <Container fluid>
            <Nav tabs>
                <NavItem>
                    <NavLink
                        href="#"
                        className={activeTab === TabName.stand ? 'active' : ''}
                        onClick={() => setActiveTab(TabName.stand)}
                        disabled={!standName}
                    >
                        Из стенда
                    </NavLink>
                </NavItem>
                <NavItem>
                    <NavLink
                        href="#"
                        className={activeTab === TabName.release ? 'active' : ''}
                        onClick={() => setActiveTab(TabName.release)}
                        disabled={!standName}
                    >
                        Из релиза
                    </NavLink>
                </NavItem>
            </Nav>
            {data && (
                <TabContent activeTab={activeTab}>
                    {Object.entries(data.services).map(([type, servicesList]) => (
                        <TabPane tabId={type} key={type}>
                            <Row>
                                <Col>
                                    <ListGroup>
                                        {servicesList.map((serviceItem, idx) => (
                                            <ServicesListItem
                                                key={serviceItem.name + idx}
                                                {...serviceItem}
                                            />
                                        ))}
                                    </ListGroup>
                                </Col>
                            </Row>
                        </TabPane>
                    ))}
                </TabContent>
            )}
        </Container>
    );
};
