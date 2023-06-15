import { HTTPMethod } from './HttpMethods';

export type ExpectationsResponse = Expectation[];

export type Expectation = {
    id: number;
    httpMethod: HTTPMethod;
    requestPath: string;
    requestHeaders: Array<{
        key: string;
        value: string;
    }>;
    queryParams: Array<{
        key: string;
        value: string;
    }>;
    requestBody: unknown; // TODO: тип пока неизвестен
    responseStatus: number; // TODO: заменить на перечисление возможных статусов
    responseHeaders: Array<{
        key: string;
        value: string;
    }>;
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
