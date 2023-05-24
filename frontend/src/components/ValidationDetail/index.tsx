import { useState } from 'react';
import { Accordion, AccordionBody, AccordionHeader, AccordionItem } from 'reactstrap';
import { ServicesList } from './ServicesList';
import { ErrorsList } from './ErrorsList';

export type ValidationDetailProps = {
    relationsList: any;
    standName: string;
};

export const ValidationDetail: React.FC<ValidationDetailProps> = ({ relationsList, standName }) => {
    const [open, setOpen] = useState('');

    const toggle = (id: string) => {
        if (open === id) {
            setOpen('');
        } else {
            setOpen(id);
        }
    };
    return (
        // @ts-expect-error
        <Accordion open={open} toggle={toggle}>
            {relationsList.map((relationItem: any, idx: number) => (
                <AccordionItem key={idx}>
                    <AccordionHeader targetId={idx.toString()}>
                        <ServicesList
                            producer={relationItem.producer}
                            consumer={relationItem.consumer}
                            wrongExpectaionCount={relationItem.wrongExpectationCount}
                            errorCount={relationItem.errorCount}
                        />
                    </AccordionHeader>
                    <AccordionBody accordionId={idx.toString()}>
                        {open === idx.toString() && (
                            <ErrorsList
                                // @ts-expect-error
                                standName={standName}
                                validationId={1}
                                consumerId={relationItem.consumer.id}
                                producerId={relationItem.producer.id}
                            />
                        )}
                    </AccordionBody>
                </AccordionItem>
            ))}
        </Accordion>
    );
};
