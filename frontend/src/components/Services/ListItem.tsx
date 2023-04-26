import { Service } from './types/Service';

export type ServicesListItemProps = {
    item: Service;
};

export const ServicesListItem: React.FC<ServicesListItemProps> = ({ item }) => {
    return (
        <div className="list-group-item list-group-item-action d-flex gap-3 py-3">
            <div className="d-flex gap-5 justify-content-between">
                <div>
                    <h6 className="mb-0">{item.name}</h6>
                    <p className="mb-0 opacity-75">{item.version}</p>
                </div>
                {item.isConsumer && (
                    <div className="d-flex align-items-center gap-2">
                        <p className="mb-0 opacity-75">Consumer</p>
                        <small className="opacity-50 text-nowrap">
                            {new Date(item.expectationPublishDate).toLocaleDateString('ru-RU')}
                        </small>
                    </div>
                )}
                {item.isProducer && (
                    <div className="d-flex align-items-center gap-2">
                        <p className="mb-0 opacity-75">Producer</p>
                        <small className="opacity-50 text-nowrap">
                            {new Date(item.schemaPublishDate).toLocaleDateString('ru-RU')}
                        </small>
                    </div>
                )}
            </div>
        </div>
    );
};
