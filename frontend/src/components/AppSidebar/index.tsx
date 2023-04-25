import { Nav, NavItem, NavLink } from 'reactstrap';

export const AppSidebar: React.FC = () => {
    return (
        <div className="sidebar col-2 pt-3 justify-content-start">
            <Nav vertical pills>
                <NavItem>
                    <NavLink className="link" href="/">
                        Статус
                    </NavLink>
                </NavItem>
                <NavItem>
                    <NavLink className="link" href="#">
                        История Валидаций
                    </NavLink>
                </NavItem>
                <NavItem>
                    <NavLink className="link" href="#">
                        Валидация 23.04.2023 11:22
                        <button
                            type="button"
                            className="btn-close bg-danger ms-3"
                            aria-label="Close"
                        ></button>
                    </NavLink>
                </NavItem>
                <NavItem>
                    <NavLink className="link" href="#">
                        Валидация 23.04.2023 10:22
                        <button
                            type="button"
                            className="btn-close bg-danger ms-3"
                            aria-label="Close"
                        ></button>
                    </NavLink>
                </NavItem>
            </Nav>
        </div>
    );
};
