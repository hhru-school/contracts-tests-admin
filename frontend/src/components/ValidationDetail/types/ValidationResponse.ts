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
    producer: {
        id: number;
        name: string;
        isRelease: boolean;
        version: string;
        schemaLink: string;
    };
    consumer: {
        id: number;
        name: string;
        isRelease: false;
        version: string;
        expectationLink: string;
    };
    wrongExpectationCount: number;
    errorCount: number;
};
