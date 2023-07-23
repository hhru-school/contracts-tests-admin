import { delay } from 'utils/delay';

export const getValidationInfo = (url: string) =>
    delay(800).then(() => fetch(url).then((res) => res.json())); // delay для симуляции прогресса запроса

export const getExpectationsInfo = (url: string) =>
    delay(1200).then(() => fetch(url).then((res) => res.json())); // delay для симуляции прогресса запроса
