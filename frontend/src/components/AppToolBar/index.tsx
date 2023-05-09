import play from '../../img/play.svg';
import useSWR from 'swr';
import React, { useState, KeyboardEvent, ChangeEvent, MouseEvent } from 'react';
import { ListGroup, ListGroupItem, Input, Button, Alert } from 'reactstrap';
export const AppToolBar: React.FC = () => {
    const { isLoading, data, error } = useSWR('/api/stands/');
    const [selectedItem, setSelectedItem] = useState('');
    const [showList, setShowList] = useState(false);
    const [hoveredItem, setHoveredItem] = useState<string>('');
    const [currentPositionInList, setCurrentPositionInList] = useState(0);
    if (isLoading) {
        return <Alert color="primary"> Data is loading </Alert>;
    }
    if (error) {
        return <Alert color="danger"> Cant load data {error.message} </Alert>;
    }

    const handleSearch = (e: ChangeEvent<HTMLInputElement>) => {
        setSelectedItem(e.target.value);
        setShowList(true);
    };
    const filteredData = data.stands
        .map((item: any) => item['name'])
        .filter((name: string) => name.toLowerCase().includes(selectedItem.toLowerCase()));

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
        console.log('handleKeyDown', event.key);
        if (event.key === 'Escape') {
            setShowList(false);
        } else if (event.key === 'Enter') {
            const element = filteredData[currentPositionInList];
            setSelectedItem(element);
            setShowList(false);
        } else if (event.key === 'ArrowDown') {
            if (currentPositionInList < filteredData.length) {
                setCurrentPositionInList(currentPositionInList + 1);
                const element = filteredData[currentPositionInList + 1];
                handleHover(element);
                //setSelectedItem(element);
            }
        } else if (event.key === 'ArrowUp') {
            if (currentPositionInList > 0) {
                setCurrentPositionInList(currentPositionInList - 1);
                const element = filteredData[currentPositionInList - 1];
                handleHover(element);
                //setSelectedItem(element);
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
                <Button color="primary" className="ms-3">
                    <img src={play} /> Start
                </Button>
            </div>
        </div>
    );
};
