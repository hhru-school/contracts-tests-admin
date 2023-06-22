import { ToolBar } from 'components/ToolBar';
import { useState } from 'react';
import { NavLink } from 'react-router-dom';
import { Collapse, Nav, NavItem, Navbar, NavbarText, NavbarToggler } from 'reactstrap';
import { ReactComponent as LogoIcon } from './img/logo.svg';
import './styles.css';

import navigation from 'routes/navigation';

export const AppHeader: React.FC = () => {
    const [isOpen, setIsOpen] = useState(false);
    const [isShowToolBar, setIsShowToolBar] = useState<boolean>(
        window.location.pathname === navigation.error_validations.view ? false : true,
    );
    const toggle = () => setIsOpen(!isOpen);
    return (
        <header>
            <div className="app-header__container">
                <Navbar expand="md border-bottom border-2">
                    <NavLink
                        className="navbar-brand me-5"
                        to={navigation.base}
                        onClick={() => setIsShowToolBar(true)}
                    >
                        <LogoIcon />
                    </NavLink>
                    <NavbarToggler onClick={toggle} />
                    <Collapse className="me-5" isOpen={isOpen} navbar>
                        <Nav navbar>
                            <NavItem className="flex-shrink-0">
                                <NavLink
                                    className="nav-link"
                                    to={navigation.base}
                                    onClick={() => setIsShowToolBar(true)}
                                >
                                    Сервисы
                                </NavLink>
                            </NavItem>
                            <NavItem className="flex-shrink-0">
                                <NavLink
                                    className="nav-link"
                                    to={navigation.validations.view}
                                    onClick={() => setIsShowToolBar(true)}
                                >
                                    Валидации
                                </NavLink>
                            </NavItem>
                            <NavItem className="flex-shrink-0">
                                <NavLink
                                    className="nav-link"
                                    to={navigation.error_validations.view}
                                    onClick={() => setIsShowToolBar(false)}
                                >
                                    Ошибки
                                </NavLink>
                            </NavItem>
                        </Nav>
                    </Collapse>
                    {isShowToolBar && (
                        <NavbarText tag="div" className="d-flex flex-grow-1">
                            <ToolBar />
                        </NavbarText>
                    )}
                </Navbar>
            </div>
        </header>
    );
};
