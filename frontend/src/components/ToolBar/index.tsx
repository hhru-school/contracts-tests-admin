import { ReactComponent as PlayIcon } from './img/play.svg';
import useSWR from 'swr';
import React, { useState, KeyboardEvent, ChangeEvent, MouseEvent } from 'react';
import { ListGroup, ListGroupItem, Input, Button, Alert, Spinner } from 'reactstrap';
import { HandleKeys } from './types/HandleKeys';
import { Stand } from './types/Stand';

export type ToolBarProps = {
    selectedItem: string;
    setSelectedItem: (item: string) => void;
};

export const ToolBar: React.FC<ToolBarProps> = ({ selectedItem, setSelectedItem }) => {
    const [showList, setShowList] = useState(false);
    const [validation, setValidation] = useState(false);
    const [hoveredItem, setHoveredItem] = useState<string>('');
    const [currentPositionInList, setCurrentPositionInList] = useState(0);
    const { isLoading, data, error } = useSWR<Stand[]>(
        selectedItem.length >= 300 ? `/api/stands?search=${selectedItem}` : '/api/stands',
    );
    if (isLoading) {
        return <Alert color="primary"> Data is loading </Alert>;
    }
    if (error) {
        return <Alert color="danger"> Cant load data {error.message} </Alert>;
    }
    if (!data) {
        return null;
    }
    const filteredData = data.map((item) => item.name);
    const handleSearch = (e: ChangeEvent<HTMLInputElement>) => {
        setSelectedItem(e.target.value);
        setShowList(true);
    };
    const handleHover = (item: string) => {
        setHoveredItem(item);
        if (item.length != 0) {
            setCurrentPositionInList(filteredData.indexOf(item));
        }
    };
    const selectElement = (item: string) => {
        setSelectedItem(item);
        setShowList(false);
        setCurrentPositionInList(0);
    };
    const handleSelect = (item: MouseEvent<HTMLElement>) => {
        selectElement(item.currentTarget.textContent as string);
    };
    const handleFocus = () => {
        setShowList(true);
    };
    const handleBlur = () => {
        selectElement(hoveredItem);
    };
    const handleKeyDown = (event: KeyboardEvent<HTMLInputElement>) => {
        if (event.key === HandleKeys.Escape) {
            setShowList(false);
        } else if (event.key === HandleKeys.Enter) {
            const element = filteredData[currentPositionInList];
            setSelectedItem(element);
            setShowList(false);
        } else if (event.key === HandleKeys.ArrowDown) {
            let curIndex = currentPositionInList;
            if (curIndex + 1 < filteredData.length) {
                curIndex += 1;
            } else {
                curIndex = 0;
            }
            const element = filteredData[curIndex];
            handleHover(element);
            setCurrentPositionInList(curIndex);
        } else if (event.key === HandleKeys.ArrowUp) {
            let curIndex = currentPositionInList;
            if (curIndex > 0) {
                curIndex -= 1;
            } else {
                curIndex = filteredData.length - 1;
            }
            setCurrentPositionInList(curIndex);
            const element = filteredData[curIndex];
            handleHover(element);
        }
    };
    const submitRequest = () => {
        setValidation(true);
        setTimeout(() => {
            setValidation(false);
        }, 5000);
    };
    return (
        <div className="d-flex align-items-center gap-3 w-100">
            <div className="position-relative flex-grow-1">
                <Input
                    type="text"
                    placeholder="Search"
                    value={selectedItem}
                    onChange={handleSearch}
                    onFocus={handleFocus}
                    onBlur={handleBlur}
                    onKeyDown={handleKeyDown}
                />
                {showList && (
                    <ListGroup
                        className="position-absolute w-100"
                        style={{ height: '250px', overflow: 'auto' }}
                        onSelect={handleSelect}
                    >
                        {filteredData.length > 0 &&
                            filteredData.map((item: string) => (
                                <ListGroupItem
                                    key={item}
                                    onClick={() => selectElement(item)}
                                    onMouseEnter={() => handleHover(item)}
                                    onMouseLeave={() => handleHover('')}
                                    className={hoveredItem === item ? 'active' : ''}
                                >
                                    {item}
                                </ListGroupItem>
                            ))}
                    </ListGroup>
                )}
            </div>
            <Button
                color="primary"
                className="flex-shrink-0"
                disabled={!selectedItem}
                onClick={() => {
                    submitRequest();
                }}
            >
                {validation ? (
                    <>
                        <Spinner color="white" style={{ width: '16px', height: '16px' }} /> Process
                    </>
                ) : (
                    <>
                        <PlayIcon /> Start
                    </>
                )}
            </Button>
        </div>
    );
};
