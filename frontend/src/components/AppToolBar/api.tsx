export const fetchCustomData = (url: string) => fetch(url).then((res) => res.json()); // delay для симуляции прогресса запроса
