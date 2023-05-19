import { ServicesContainer } from 'components/Services/Container';
import { ValidationHistory } from 'components/ValidationHistory';
import { AppContext } from 'context/AppContext';
import { useContext } from 'react';
import { Col, Row } from 'reactstrap';

export const StatusesPage: React.FC = () => {
    const { standName } = useContext(AppContext);
    return (
        <Row>
            <Col md={10}>
                <ServicesContainer standName={standName} />
            </Col>
            <Col>
                <ValidationHistory />
            </Col>
        </Row>
    );
};
