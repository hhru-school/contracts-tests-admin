import { delay } from 'utils/delay';

export const getServicesList = (url: string) =>
    delay(1200).then(() => fetch(url).then((res) => res.json())); // delay для симуляции прогресса запроса
