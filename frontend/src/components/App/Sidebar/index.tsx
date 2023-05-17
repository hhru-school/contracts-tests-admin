import { Nav, NavItem, NavLink } from 'reactstrap';
import navigation from 'routes/navigation';

export const AppSidebar: React.FC = () => {
    return (
        <Nav vertical pills>
            <NavItem>
                <NavLink active href={navigation.base}>
                    Статус
                </NavLink>
            </NavItem>
        </Nav>
    );
};
