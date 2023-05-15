import { Service } from './types/Service';

export type ServicesListItemProps = {
    item: Service;
};

export const ServicesListItem: React.FC<ServicesListItemProps> = ({ item }) => {
    return (
        <div className="list-group-item list-group-item-action d-flex gap-3 py-3 justify-content-between">
            <div>
                <h6 className="mb-0">{item.name}</h6>
                <p className="mb-0 opacity-75">{item.version}</p>
            </div>
            <div className="d-flex align-items-center gap-2" style={{ minWidth: '25%' }}>
                {item.isConsumer && (
                    <>
                        <p className="mb-0 opacity-75">Consumer</p>
                        <small className="opacity-50 text-nowrap">
                            {new Date(item.expectationPublishDate).toLocaleDateString('ru-RU')}
                        </small>
                    </>
                )}
            </div>
            <div className="d-flex align-items-center gap-2" style={{ minWidth: '25%' }}>
                {item.isProducer && (
                    <>
                        <p className="mb-0 opacity-75">Producer</p>
                        <small className="opacity-50 text-nowrap">
                            {new Date(item.schemaPublishDate).toLocaleDateString('ru-RU')}
                        </small>
                    </>
                )}
            </div>
        </div>
    );
};
