import {
    AccordionBody,
    AccordionHeader,
    AccordionItem,
    Button,
    Col,
    UncontrolledAccordion,
} from 'reactstrap';

import { ReactComponent as DownloadIcon } from 'components/Services/img/download.svg';

export const DetailPage: React.FC = () => {
    return (
        <UncontrolledAccordion defaultOpen="1">
            <AccordionItem>
                <AccordionHeader targetId="1">
                    <Col md={4}>
                        <h6 className="mb-0">Service Name</h6>
                        <p className="mb-0 opacity-75">0.1.0</p>
                    </Col>
                    <Col md={4}>
                        <div className="d-flex align-items-center justify-content-center gap-3">
                            <div>
                                <p className="mb-0 opacity-75">Consumer</p>
                                <small className="opacity-50 text-nowrap">20.03.2023</small>
                            </div>
                            <Button color="primary" tag="a" outline>
                                <DownloadIcon />
                            </Button>
                        </div>
                    </Col>
                    <Col md={3}>
                        <div className="d-flex align-items-center justify-content-center gap-3">
                            <div>
                                <p className="mb-0 opacity-75">Producer</p>
                                <small className="opacity-50 text-nowrap">20.03.2023</small>
                            </div>
                            <Button color="primary" tag="a" outline>
                                <DownloadIcon />
                            </Button>
                        </div>
                    </Col>
                </AccordionHeader>
                <AccordionBody accordionId="1">
                    {/* <strong>This is the first item's accordion body.</strong>
                        You can modify any of this with custom CSS or overriding our default
                        variables. It's also worth noting that just about any HTML can go within the{' '}
                        <code>.accordion-body</code>, though the transition does limit overflow. */}
                    <UncontrolledAccordion flush defaultOpen="1">
                        <AccordionItem>
                            <AccordionHeader targetId="1">Accordion Item 1</AccordionHeader>
                            <AccordionBody accordionId="1">
                                <strong>This is the first item's accordion body.</strong>
                                You can modify any of this with custom CSS or overriding our default
                                variables. It's also worth noting that just about any HTML can go
                                within the <code>.accordion-body</code>, though the transition does
                                limit overflow.
                            </AccordionBody>
                        </AccordionItem>
                        <AccordionItem>
                            <AccordionHeader targetId="2">Accordion Item 2</AccordionHeader>
                            <AccordionBody accordionId="2">
                                <strong>This is the second item's accordion body.</strong>
                                You can modify any of this with custom CSS or overriding our default
                                variables. It's also worth noting that just about any HTML can go
                                within the <code>.accordion-body</code>, though the transition does
                                limit overflow.
                            </AccordionBody>
                        </AccordionItem>
                        <AccordionItem>
                            <AccordionHeader targetId="3">Accordion Item 3</AccordionHeader>
                            <AccordionBody accordionId="3">
                                <strong>This is the third item's accordion body.</strong>
                                You can modify any of this with custom CSS or overriding our default
                                variables. It's also worth noting that just about any HTML can go
                                within the <code>.accordion-body</code>, though the transition does
                                limit overflow.
                            </AccordionBody>
                        </AccordionItem>
                    </UncontrolledAccordion>
                </AccordionBody>
            </AccordionItem>
            <AccordionItem>
                <AccordionHeader targetId="2">Accordion Item 2</AccordionHeader>
                <AccordionBody accordionId="2">
                    <strong>This is the second item's accordion body.</strong>
                    You can modify any of this with custom CSS or overriding our default variables.
                    It's also worth noting that just about any HTML can go within the{' '}
                    <code>.accordion-body</code>, though the transition does limit overflow.
                </AccordionBody>
            </AccordionItem>
            <AccordionItem>
                <AccordionHeader targetId="3">Accordion Item 3</AccordionHeader>
                <AccordionBody accordionId="3">
                    <strong>This is the third item's accordion body.</strong>
                    You can modify any of this with custom CSS or overriding our default variables.
                    It's also worth noting that just about any HTML can go within the{' '}
                    <code>.accordion-body</code>, though the transition does limit overflow.
                </AccordionBody>
            </AccordionItem>
        </UncontrolledAccordion>
    );
};
