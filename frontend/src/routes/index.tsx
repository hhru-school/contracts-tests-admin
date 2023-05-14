import { Navigate, createBrowserRouter } from 'react-router-dom';
import { base } from './navigation';
import { MainLayout } from '../layouts/MainLayout';
import { StatusesPage } from 'pages/Statuses';

export const routes = createBrowserRouter([
    {
        path: base,
        element: <MainLayout />,
        children: [
            {
                index: true,
                element: <StatusesPage />,
            },
        ],
    },
    { path: '*', element: <Navigate to={base} replace /> },
]);
