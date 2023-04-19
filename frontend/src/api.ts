import { Fetcher } from 'swr';

export const fetcher: Fetcher = (url: RequestInfo | URL, options?: RequestInit) =>
    fetch(url, options).then((res) => res.json());
