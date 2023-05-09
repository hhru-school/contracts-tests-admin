import { AppFooter } from 'components/AppFooter/Index';
import { AppHeader } from 'components/AppHeader';
import { AppToolBar } from 'components/AppToolBar';

export const MainLayout = () => {
    return (
        <>
            <AppHeader />
            <div className="container-fluid">
                <div className="row">
                    <AppToolBar />
                </div>
                <div className="row">
                    <AppFooter />
                </div>
            </div>
        </>
    );
};
