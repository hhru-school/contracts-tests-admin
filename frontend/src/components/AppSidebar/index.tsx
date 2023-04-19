import { Nav, NavItem, NavLink } from 'reactstrap';

export const AppSidebar: React.FC = () => {
    return (
        <div className="sidebar col-md-2 pt-3">
            <Nav vertical pills>
                <NavItem>
                    <NavLink className="link" href="/">
                        Sidebar Item #1
                    </NavLink>
                </NavItem>
                <NavItem>
                    <NavLink className="link" href="#">
                        Sidebar Item #2
                    </NavLink>
                </NavItem>
                <NavItem>
                    <NavLink className="link" href="#">
                        Sidebar Item #3
                    </NavLink>
                </NavItem>
                <NavItem>
                    <NavLink className="link" href="#">
                        Sidebar Item #4
                    </NavLink>
                </NavItem>
            </Nav>
        </div>
    );
};
