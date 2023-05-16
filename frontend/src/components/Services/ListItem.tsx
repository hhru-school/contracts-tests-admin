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
                    <p className="mb-0 opacity-75">{version}</p>
                </Col>
                <Col md={4}>
                    {isConsumer && (
                        <div className="d-flex align-items-center justify-content-center gap-3">
                            <div>
                                <p className="mb-0 opacity-75">Consumer</p>
                                <small className="opacity-50 text-nowrap">
                                    {new Date(expectationPublishDate).toLocaleDateString('ru-RU')}
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
                                <p className="mb-0 opacity-75">Producer</p>
                                <small className="opacity-50 text-nowrap">
                                    {new Date(schemaPublishDate).toLocaleDateString('ru-RU')}
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
