import { Nav, NavItem, NavLink } from 'reactstrap';

export const AppSidebar: React.FC = () => {
    return (
        <div className="sidebar col-2 pt-3 justify-content-start">
            <Nav vertical pills>
                <NavItem>
                    <NavLink className="link active" href="/">
                        Статус
                    </NavLink>
                </NavItem>
                <NavItem>
                    <NavLink className="link" href="#">
                        История Валидаций
                    </NavLink>
                </NavItem>
            </Nav>
        </div>
    );
};
