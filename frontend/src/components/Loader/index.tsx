import { Spinner } from 'reactstrap';

export const Loader: React.FC = () => (
    <Spinner
        type="border"
        color="primary"
        style={{
            height: '3rem',
            width: '3rem',
        }}
    />
);
