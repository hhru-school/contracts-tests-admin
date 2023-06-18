import { ToolBar } from 'components/ToolBar';
import { useState } from 'react';
import { NavLink } from 'react-router-dom';
import { Collapse, Nav, NavItem, Navbar, NavbarText, NavbarToggler } from 'reactstrap';
import { ReactComponent as LogoIcon } from './img/logo.svg';
import './styles.css';

import navigation from 'routes/navigation';

export const AppHeader: React.FC = () => {
    const [isOpen, setIsOpen] = useState(false);

    const toggle = () => setIsOpen(!isOpen);
    return (
        <header>
            <div className="app-header__container">
                <Navbar expand="md border-bottom border-2">
                    <NavLink className="navbar-brand me-5" to={navigation.base}>
                        <LogoIcon />
                    </NavLink>
                    <NavbarToggler onClick={toggle} />
                    <Collapse className="me-5" isOpen={isOpen} navbar>
                        <Nav navbar>
                            <NavItem className="flex-shrink-0">
                                <NavLink className="nav-link" to={navigation.base}>
                                    Сервисы
                                </NavLink>
                            </NavItem>
                            <NavItem className="flex-shrink-0">
                                <NavLink className="nav-link" to={navigation.validations.view}>
                                    Валидации
                                </NavLink>
                            </NavItem>
                        </Nav>
                    </Collapse>
                    <NavbarText tag="div" className="d-flex flex-grow-1">
                        <ToolBar />
                    </NavbarText>
                </Navbar>
            </div>
        </header>
    );
};
