import { useState } from 'react';
import { Col, Container, Nav, NavItem, NavLink, Row, TabContent, TabPane } from 'reactstrap';
import { ServicesList } from './List';
import { Loader } from 'components/Loader';
import { ApiResponse } from './types/ApiResponse';
import useSWR from 'swr';
import { getServicesList } from './api';

enum TabName {
    stand = 'stand',
    release = 'release',
}

export type ServicesContainerProps = {
    standName: string;
};

export const ServicesContainer: React.FC<ServicesContainerProps> = ({ standName }) => {
    const [activeTab, setactiveTab] = useState<TabName>(TabName.stand);

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
                        className={activeTab === TabName.stand ? 'active' : ''}
                        onClick={() => setactiveTab(TabName.stand)}
                        disabled={!standName}
                    >
                        Из стенда
                    </NavLink>
                </NavItem>
                <NavItem>
                    <NavLink
                        href="#"
                        className={activeTab === TabName.release ? 'active' : ''}
                        onClick={() => setactiveTab(TabName.release)}
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
                    <TabPane tabId={TabName.stand}>
                        <Row>
                            <Col>
                                <ServicesList items={data.services.stand} />
                            </Col>
                        </Row>
                    </TabPane>
                    <TabPane tabId={TabName.release}>
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
