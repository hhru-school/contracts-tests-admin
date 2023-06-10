import { Outlet } from 'react-router-dom';
import { Container } from 'reactstrap';

import './styles.css';

export const AppContent: React.FC = () => {
    return (
        <main>
            <Container fluid className="main-content">
                <Outlet />
            </Container>
        </main>
    );
};
