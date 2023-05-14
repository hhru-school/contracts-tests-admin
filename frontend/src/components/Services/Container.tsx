import { useState } from 'react';
import { Col, Container, Nav, NavItem, NavLink, Row, TabContent, TabPane } from 'reactstrap';
import { ServicesList } from './List';
import { Loader } from 'components/Loader';
import { ApiResponse } from './types/ApiResponse';
import useSWR from 'swr';
import { getServicesList } from './api';

export type ServicesContainerProps = {
    standName: string;
};

export const ServicesContainer: React.FC<ServicesContainerProps> = ({ standName }) => {
    const [activeTab, setactiveTab] = useState(1);

    const { isLoading, isValidating, data } = useSWR<ApiResponse>(
        standName ? `/api/stands/${standName}` : null,
        getServicesList,
    );

    const showLoader = isLoading || isValidating;

    return (
        <Container fluid>
            <Nav tabs>
                <NavItem>
                    <NavLink
                        href="#"
                        className={activeTab === 1 ? 'active' : ''}
                        onClick={() => setactiveTab(1)}
                        disabled={!standName}
                    >
                        Из стенда
                    </NavLink>
                </NavItem>
                <NavItem>
                    <NavLink
                        href="#"
                        className={activeTab === 2 ? 'active' : ''}
                        onClick={() => setactiveTab(2)}
                        disabled={!standName}
                    >
                        Из релиза
                    </NavLink>
                </NavItem>
            </Nav>
            {!standName && (
                <div className="pt-5 d-flex justify-content-center">
                    <h5>выберите стенд для отображения сервисов</h5>
                </div>
            )}
            {showLoader && (
                <div className="pt-5 d-flex justify-content-center">
                    <Loader />
                </div>
            )}
            {!showLoader && data && (
                <TabContent activeTab={activeTab}>
                    <TabPane tabId={1}>
                        <Row>
                            <Col>
                                <ServicesList items={data.services.stand} />
                            </Col>
                        </Row>
                    </TabPane>
                    <TabPane tabId={2}>
                        <Row>
                            <Col>
                                <ServicesList items={data.services.release} />
                            </Col>
                        </Row>
                    </TabPane>
                </TabContent>
            )}
        </Container>
    );
};
