import { Stand } from './Stand';

export type ApiResponse = {
    stands: Stand[];
    releaseName: string;
    releaseLink: string | null;
};
