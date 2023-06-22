import { useGlobalContext } from 'context/AppContext';
import { Col, Row } from 'reactstrap';
import { Validation } from 'components/Validation';
export const ValidationPage: React.FC = () => {
    const { standName } = useGlobalContext();
    return (
        <Row>
            <Col md={12}>
                <Validation standName={standName} />
            </Col>
        </Row>
    );
};
