import { ReactComponent as DBExpectation } from './img/db-expectation.svg';
import { ReactComponent as DataBaseIcon } from './img/database.svg';
import { Button, Col, ListGroupItem, Row } from 'reactstrap';
import { Service } from './types/Service';
import React, { useState } from 'react';
import useSWR from 'swr';
export type ServicesListItemProps = Service & {};

const handleDownload = (fileUrl: string) => {
    const link = document.createElement('a');
    link.href = fileUrl;
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
    const [linkFile, setLinkFile] = useState<string>('');
    const { data } = useSWR(
        linkFile.length === 0 ? null : `/api/download-links?filePath=${linkFile}`,
    );
    if (data && linkFile.length !== 0) {
        if (linkFile.length === 100000) {
            handleDownload(data.link);
        }
        handleDownload(data.link);
        setLinkFile('');
    }
    const onClickIcon = async (linkName: string) => {
        setLinkFile(linkName);
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
                                        onClick={() => {
                                            onClickIcon(schemaLink);
                                        }}
                                        title="Schema Link"
                                    >
                                        <DataBaseIcon />
                                    </Button>
                                )}
                                {expectationLink && (
                                    <Button
                                        className="d-flex align-items-start"
                                        color="primary"
                                        onClick={() => {
                                            onClickIcon(expectationLink);
                                        }}
                                        title="Expectation Link"
                                    >
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
                                        title="Schema Link"
                                    >
                                        <DataBaseIcon />
                                    </Button>
                                )}
                                {expectationLink && (
                                    <Button
                                        className="d-flex align-items-start"
                                        color="primary"
                                        onClick={() => {
                                            onClickIcon(expectationLink);
                                        }}
                                        title="Expectation Link"
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
