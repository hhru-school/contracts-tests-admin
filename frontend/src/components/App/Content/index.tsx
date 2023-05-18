import { Outlet } from 'react-router-dom';
import './index.css';
import { Container } from 'reactstrap';

export const AppContent: React.FC = () => {
    return (
        <main className="app-content">
            <Container fluid className="pt-3">
                <Outlet />
            </Container>
        </main>
    );
};
