import { AppContent } from 'components/AppContent';
import { AppFooter } from 'components/AppFooter/Index';
import { AppHeader } from 'components/AppHeader';
import { AppSidebar } from 'components/AppSidebar';
import { AppToolBar } from 'components/AppToolBar';
import { AppHistory } from 'components/AppHistory';

export const MainLayout = () => {
    return (
        <>
            <AppHeader />
            <div className="container-fluid">
                <div className="row">
                    <AppToolBar />
                </div>
                <div className="row">
                    <AppSidebar />
                    <AppContent />
                    <AppHistory />
                    <AppFooter />
                </div>
            </div>
        </>
    );
};
