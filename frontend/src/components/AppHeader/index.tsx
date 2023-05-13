import { useState } from 'react';
import { Navbar, NavbarBrand, NavbarToggler } from 'reactstrap';

export const AppHeader: React.FC = () => {
    const [isOpen, setisOpen] = useState(false);

    const toggle = () => setisOpen(!isOpen);
    return (
        <header>
            <Navbar expand="md">
                <NavbarBrand href="/">hh Contract</NavbarBrand>
                <NavbarToggler onClick={toggle} />
            </Navbar>
        </header>
    );
};
