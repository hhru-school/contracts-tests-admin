import { Outlet } from 'react-router-dom';

export const AppContent: React.FC = () => {
    return (
        <main>
            <Outlet />
        </main>
    );
};
