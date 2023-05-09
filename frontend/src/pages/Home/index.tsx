import logo from 'logo.svg';
import { ApiHealthStatus } from 'components/HealthCheck';
import { AppContent } from 'components/AppContent';
import { AppSidebar } from 'components/AppSidebar';
import { AppHistory } from 'components/AppHistory';

import './index.css';

export const HomePage: React.FC = () => {
    return (
        <div className="App">
            <div className="App-header">
                <img src={logo} className="App-logo" alt="logo" />
                <div className="App-status">
                    <ApiHealthStatus />
                    <div className="row">
                        <AppSidebar />
                        <AppContent />
                        <AppHistory />
                    </div>
                </div>
            </div>
        </div>
    );
};
