import { AppToolBar } from 'components/AppToolBar';
import { AppHistory } from 'components/AppHistory';

export const AppContent: React.FC = () => {
    return (
        <main className="col-md-10">
            <AppToolBar />
            <div className="row mt-3 d-flex justify-content-end">
                <AppHistory />
            </div>
        </main>
    );
};
