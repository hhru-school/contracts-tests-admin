import logo from 'logo.svg';
import { ApiHealthStatus } from 'components/HealthCheck';

import './index.css';

export const HomePage: React.FC = () => {
    return (
        <div className="App">
            <div className="App-header">
                <img src={logo} className="App-logo" alt="logo" />
                <div className="App-status">
                    <ApiHealthStatus />
                </div>
            </div>
        </div>
    );
};
