import { Navigate, createBrowserRouter } from 'react-router-dom';
import { MainLayout } from '../layouts/MainLayout';
import { StatusesPage } from 'pages/Statuses';
import { ValidationsPage } from 'pages/Validations';

import navigation from './navigation';

export const routes = createBrowserRouter([
    {
        path: navigation.base,
        element: <MainLayout />,
        children: [
            {
                index: true,
                element: <StatusesPage />,
            },
            {
                path: 'validations',
                index: true,
                element: <ValidationsPage />,
            },
        ],
    },
    { path: '*', element: <Navigate to={navigation.base} replace /> },
]);
