import { Outlet } from 'react-router-dom';

export const AppContent: React.FC = () => {
    return (
        <main className="col-8 pt-3 bg-success">
            <div className="d-flex justify-content-center flex-md-nowrap align-items-center">
                <Outlet />
            </div>
        </main>
    );
};
