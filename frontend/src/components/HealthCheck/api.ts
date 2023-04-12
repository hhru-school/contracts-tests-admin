import { delay } from '../../utils/delay';

export const getHealthStatus = (url: string) =>
    delay(800).then(() => fetch(url).then((res) => res.json())); // delay для симуляции прогресса запроса
