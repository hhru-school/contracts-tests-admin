import { useState } from 'react';
import { Col, Container, Nav, NavItem, NavLink, Row, TabContent, TabPane } from 'reactstrap';
import { ServicesList } from './List';
import useSWR from 'swr';
import { getServicesList } from './api';
import { Loader } from 'components/Loader';
import { ApiResponse } from './types/ApiResponse';

export const ServicesContainer = () => {
    const [activeTab, setactiveTab] = useState(1);

    //TODO: заменить на хук useStandName из тулбара
    const standId = 'ts107.pyn.ru-contracts';

    const { isLoading, isValidating, data } = useSWR<ApiResponse>(
        `/api/stands/${standId}`,
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
                    >
                        Из стенда
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
                        {showLoader && (
                            <Col className="d-flex justify-content-center">
                                <div className="pt-5">
                                    <Loader />
                                </div>
                            </Col>
                        )}
                        {!showLoader && data && (
                            <Col>
                                <ServicesList items={data.services.stand} />
                            </Col>
                        )}
                    </Row>
                </TabPane>
                <TabPane tabId={2}>
                    <Row>
                        {showLoader && (
                            <Col className="d-flex justify-content-center">
                                <div className="pt-5">
                                    <Loader />
                                </div>
                            </Col>
                        )}
                        {!showLoader && data && (
                            <Col>
                                <ServicesList items={data.services.release} />
                            </Col>
                        )}
                    </Row>
                </TabPane>
            </TabContent>
        </Container>
    );
};
