import { Nav, NavItem, NavLink } from 'reactstrap';

export const AppSidebar: React.FC = () => {
    return (
        <Nav vertical pills>
            <NavItem>
                <NavLink active href="/">
                    Статус
                </NavLink>
            </NavItem>
        </Nav>
    );
};
