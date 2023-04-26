import { Nav, NavItem, NavLink } from 'reactstrap';

export const AppSidebar: React.FC = () => {
    return (
        <Nav vertical pills>
            <NavItem>
                <NavLink active href="/">
                    Home page
                </NavLink>
            </NavItem>
            <NavItem>
                <NavLink href="#">Sidebar Item #2</NavLink>
            </NavItem>
            <NavItem>
                <NavLink href="#">Sidebar Item #3</NavLink>
            </NavItem>
            <NavItem>
                <NavLink href="#">Sidebar Item #4</NavLink>
            </NavItem>
        </Nav>
    );
};
