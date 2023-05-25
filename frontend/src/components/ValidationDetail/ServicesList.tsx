import { Col } from 'reactstrap';
export type ServicesListProps = {
    producer: any;
    consumer: any;
    wrongExpectaionCount: any;
    errorCount: any;
};
export const ServicesList: React.FC<ServicesListProps> = ({
    producer,
    consumer,
    wrongExpectaionCount,
    errorCount,
}) => {
    return (
        <>
            <Col xs={4}>
                <b>Producer:</b> {producer.name}
            </Col>
            <Col xs={4}>
                <b>Consumer:</b> {consumer.name}
            </Col>
            <Col xs={3} className="d-flex justify-content-between">
                <small>Broken endpoints: {wrongExpectaionCount}</small>
                <small>Errors: {errorCount}</small>
            </Col>
        </>
    );
};
