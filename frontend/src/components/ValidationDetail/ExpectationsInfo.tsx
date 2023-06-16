import { Loader } from 'components/Loader';
import {
    AccordionBody,
    AccordionHeader,
    AccordionItem,
    Col,
    ListGroup,
    ListGroupItem,
    ListGroupItemText,
    Row,
    UncontrolledAccordion,
} from 'reactstrap';
import useSWR from 'swr';
import { getExpectationsInfo } from './api';
import { ExpectationsResponse } from './types/ExpectationsResponse';

export type ExpectationsInfoProps = {
    standName: string;
    validationId: number;
    consumerId: number;
    producerId: number;
};

export const ExpectationsInfo: React.FC<ExpectationsInfoProps> = ({
    standName,
    validationId,
    consumerId,
    producerId,
}) => {
    const { isLoading, isValidating, data } = useSWR<ExpectationsResponse>(
        standName
            ? `/api/stands/${standName}/validations/${validationId}/expectations?consumerId=${consumerId}&producerId=${producerId}`
            : null,
        getExpectationsInfo,
    );
    if (isLoading || isValidating)
        return (
            <div className="py-2 d-flex justify-content-center">
                <Loader />
            </div>
        );

    if (!data) return null;
    return (
        <UncontrolledAccordion flush>
            {data.map(({ id, httpMethod, requestPath, requestBody, responseBody, errors }) => (
                <AccordionItem key={id}>
                    <AccordionHeader targetId={id.toString()}>
                        {httpMethod}: {requestPath}
                    </AccordionHeader>
                    <AccordionBody accordionId={id.toString()}>
                        <Row>
                            <Col md={6} className="border b-2 p-5">
                                Request: {JSON.stringify(requestBody)}
                            </Col>
                            <Col className="border b-2 p-5" md={6}>
                                Response: {JSON.stringify(responseBody)}
                            </Col>
                        </Row>
                        <ListGroup flush>
                            {errors.map((error, idx) => (
                                <ListGroupItem key={error.id + '-' + idx}>
                                    <ListGroupItemText tag="div">
                                        <div>
                                            <small>Error key: {error.errorType.key}</small>
                                            <p>comment: {error.errorType.comment}</p>
                                        </div>
                                        <div>
                                            <small>Error level:{error.errorLevel}</small>
                                            <p>comment: {error.comment}</p>
                                        </div>
                                    </ListGroupItemText>
                                </ListGroupItem>
                            ))}
                        </ListGroup>
                    </AccordionBody>
                </AccordionItem>
            ))}
        </UncontrolledAccordion>
    );
};
