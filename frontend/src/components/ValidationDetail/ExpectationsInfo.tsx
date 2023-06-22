// @ts-nocheck
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
import SyntaxHighlighter from 'react-syntax-highlighter';
import { atelierLakesideLight } from 'react-syntax-highlighter/dist/esm/styles/hljs';

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
            {data.map(
                ({
                    id,
                    httpMethod,
                    requestPath,
                    requestBody,
                    responseBody,
                    queryParams,
                    errors,
                    requestHeaders,
                    responseHeaders,
                }) => (
                    <AccordionItem key={id}>
                        <AccordionHeader targetId={id.toString()}>
                            {httpMethod}: {requestPath}
                        </AccordionHeader>
                        <AccordionBody accordionId={id.toString()}>
                            <Row>
                                <Col md={6} className="border">
                                    Request:
                                </Col>
                                <Col md={6} className="border">
                                    Response:
                                </Col>
                            </Row>
                            <Row>
                                <Col md={6} className="border b-2 p-2">
                                    <div>
                                        <strong>Headers: </strong>
                                        {Object.entries(requestHeaders).map(([key, value]) => (
                                            <div>
                                                "{key}" : "[{value.join(',')}]"
                                            </div>
                                        ))}
                                    </div>
                                    <div>
                                        <strong>QueryParams: </strong>
                                        {Object.entries(queryParams).map(([key, value]) => (
                                            <div>
                                                "{key}" : "[{value.join(',')}]"
                                            </div>
                                        ))}
                                    </div>
                                    <div>
                                        <strong>Body:</strong>
                                        <SyntaxHighlighter
                                            language="json"
                                            style={atelierLakesideLight}
                                        >
                                            {requestBody}
                                        </SyntaxHighlighter>
                                    </div>
                                </Col>
                                <Col className="border b-2 p-2" md={6}>
                                    <div>
                                        <strong>Headers: </strong>
                                        {Object.entries(responseHeaders).map(([key, value]) => (
                                            <div>
                                                "{key}" : "[{value.join(',')}]"
                                            </div>
                                        ))}
                                    </div>
                                    <div>
                                        <strong>Body:</strong>
                                        <SyntaxHighlighter
                                            language="json"
                                            style={atelierLakesideLight}
                                        >
                                            {responseBody}
                                        </SyntaxHighlighter>
                                    </div>
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
                                            <div>
                                                <p>Error message:</p>
                                                <SyntaxHighlighter
                                                    language="json"
                                                    style={atelierLakesideLight}
                                                >
                                                    {error.message}
                                                </SyntaxHighlighter>
                                            </div>
                                        </ListGroupItemText>
                                    </ListGroupItem>
                                ))}
                            </ListGroup>
                        </AccordionBody>
                    </AccordionItem>
                ),
            )}
        </UncontrolledAccordion>
    );
};
