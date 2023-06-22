import { ServicesContainer } from 'components/Services/Container';
import { ValidationHistory } from 'components/ValidationHistory';
import { useGlobalContext } from 'context/AppContext';
import { Col, Row } from 'reactstrap';

export const StatusesPage: React.FC = () => {
    const { standName } = useGlobalContext();
    return (
        <Row>
            <Col md={8}>
                <ServicesContainer standName={standName} />
            </Col>
            <Col md={4}>
                <ValidationHistory standName={standName} />
            </Col>
        </Row>
    );
};
