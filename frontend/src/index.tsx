import React from 'react';
import ReactDOM from 'react-dom/client';
import { App } from './App';

// bootstrap styles
import 'bootstrap/dist/css/bootstrap.css';
import './index.css';

import { SWRConfig } from 'swr';
import { fetcher } from './api';

const root = ReactDOM.createRoot(document.getElementById('root') as HTMLElement);
root.render(
    <React.StrictMode>
        <SWRConfig
            value={{
                fetcher,
            }}
        >
            <App />
        </SWRConfig>
    </React.StrictMode>,
);
