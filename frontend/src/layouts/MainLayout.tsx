import { AppContent } from 'components/App/Content';
import { AppHeader } from 'components/App/Header';
import { Container } from 'reactstrap';

export const MainLayout = () => {
    return (
        <Container>
            <AppHeader />
            <AppContent />
        </Container>
    );
};
