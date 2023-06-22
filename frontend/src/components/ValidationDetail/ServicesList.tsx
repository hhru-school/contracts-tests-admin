import { Col } from 'reactstrap';
export type ServicesListProps = {
    producer: any;
    consumer: any;
    failedRequestPathCount: number;
    errorCount: number;
};
export const ServicesList: React.FC<ServicesListProps> = ({
    producer,
    consumer,
    failedRequestPathCount,
    errorCount,
}) => {
    return (
        <>
            <Col xs={4}>
                <b>Producer:</b> {producer.name}
                <p className="mb-0 opacity-50">version: {producer.version}</p>
            </Col>
            <Col xs={4}>
                <b>Consumer:</b> {consumer.name}
                <p className="mb-0 opacity-50">version: {consumer.version}</p>
            </Col>
            <Col xs={3} className="d-flex justify-content-between">
                <small>Broken endpoints: {failedRequestPathCount}</small>
                <small>Errors: {errorCount}</small>
            </Col>
        </>
    );
};
