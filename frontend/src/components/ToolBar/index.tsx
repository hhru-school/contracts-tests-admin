import { ReactComponent as PlayIcon } from './img/play.svg';
import useSWR from 'swr';
import React, { useState, KeyboardEvent, ChangeEvent, MouseEvent } from 'react';
import { ListGroup, ListGroupItem, Input, Button, Alert } from 'reactstrap';
import { HandleKeys } from './types/HandleKeys';
import { Stand } from './types/Stand';

export type ToolBarProps = {
    selectedItem: string;
    setSelectedItem: (item: string) => void;
};

export const ToolBar: React.FC<ToolBarProps> = ({ selectedItem, setSelectedItem }) => {
    const { isLoading, data, error } = useSWR<Stand[]>('/api/stands');
    const [showList, setShowList] = useState(false);
    const [hoveredItem, setHoveredItem] = useState<string>('');
    const [currentPositionInList, setCurrentPositionInList] = useState(0);

    if (isLoading) {
        return <Alert color="primary"> Data is loading </Alert>;
    }

    if (error) {
        return <Alert color="danger"> Cant load data {error.message} </Alert>;
    }

    if (!data) {
        return null;
    }

    const handleSearch = (e: ChangeEvent<HTMLInputElement>) => {
        setSelectedItem(e.target.value);
        setShowList(true);
    };
    const filteredData = data
        .map((item) => item.name)
        .filter((name) => name.toLowerCase().includes(selectedItem.toLowerCase()));

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
            if (currentPositionInList < filteredData.length) {
                setCurrentPositionInList(currentPositionInList + 1);
                const element = filteredData[currentPositionInList + 1];
                handleHover(element);
            }
        } else if (event.key === HandleKeys.ArrowUp) {
            if (currentPositionInList > 0) {
                setCurrentPositionInList(currentPositionInList - 1);
                const element = filteredData[currentPositionInList - 1];
                handleHover(element);
            }
        }
    };
    return (
        <div className="row mb-5">
            <div className="col-12 d-inline d-flex align-items-center justify-content-center">
                <div className="d-block position-relative w-50">
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
                            {filteredData.map((item: string) => (
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
                <Button color="primary" className="ms-3 d-flex gap-1 align-items-center">
                    <PlayIcon /> Start
                </Button>
            </div>
        </div>
    );
};
