import { Col } from 'reactstrap';
import { ConsumerService, ProducerService } from './types/ValidationResponse';
export type ServicesListProps = {
    producer: ProducerService;
    consumer: ConsumerService;
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
                {producer.release && <span className="opacity-70">release</span>}
            </Col>
            <Col xs={4}>
                <b>Consumer:</b> {consumer.name}
                <p className="mb-0 opacity-50">version: {consumer.version}</p>
                {consumer.release && <span className="opacity-70">release</span>}
            </Col>
            <Col xs={3} className="d-flex justify-content-between">
                <small>Broken endpoints: {failedRequestPathCount}</small>
                <small>Errors: {errorCount}</small>
            </Col>
        </>
    );
};
