import { ServicesListItem } from './ListItem';
import { Service } from './types/Service';

export type ServicesListProps = {
    items: Service[];
};

export const ServicesList: React.FC<ServicesListProps> = ({ items }) => {
    return (
        <div className="list-group">
            {items.map((el, idx) => (
                <ServicesListItem key={el.name + idx} item={el} />
            ))}
        </div>
    );
};
