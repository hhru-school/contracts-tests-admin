import { ServicesContainer } from 'components/Services/Container';
import { ToolBar } from 'components/ToolBar';
import { ValidationHistory } from 'components/ValidationHistory';
import { useState } from 'react';
import { Col, Container, Row } from 'reactstrap';

export const StatusesPage: React.FC = () => {
    const [selectedStandName, setSelectedStandName] = useState('');
    return (
        <Container>
            <Row className="pt-4">
                <ToolBar selectedItem={selectedStandName} setSelectedItem={setSelectedStandName} />
            </Row>
            <Row>
                <Col className="col-md-10">
                    <ServicesContainer standName={selectedStandName} />
                </Col>
                <Col>
                    <ValidationHistory />
                </Col>
            </Row>
        </Container>
    );
};
