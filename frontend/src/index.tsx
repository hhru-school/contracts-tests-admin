import React from 'react';
import ReactDOM from 'react-dom/client';
import { App } from './App';

// bootstrap styles
import 'bootstrap/dist/css/bootstrap.css';
import './index.css';

import { FetcherConfig } from './api';
import { AppContextProvider } from 'context/AppContext';

const root = ReactDOM.createRoot(document.getElementById('root') as HTMLElement);
root.render(
    <FetcherConfig>
        <AppContextProvider>
            <App />
        </AppContextProvider>
    </FetcherConfig>,
);
