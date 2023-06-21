import { Navigate, createBrowserRouter } from 'react-router-dom';
import { MainLayout } from '../layouts/MainLayout';
import { StatusesPage } from 'pages/Statuses';
import { ValidationsPage } from 'pages/Validations';
import { ErrorValidation } from 'pages/ErrorValidation';

import navigation from './navigation';
import { ValidationDetailPage } from 'pages/ValidationsDetail';

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
                path: navigation.validations.view,
                element: <ValidationsPage />,
            },
            {
                path: navigation.validations.detail,
                element: <ValidationDetailPage />,
            },
            {
                path: navigation.error_validations.view,
                element: <ErrorValidation />,
            },
        ],
    },
    { path: '*', element: <Navigate to={navigation.base} replace /> },
]);
