export enum Direction {
    InProgress = 'IN_PROGRESS',
    Success = 'SUCCESS',
    Failed = 'FAILED',
    Cancelled = 'CANCELLED',
}
export type StandResponse = {
    id: number;
    createdDate: Date;
    executeDate: Date;
    releaseLink: string;
    status: Direction;
    errorCount: number;
};