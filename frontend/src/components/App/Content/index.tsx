import { Outlet } from 'react-router-dom';
import './index.css';

export const AppContent: React.FC = () => {
    return (
        <main className="app-content">
            <Outlet />
        </main>
    );
};
