import { useState } from 'react';
import {
    Col,
    Container,
    Nav,
    NavItem,
    NavLink,
    Row,
    Spinner,
    TabContent,
    TabPane,
} from 'reactstrap';
import { ServicesList } from './List';
import useSWR from 'swr';
import { Service } from './types/Service';
import { getServicesList } from './api';

const Loader: React.FC = () => (
    <Spinner
        type="grow"
        color="primary"
        style={{
            height: '3rem',
            width: '3rem',
        }}
    />
);

export const ServicesContainer = () => {
    const [activeTab, setactiveTab] = useState(1);

    const { isLoading, isValidating, data } = useSWR<Service[]>('/api/services', getServicesList);

    return (
        <Container fluid>
            <Nav tabs>
                <NavItem>
                    <NavLink
                        href="#"
                        className={activeTab === 1 ? 'active' : ''}
                        onClick={() => setactiveTab(1)}
                    >
                        Из стендов
                    </NavLink>
                </NavItem>
                <NavItem>
                    <NavLink
                        href="#"
                        className={activeTab === 2 ? 'active' : ''}
                        onClick={() => setactiveTab(2)}
                    >
                        Из релиза
                    </NavLink>
                </NavItem>
            </Nav>
            <TabContent activeTab={activeTab}>
                <TabPane tabId={1}>
                    <Row>
                        {(isLoading || isValidating) && (
                            <Col className="d-flex justify-content-center">
                                <div className="pt-5">
                                    <Loader />
                                </div>
                            </Col>
                        )}
                        {data && (
                            <Col>
                                <ServicesList items={data} />
                            </Col>
                        )}
                    </Row>
                </TabPane>
                <TabPane tabId={2}>
                    <Row>
                        <Col className="d-flex justify-content-center">
                            <div className="pt-5">
                                <Loader />
                            </div>
                        </Col>
                    </Row>
                </TabPane>
            </TabContent>
        </Container>
    );
};
