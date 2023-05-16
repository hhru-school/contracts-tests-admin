import { PropsWithChildren } from 'react';
import { Fetcher, SWRConfig } from 'swr';

export const fetcher: Fetcher = (url: RequestInfo | URL, options?: RequestInit) =>
    fetch(url, options).then((res) => res.json());

export const FetcherConfig: React.FC<PropsWithChildren> = ({ children }) => (
    <SWRConfig value={{ fetcher }}>{children}</SWRConfig>
);
