import { ServicesContainer } from 'components/Services/Container';
import { ValidationHistory } from 'components/ValidationHistory';
import { AppContext } from 'context/AppContext';
import { useContext } from 'react';
import { Col, Row } from 'reactstrap';

export const StatusesPage: React.FC = () => {
    const { standName } = useContext(AppContext);
    return (
        <Row>
            <Col md={9}>
                <ServicesContainer standName={standName} />
            </Col>
            <Col md={3}>
                <ValidationHistory standName={standName} sizeLimit={5} />
            </Col>
        </Row>
    );
};
