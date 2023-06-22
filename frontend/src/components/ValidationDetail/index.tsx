import { useState } from 'react';
import { Accordion, AccordionBody, AccordionHeader, AccordionItem } from 'reactstrap';
import { ServicesList } from './ServicesList';
import { ExpectationsInfo } from './ExpectationsInfo';
import { ServicesRelation } from './types/ValidationResponse';

export type ValidationDetailProps = {
    relationsList: ServicesRelation[];
    standName: string;
};

export const ValidationDetail: React.FC<ValidationDetailProps> = ({ relationsList, standName }) => {
    const [open, setOpen] = useState('');

    const toggle = (id: string) => setOpen(open === id ? '' : id);

    return (
        // @ts-expect-error
        <Accordion open={open} toggle={toggle}>
            {relationsList.map(({ producer, consumer, failedRequestPathCount, errorCount }) => {
                const uniqueItemId = producer.id + '-' + consumer.id;
                return (
                    <AccordionItem key={uniqueItemId}>
                        <AccordionHeader targetId={uniqueItemId}>
                            <ServicesList
                                producer={producer}
                                consumer={consumer}
                                failedRequestPathCount={failedRequestPathCount}
                                errorCount={errorCount}
                            />
                        </AccordionHeader>
                        <AccordionBody accordionId={uniqueItemId}>
                            {open === uniqueItemId && (
                                <ExpectationsInfo
                                    standName={standName}
                                    validationId={1}
                                    consumerId={consumer.id}
                                    producerId={producer.id}
                                />
                            )}
                        </AccordionBody>
                    </AccordionItem>
                );
            })}
        </Accordion>
    );
};
