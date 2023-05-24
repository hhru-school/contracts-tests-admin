import { Outlet } from 'react-router-dom';
import { Container } from 'reactstrap';

export const AppContent: React.FC = () => {
    return (
        <main>
            <Container fluid className="pt-3">
                <Outlet />
            </Container>
        </main>
    );
};
