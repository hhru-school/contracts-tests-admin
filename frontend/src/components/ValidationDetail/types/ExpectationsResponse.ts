import { HTTPMethod } from './HttpMethods';

export type ExpectationsResponse = Expectation[];

export type Expectation = {
    id: number;
    httpMethod: HTTPMethod;
    requestPath: string;
    requestHeaders: {
        key: string;
        value: string[];
    };
    queryParams: {
        key: string;
        value: string[];
    };
    requestBody: string;
    responseStatus: number; // TODO: заменить на перечисление возможных статусов
    responseHeaders: {
        key: string;
        value: string[];
    };
    responseBody: unknown;
    errors: ValidationDetailError[];
};

export type ValidationDetailError = {
    id: number;
    errorType: {
        key: string;
        comment: string;
    };
    errorLevel: string; //  TODO: заменить на перечисление возможных уровней
    comment: string;
};
