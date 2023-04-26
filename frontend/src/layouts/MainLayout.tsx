import { AppContent } from 'components/App/Content';
import { AppFooter } from 'components/App/Footer/Index';
import { AppHeader } from 'components/App/Header';
import { AppSidebar } from 'components/App/Sidebar';
import { Col, Container, Row } from 'reactstrap';

export const MainLayout = () => {
    return (
        <>
            <AppHeader />
            <Container fluid>
                <Row style={{ height: '89vh' }}>
                    <Col md={2} className="pt-3 border-end border-1">
                        <AppSidebar />
                    </Col>
                    <Col md={10}>
                        <AppContent />
                    </Col>
                </Row>
                <Row>
                    <AppFooter />
                </Row>
            </Container>
        </>
    );
};
