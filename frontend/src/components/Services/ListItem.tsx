import { ReactComponent as DownloadIcon } from './img/download.svg';
import { Button, Col, ListGroupItem, Row } from 'reactstrap';
import { Service } from './types/Service';

export type ServicesListItemProps = Service & {};

export const ServicesListItem: React.FC<ServicesListItemProps> = ({
    name,
    version,
    isConsumer,
    isProducer,
    expectationPublishDate,
    schemaPublishDate,
}) => {
    return (
        <ListGroupItem>
            <Row>
                <Col md={4}>
                    <h6 className="mb-0">{name}</h6>
                    <p className="mb-0 opacity-50">version: {version}</p>
                </Col>
                <Col md={4}>
                    {isConsumer && (
                        <div className="d-flex align-items-center justify-content-center gap-3">
                            <div>
                                <p className="mb-0 opacity-75">Contract as consumer</p>
                                <small className="opacity-50 text-nowrap">
                                    Uploaded:{' '}
                                    {new Date(expectationPublishDate).toLocaleString('ru-RU')}
                                </small>
                            </div>
                            <Button color="primary" outline>
                                <DownloadIcon />
                            </Button>
                        </div>
                    )}
                </Col>
                <Col md={4}>
                    {isProducer && (
                        <div className="d-flex align-items-center justify-content-center gap-3">
                            <div>
                                <p className="mb-0 opacity-75">Contract as producer</p>
                                <small className="opacity-50 text-nowrap">
                                    Uploaded: {new Date(schemaPublishDate).toLocaleString('ru-RU')}
                                </small>
                            </div>
                            <Button color="primary" outline>
                                <DownloadIcon />
                            </Button>
                        </div>
                    )}
                </Col>
            </Row>
        </ListGroupItem>
    );
};
