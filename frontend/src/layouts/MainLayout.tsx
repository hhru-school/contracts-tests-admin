import { AppContent } from 'components/AppContent';
import { AppFooter } from 'components/AppFooter/Index';
import { AppHeader } from 'components/AppHeader';
import { AppSidebar } from 'components/AppSidebar';

export const MainLayout = () => {
    return (
        <>
            <AppHeader />
            <div className="container-fluid">
                <div className="row">
                    <AppSidebar />
                    <AppContent />
                    <AppFooter />
                </div>
            </div>
        </>
    );
};
