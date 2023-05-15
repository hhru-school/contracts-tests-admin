import { Col, Navbar, NavbarBrand } from 'reactstrap';

export const AppHeader: React.FC = () => {
    return (
        <header>
            <Navbar expand="md border-bottom border-2">
                <Col md={2} className="d-flex justify-content-center">
                    <NavbarBrand className="p-2 m-0" href="/">
                        <h3>Admin panel</h3>
                    </NavbarBrand>
                </Col>
            </Navbar>
        </header>
    );
};
