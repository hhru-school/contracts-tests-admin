export enum Direction {
    InProgress = 'IN_PROGRESS',
}
export type StandResponse = {
    id: number;
    createdDate: Date;
    executeDate: Date;
    releaseLink: string;
    status: Direction;
    errorCount: number;
};
