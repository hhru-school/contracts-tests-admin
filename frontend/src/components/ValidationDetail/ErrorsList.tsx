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
import { delay } from 'utils/delay';

export const getValidationInfo = (url: string) =>
    delay(1200).then(() => fetch(url).then((res) => res.json())); // delay для симуляции прогресса запроса

// @ts-expect-error
export const ErrorsList: React.FC = ({ standName, validationId, consumerId, producerId }) => {
    const { isLoading, isValidating, data } = useSWR(
        standName
            ? `/api/stands/${standName}/validations/${validationId}/expectations?consumerId=${consumerId}&producerId=${producerId}`
            : null,
        getValidationInfo,
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
            {data.map((el: any) => {
                const { id, httpMethod, requestPath, ...other } = el;
                return (
                    <AccordionItem key={id}>
                        <AccordionHeader targetId={id.toString()}>
                            {httpMethod}: {requestPath}
                        </AccordionHeader>
                        <AccordionBody accordionId={id.toString()}>
                            <Row>
                                <Col md={6} className="border b-2 p-5">
                                    Request: {JSON.stringify(other.requestBody)}
                                </Col>
                                <Col className="border b-2 p-5" md={6}>
                                    Response: {JSON.stringify(other.responseBody)}
                                </Col>
                            </Row>
                            <ListGroup flush>
                                {other.errors.map((error: any, idx: number) => (
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
                );
            })}
        </UncontrolledAccordion>
    );
};
