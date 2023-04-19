import { RouterProvider } from 'react-router-dom';
import { routes } from 'routes';

export const App: React.FC = () => {
    return <RouterProvider router={routes} />;
};
