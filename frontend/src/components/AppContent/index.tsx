import { Outlet } from 'react-router-dom';

export const AppContent: React.FC = () => {
    return (
        <main className="col-md-10">
            <Outlet />
        </main>
    );
};
