import { Spinner } from 'reactstrap';

export const Loader: React.FC = () => (
    <Spinner
        type="grow"
        color="primary"
        style={{
            height: '3rem',
            width: '3rem',
        }}
    />
);
