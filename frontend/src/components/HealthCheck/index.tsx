import { Spinner } from 'reactstrap';
import useSWR from 'swr';
import { getHealthStatus } from './api';

export const ApiHealthStatus: React.FC = () => {
    const { isLoading, isValidating, error } = useSWR('/api/health', getHealthStatus, {
        refreshInterval: 5000,
    });

    if (isLoading || isValidating) return <Spinner color="info" type="grow"></Spinner>;
    if (error) return <p className="text-danger">Unhealthy</p>;
    return <p className="text-info">Healthy</p>;
};
