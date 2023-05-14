import { Service } from './Service';

export type ApiResponse = {
    name: string;
    isRelease: boolean;
    releaseLink: string;
    services: StandServicesInfo;
};

export type StandServicesInfo = {
    stand: Service[];
    release: Service[];
};
