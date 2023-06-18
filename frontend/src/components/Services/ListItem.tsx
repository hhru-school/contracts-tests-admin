import { ReactComponent as DBExpectation } from './img/db-expectation.svg';
import { ReactComponent as DataBaseIcon } from './img/database.svg';
import { Button, Col, ListGroupItem, Row } from 'reactstrap';
import { Service } from './types/Service';
import React from 'react';
export type ServicesListItemProps = Service & {};

const handleDownload = (fileUrl: string) => {
    console.log(fileUrl);
    const link = document.createElement('a');
    link.href = fileUrl;
    link.download = 'downloaded_file.pdf';
    document.body.appendChild(link);
    link.click();
    document.body.removeChild(link);
};
export const ServicesListItem: React.FC<ServicesListItemProps> = ({
    name,
    version,
    expectationLink,
    schemaLink,
    isConsumer,
    isProducer,
    expectationPublishDate,
    schemaPublishDate,
}) => {
    const onClickIcon = async (linkName: string) => {
        console.log(linkName);
        try {
            const res = await fetch(`/api/download-links?filePath=${linkName}`);
            if (res.ok) {
                const json = await res.json();
                handleDownload(json.link);
            } else {
                console.log("Can't load link for file");
            }
        } catch (err: any) {
            console.log(err.message);
        }
    };
    return (
        <ListGroupItem>
            <Row>
                <Col lg={2}>
                    <h6 className="mb-0">{name}</h6>
                    <p className="mb-0 opacity-50">version: {version}</p>
                </Col>
                <Col lg={5}>
                    {isConsumer && (
                        <div className="d-flex align-items-center justify-content-end gap-3">
                            <div>
                                <p className="mb-0 opacity-75">Contract as consumer</p>
                                <small className="opacity-50 text-nowrap">
                                    Uploaded:{' '}
                                    {new Date(expectationPublishDate).toLocaleString('ru-RU')}
                                </small>
                            </div>
                            <div className="d-block">
                                {schemaLink && (
                                    <Button
                                        className="mb-1 d-flex align-items-start"
                                        color="primary"
                                    >
                                        <DataBaseIcon />
                                    </Button>
                                )}
                                {expectationLink && (
                                    <Button className="d-flex align-items-start" color="primary">
                                        <DBExpectation />
                                    </Button>
                                )}
                            </div>
                        </div>
                    )}
                </Col>
                <Col lg={5}>
                    {isProducer && (
                        <div className="d-flex align-items-center justify-content-end gap-3">
                            <div>
                                <p className="mb-0 opacity-75">Contract as producer</p>
                                <small className="opacity-50 text-nowrap">
                                    Uploaded: {new Date(schemaPublishDate).toLocaleString('ru-RU')}
                                </small>
                            </div>
                            <div className="mt-lg-0 mt-4 d-block">
                                {schemaLink && (
                                    <Button
                                        className="mb-1 d-flex align-items-start"
                                        color="primary"
                                        onClick={() => {
                                            onClickIcon(schemaLink);
                                        }}
                                    >
                                        <DataBaseIcon />
                                    </Button>
                                )}
                                {expectationLink && (
                                    <Button
                                        className="d-flex align-items-start"
                                        color="primary"
                                        onClick={() => {
                                            onClickIcon(schemaLink);
                                        }}
                                    >
                                        <DBExpectation />
                                    </Button>
                                )}
                            </div>
                        </div>
                    )}
                </Col>
            </Row>
        </ListGroupItem>
    );
};
