import { NavLink } from 'react-router-dom';

import { Nav, NavItem } from 'reactstrap';
import navigation from 'routes/navigation';

export const AppSidebar: React.FC = () => {
    return (
        <Nav vertical pills>
            <NavItem>
                <NavLink className="nav-link" to={navigation.base}>
                    Статус
                </NavLink>
            </NavItem>
            <NavItem>
                <NavLink className="nav-link" to={navigation.validations}>
                    Истории валидаций
                </NavLink>
            </NavItem>
        </Nav>
    );
};
