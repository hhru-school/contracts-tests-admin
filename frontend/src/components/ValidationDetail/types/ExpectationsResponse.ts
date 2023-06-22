import { HTTPMethod } from './HttpMethods';

export enum ErrorLevel {
    ERROR = 'ERROR',
    WARN = 'WARN',
}

export type ExpectationsResponse = Expectation[];

export type Expectation = {
    id: number;
    httpMethod: HTTPMethod;
    requestPath: string;
    requestHeaders: HeadersType;
    queryParams: QueryParamsType;
    requestBody: string;
    responseStatus: number; // TODO: заменить на перечисление возможных статусов
    responseHeaders: HeadersType;
    responseBody: string;
    errors: ValidationDetailError[];
};

export type ValidationDetailError = {
    id: number;
    errorType: {
        key: string;
        comment: string;
        version: 0;
    };
    errorLevel: ErrorLevel;
    message: string;
};

export type HeadersType = {
    key: string;
    value: string[];
};

export type QueryParamsType = {
    key: string;
    value: string[];
};
