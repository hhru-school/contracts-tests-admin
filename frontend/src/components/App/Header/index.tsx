import { ToolBar } from 'components/ToolBar';
import { AppContext } from 'context/AppContext';
import { useContext } from 'react';
import { Col, Navbar, NavbarBrand } from 'reactstrap';

import navigation from 'routes/navigation';

export const AppHeader: React.FC = () => {
    const { standName, setStandName } = useContext(AppContext);
    return (
        <header>
            <Navbar expand="md border-bottom border-2">
                <Col md={2} className="d-flex justify-content-center">
                    <NavbarBrand className="p-2 m-0" href={navigation.base}>
                        <h3>Admin panel</h3>
                    </NavbarBrand>
                </Col>
                <Col>
                    <ToolBar selectedItem={standName} setSelectedItem={setStandName} />
                </Col>
            </Navbar>
        </header>
    );
};
