import { Navigate, createBrowserRouter } from 'react-router-dom';
import { base } from './navigation';
import { MainLayout } from '../layouts/MainLayout';
import { HomePage } from 'pages/Home';

export const routes = createBrowserRouter([
    {
        path: base,
        element: <MainLayout />,
        children: [
            {
                index: true,
                element: <HomePage />,
            },
        ],
    },
    { path: '*', element: <Navigate to={base} replace /> },
]);
