export const AppHistory: React.FC = () => {
    return (
        <div className="col-4 d-flex">
            <ul className="list-group">
                <li className="list-group-item d-flex justify-content-between align-items-center">
                    Предупреждений
                    <span className="badge bg-primary rounded-pill ms-2">5</span>
                </li>
                <li className="list-group-item d-flex justify-content-between align-items-center">
                    Пройдено
                </li>
                <li className="list-group-item d-flex justify-content-between align-items-center">
                    Предупреждений
                    <span className="badge bg-primary rounded-pill ms-2">1</span>
                </li>
            </ul>
        </div>
    );
};
