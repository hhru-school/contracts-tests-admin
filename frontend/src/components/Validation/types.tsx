import { ReactComponent as SuccessIcon } from './img/success.svg';
import { ReactComponent as FailedIcon } from './img/failed.svg';
import { ReactComponent as CancelledIcon } from './img/cancelled.svg';
import { Spinner } from 'reactstrap';

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

export const getStatus = (statusResponce: Direction) => {
    switch (statusResponce) {
        case Direction.InProgress:
            return <Spinner color="primary" />;
            break;
        case Direction.Cancelled:
            return <CancelledIcon />;
            break;
        case Direction.Failed:
            return <FailedIcon />;
            break;
        case Direction.Success:
            return <SuccessIcon />;
            break;
    }
};
