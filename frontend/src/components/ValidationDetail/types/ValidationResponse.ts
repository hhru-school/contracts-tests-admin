import { Direction } from '../../Validation/types';

export type ValidationResponse = {
    id: number;
    createdDate: Date;
    executeDate: Date;
    releaseLink: string;
    status: Direction;
    errorCount: number;
    servicesRelations: ServicesRelation[];
};

export type ServicesRelation = {
    producer: ProducerService;
    consumer: ConsumerService;
    failedRequestPathCount: number;
    errorCount: number;
};

export type ServiceType = {
    id: number;
    name: string;
    release: boolean;
    version: string;
};

export type ProducerService = ServiceType & {
    schemaLink: string;
};

export type ConsumerService = ServiceType & {
    expectationLink: string;
};
